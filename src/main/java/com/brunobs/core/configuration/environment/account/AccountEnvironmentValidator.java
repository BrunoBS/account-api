package com.brunobs.core.configuration.environment.account;

import com.brunobs.core.catalog.type.environment.EnvironmentTypeEnum;
import com.brunobs.core.catalog.type.publisherscope.PublisherScopeTypeEnum;
import com.brunobs.core.catalog.type.schema.SchemaType;
import com.brunobs.core.catalog.type.schema.SchemaTypeEnum;
import com.brunobs.core.catalog.type.schema.SchemaTypeRepository;
import com.brunobs.core.configuration.PublisherConfig;
import com.brunobs.core.configuration.environment.account.dto.AccountEnvironmentDTO;
import com.brunobs.core.configuration.environment.account.dto.AccountEnvironmentIdDTO;
import com.brunobs.core.publisher.Publisher;
import com.brunobs.core.publisher.PublisherDTO;
import com.brunobs.message.feature.AccountEnvMessages;
import com.brunobs.shared.base.BaseEnum;
import com.brunobs.shared.base.BaseValidator;
import com.brunobs.shared.SchemaValidator;
import com.brunobs.shared.validation.ValidationResult;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Component
public class AccountEnvironmentValidator extends BaseValidator<AccountEnvironmentDTO, AccountEnvironmentIdDTO> {


    private final SchemaValidator schemaValidator;
    private final AccountEnvMessages accountEnvMessages;
    private final SchemaValidator schemaEngine;
    private final SchemaTypeRepository schemaTypeRepository;

    public AccountEnvironmentValidator(SchemaValidator schemaValidator, AccountEnvMessages accountEnvMessages, SchemaValidator schemaEngine, SchemaTypeRepository schemaTypeRepository) {
        super();
        this.schemaValidator = schemaValidator;
        this.accountEnvMessages = accountEnvMessages;
        this.schemaEngine = schemaEngine;
        this.schemaTypeRepository = schemaTypeRepository;
    }

    @Override
    public void validateAttributes(AccountEnvironmentDTO dto, ValidationResult vr) {
        Set<String> publisherNames = new HashSet<>();

        if (dto.publishers() == null || dto.publishers().isEmpty()) {
            vr.addError("publishers", accountEnvMessages.minPublisher(1));
        } else {
            int index = 0;
            for (PublisherConfig config : dto.publishers()) {
                String pathPrefix = "publishers[" + index + "]";
                PublisherScopeTypeEnum publisherScopeTypeEnum = getPublisherScopeTypeEnum(config.getPublisher().getPublisherScope().getName());
                if (!PublisherScopeTypeEnum.ACCOUNT.equals(publisherScopeTypeEnum)) {
                    vr.addError(pathPrefix + ".name", accountEnvMessages.invalidPublisherScope());
                }

                if (!publisherNames.add(config.getPublisher().getName())) {
                    vr.addError(pathPrefix + ".name", accountEnvMessages.duplicatePublisher());
                }

                if (config.getOrder() == null || config.getOrder() <= 0) {
                    vr.addError(pathPrefix + ".order", accountEnvMessages.orderRequired());
                }

                JsonNode jsonNode = schemaValidator.fromString(config.getParameters());
                validatePublisherSchema(config.getPublisher(), jsonNode, pathPrefix + ".parameters", vr);

                index++;
            }
        }
        validateAdditionalFields(dto, vr);
    }


    public void validateAdditionalFields(AccountEnvironmentDTO dto, ValidationResult vr) {
        schemaEngine.validateJson(getJsonSchema(), dto.settings(), "settings", vr);
    }

    protected String getJsonSchema() {
        return schemaTypeRepository.findByNameAndActiveTrue(SchemaTypeEnum.ACCOUNT_ENVIRONMENT.name())
                .map(SchemaType::getJsonSchema)
                .orElse(SchemaTypeEnum.DEFAULT_JSON_SCHEMA);
    }

    @Override
    public String recordRequired() {
        return accountEnvMessages.recordRequired();
    }


    private void validatePublisherSchema(Publisher p, JsonNode params, String path, ValidationResult vr) {
        schemaValidator.validateJson(p.getJsonSchema(), params, path, vr);
    }

    @Override
    public String entityName() {
        return AccountEnvironment.class.getSimpleName();
    }

    @Override
    protected void validateIntegrity(AccountEnvironmentDTO dto, ValidationResult vr) {

        if (isMissingAccount(dto) || isMissingEnvironment(dto)) {
            vr.addError("account", accountEnvMessages.selectionRequired());
            return;
        }

        if (isCustomEnvironment(dto)) {
            Long accountId = dto.account().getId();
            Long envAccountId = dto.environment().getAccount() != null ? dto.environment().getAccount().getId() : null;

            if (!Objects.equals(accountId, envAccountId)) {
                vr.addError("account", accountEnvMessages.inconsistentAccount(accountId));
            }
        }
    }

    private boolean isMissingAccount(AccountEnvironmentDTO dto) {
        return dto.account() == null || dto.account().getId() == null;
    }

    private boolean isMissingEnvironment(AccountEnvironmentDTO dto) {
        return dto.environment() == null || dto.environment().getId() == null;
    }

    private boolean isCustomEnvironment(AccountEnvironmentDTO dto) {
        return EnvironmentTypeEnum.CUSTOM.equals(
                getEnvironmentTypeEnum(dto.environment().getType().getName())
        );
    }

    private EnvironmentTypeEnum getEnvironmentTypeEnum(String name) {
        return BaseEnum.from(EnvironmentTypeEnum.class, name);
    }

    private PublisherScopeTypeEnum getPublisherScopeTypeEnum(String name) {
        return BaseEnum.from(PublisherScopeTypeEnum.class, name);
    }
}
