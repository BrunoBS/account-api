package com.brunobs.core.configuration.environment.account;

import com.brunobs.core.catalog.type.environment.EnvironmentTypeEnum;
import com.brunobs.core.configuration.PublisherConfig;
import com.brunobs.core.configuration.environment.account.dto.AccountEnvironmentDTO;
import com.brunobs.core.configuration.environment.account.dto.AccountEnvironmentIdDTO;
import com.brunobs.core.publisher.Publisher;
import com.brunobs.shared.BaseEnum;
import com.brunobs.shared.validation.BaseValidator;
import com.brunobs.shared.SchemaValidator;
import com.brunobs.shared.validation.ValidationResult;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Component
public class AccountEnvironmentValidator extends BaseValidator<AccountEnvironmentDTO, AccountEnvironmentIdDTO> {

    // Chaves i18n (definidas no messages_en.properties)
    public static final String MSG_DUPLICATE_PUBLISHER = "validator.account-env.duplicate.publisher";
    public static final String MSG_INCONSISTENT_ACCOUNT = "validator.account-env.inconsistent.account";
    public static final String MSG_MIN_PUBLISHER = "validator.account-env.min.publisher";
    public static final String MSG_ORDER_REQUIRED = "validator.account-env.order.required";

    private final SchemaValidator schemaValidator;
    private final AccountEnvironmentRepository repository;

    public AccountEnvironmentValidator(SchemaValidator schemaValidator, AccountEnvironmentRepository repository, MessageSource messageSource) {
        super(messageSource);
        this.schemaValidator = schemaValidator;
        this.repository = repository;
    }

    @Override
    public void validateAttributes(AccountEnvironmentDTO dto, ValidationResult vr) {
        Set<String> publisherNames = new HashSet<>();

        if (dto.publishers() == null || dto.publishers().isEmpty()) {
            vr.addError("publishers", MSG_MIN_PUBLISHER);
        } else {
            int index = 0;
            for (PublisherConfig config : dto.publishers()) {
                String pathPrefix = "publishers[" + index + "]";

                if (!publisherNames.add(config.getPublisher().getName())) {
                    vr.addError(pathPrefix + ".name", MSG_DUPLICATE_PUBLISHER);
                }

                if (config.getOrder() == null || config.getOrder() <= 0) {
                    vr.addError(pathPrefix + ".order", MSG_ORDER_REQUIRED);
                }

                JsonNode jsonNode = schemaValidator.fromString(config.getParameters());
                validatePublisherSchema(config.getPublisher(), jsonNode, pathPrefix + ".parameters", vr);

                index++;
            }
        }

        validateIntegrity(dto, vr);
    }


    @Override
    protected boolean recordExists(AccountEnvironmentIdDTO identifier) {
        return repository.findByIdAccountIdAndIdEnvironmentId(identifier.accountId(), identifier.environmentId()).isPresent();
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

        if (dto.environment() != null && EnvironmentTypeEnum.CUSTOM.equals(
                getEnvironmentTypeEnum(dto.environment().getType().getName())
        )) {
            Long accountId = dto.account() == null ? null : dto.account().getId();
            Long envAccountId = dto.environment().getAccount() == null ? null : dto.environment().getAccount().getId();

            if (!Objects.equals(accountId, envAccountId)) {
                vr.addError("account", MSG_INCONSISTENT_ACCOUNT);
            }
        }
    }

    private EnvironmentTypeEnum getEnvironmentTypeEnum(String name) {
        return BaseEnum.from(EnvironmentTypeEnum.class, name);
    }
}
