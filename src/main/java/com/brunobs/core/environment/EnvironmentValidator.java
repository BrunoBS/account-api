package com.brunobs.core.environment;

import com.brunobs.core.catalog.type.authorization.AuthorizationTypeEnum;
import com.brunobs.core.catalog.type.environment.EnvironmentTypeEnum;
import com.brunobs.core.catalog.type.schema.SchemaType;
import com.brunobs.core.catalog.type.schema.SchemaTypeEnum;
import com.brunobs.core.catalog.type.schema.SchemaTypeRepository;
import com.brunobs.core.configuration.environment.account.dto.AccountEnvironmentDTO;
import com.brunobs.exception.ValidationException;
import com.brunobs.message.feature.EnvironmentMessages;
import com.brunobs.shared.SchemaValidator;
import com.brunobs.shared.base.BaseEnum;
import com.brunobs.shared.base.BaseValidator;
import com.brunobs.shared.validation.ValidationResult;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class EnvironmentValidator extends BaseValidator<EnvironmentDTO, Long> {


    private final EnvironmentRepository repository;
    private final EnvironmentMessages environmentMessages;
    private final SchemaValidator schemaEngine;
    private final SchemaTypeRepository schemaTypeRepository;

    public EnvironmentValidator(EnvironmentRepository repository, EnvironmentMessages environmentMessages, SchemaValidator schemaEngine, SchemaTypeRepository schemaTypeRepository) {

        this.repository = repository;
        this.environmentMessages = environmentMessages;
        this.schemaEngine = schemaEngine;
        this.schemaTypeRepository = schemaTypeRepository;
    }

    @Override
    public void validateIntegrity(EnvironmentDTO dto, ValidationResult vr) {
        Long id = dto.id() == null ? 0L : dto.id();
        EnvironmentTypeEnum typeEnum = getEnvironmentTypeEnum(dto.environmentType());

        if (typeEnum != null) {
            if (EnvironmentTypeEnum.DEFAULT.equals(typeEnum)) {
                if (repository.existsByNameAndTypeNameAndIdNotAndActiveTrue(dto.name(), EnvironmentTypeEnum.DEFAULT.name(), id)) {
                    vr.addError("name", environmentMessages.duplicatedDefault(dto.name()));
                }
            } else if (EnvironmentTypeEnum.CUSTOM.equals(typeEnum)) {
                if (dto.accountId() != null && repository.existsByNameAndAccountIdAndIdNotAndActiveTrue(dto.name(), dto.accountId(), id)) {
                    vr.addError("name", environmentMessages.duplicatedCustom(dto.name()));
                }

                if (dto.accountId() == null) {
                    vr.addError("account", environmentMessages.accountInvalidCustom());
                }
            }
        }
    }

    @Override
    public void validateAttributes(EnvironmentDTO dto, ValidationResult vr) {
        EnvironmentTypeEnum typeEnum = getEnvironmentTypeEnum(dto.environmentType());
        AuthorizationTypeEnum authEnum = getAuthorizationTypeEnum(dto.authorizationType());

        // Name Validation
        if (dto.name() == null || dto.name().isBlank()) {
            vr.addError("name", environmentMessages.nameRequired());
        } else if (dto.name().length() < 3 || dto.name().length() > 50) {
            vr.addError("name", environmentMessages.nameInvalid(3, 50));
        }

        // Description Validation
        if (dto.description() == null || dto.description().isBlank()) {
            vr.addError("description", environmentMessages.descriptionRequired());
        } else if (dto.description().length() < 3 || dto.description().length() > 250) {
            vr.addError("description", environmentMessages.descriptionInvalid(3, 50));

        }


        if (typeEnum == null) {
            List<EnvironmentTypeEnum> opcoesValidas = Arrays.stream(EnvironmentTypeEnum.values()).toList();
            String validTypes = getListOr(opcoesValidas, environmentMessages.getMessageSource());
            vr.addError("accountType", environmentMessages.typeInvalid(validTypes));
        }


        if (authEnum == null) {
            List<AuthorizationTypeEnum> opcoesValidas = Arrays.stream(AuthorizationTypeEnum.values()).toList();
            String validAuthTypes = getListOr(opcoesValidas, environmentMessages.getMessageSource());
            vr.addError("accountType", environmentMessages.authorizationInvalid(validAuthTypes));

        }

        if (EnvironmentTypeEnum.DEFAULT.equals(typeEnum) && dto.accountId() != null) {
            throw new ValidationException(new ValidationResult("account", environmentMessages.accountInvalidDefault()));
        }
        validateAdditionalFields(dto, vr);
    }

    public void validateAdditionalFields(EnvironmentDTO dto, ValidationResult vr) {
        schemaEngine.validateJson(getJsonSchema(dto), dto.settings(), "settings", vr);
    }

    private String getJsonSchema(EnvironmentDTO dto) {
        EnvironmentTypeEnum environmentTypeEnum = getEnvironmentTypeEnum(dto.environmentType());
        if (environmentTypeEnum == null) {
            return SchemaTypeEnum.DEFAULT_JSON_SCHEMA;
        }

        SchemaTypeEnum schemaTypeEnum = EnvironmentTypeEnum.DEFAULT.equals(environmentTypeEnum) ?
                SchemaTypeEnum.ENVIRONMENT_DEFAULT : SchemaTypeEnum.ENVIRONMENT_CUSTOM;

        return schemaTypeRepository.findByNameAndActiveTrue(schemaTypeEnum.name())
                .map(SchemaType::getJsonSchema)
                .orElse(SchemaTypeEnum.DEFAULT_JSON_SCHEMA);
    }

    @Override
    public String recordRequired() {
        return environmentMessages.notFound();
    }

    @Override
    public String entityName() {
        return Environment.class.getSimpleName();
    }


    private EnvironmentTypeEnum getEnvironmentTypeEnum(String name) {
        return BaseEnum.from(EnvironmentTypeEnum.class, name);
    }

    private AuthorizationTypeEnum getAuthorizationTypeEnum(String name) {
        return BaseEnum.from(AuthorizationTypeEnum.class, name);
    }
}
