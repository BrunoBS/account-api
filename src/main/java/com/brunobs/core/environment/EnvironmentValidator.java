package com.brunobs.core.environment;

import com.brunobs.core.catalog.type.authorization.AuthorizationTypeEnum;
import com.brunobs.core.catalog.type.environment.EnvironmentTypeEnum; // TipoAmbienteEnum -> EnvironmentTypeEnum
import com.brunobs.shared.BaseEnum;
import com.brunobs.shared.validation.BaseValidator;
import com.brunobs.shared.validation.ValidationResult;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

@Component
public class EnvironmentValidator extends BaseValidator<EnvironmentDTO, Long> {

    // Chaves i18n (messages_en.properties)
    public static final String MSG_NAME_REQUIRED = "validator.environment.name-required";
    public static final String MSG_NAME_INVALID = "validator.environment.name-invalid";
    public static final String MSG_DESCRIPTION_REQUIRED = "validator.environment.description-required";
    public static final String MSG_DESCRIPTION_INVALID = "validator.environment.description-invalid";
    public static final String MSG_CUSTOM_MISSING_ACCOUNT = "validator.environment.custom-missing-account";
    public static final String MSG_DUPLICATED_DEFAULT = "validator.environment.duplicated-default";
    public static final String MSG_DUPLICATED_CUSTOM = "validator.environment.duplicated-custom";
    public static final String MSG_ACCOUNT_INVALID_CUSTOM = "validator.environment.account-invalid-custom";

    private final EnvironmentRepository repository;

    public EnvironmentValidator(EnvironmentRepository repository, MessageSource messageSource) {
        super(messageSource);
        this.repository = repository;
    }

    @Override
    public void validateIntegrity(EnvironmentDTO dto, ValidationResult vr) {
        Long id = dto.id() == null ? 0L : dto.id();
        EnvironmentTypeEnum typeEnum = getEnvironmentTypeEnum(dto.environmentType());

        if (typeEnum != null) {
            if (EnvironmentTypeEnum.DEFAULT.equals(typeEnum)) {
                // Valida nome duplicado para ambientes globais (DEFAULT)
                if (repository.existsByNameAndTypeNameAndIdNot(dto.name(), EnvironmentTypeEnum.DEFAULT.name(), id)) {
                    vr.addError("name", MSG_DUPLICATED_DEFAULT);
                }
            } else if (EnvironmentTypeEnum.CUSTOM.equals(typeEnum)) {
                // Valida nome duplicado para ambientes por conta (CUSTOM)
                if (dto.accountId() != null && repository.existsByNameAndAccountIdAndIdNot(dto.name(), dto.accountId(), id)) {
                    vr.addError("name", MSG_DUPLICATED_CUSTOM);
                }
                // Garante que CUSTOM sempre tenha conta
                if (dto.accountId() == null) {
                    vr.addError("accountId", MSG_ACCOUNT_INVALID_CUSTOM);
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
            vr.addError("name", MSG_NAME_REQUIRED);
        } else if (dto.name().length() < 3 || dto.name().length() > 50) {
            vr.addError("name", MSG_NAME_INVALID);
        }

        // Description Validation
        if (dto.description() == null || dto.description().isBlank()) {
            vr.addError("description", MSG_DESCRIPTION_REQUIRED);
        } else if (dto.description().length() < 3 || dto.description().length() > 250) {
            vr.addError("description", MSG_DESCRIPTION_INVALID);
        }

        // Type Validation
        if (typeEnum == null) {
            vr.addError("type", BaseEnum.getOptionsValid(EnvironmentTypeEnum.class, messageSource));
        } else if (EnvironmentTypeEnum.CUSTOM.equals(typeEnum) && dto.accountId() == null) {
            vr.addError("accountId", MSG_CUSTOM_MISSING_ACCOUNT);
        }

        // Authorization Validation
        if (authEnum == null) {
            vr.addError("authorization", BaseEnum.getOptionsValid(AuthorizationTypeEnum.class, messageSource));
        }
    }

    @Override
    protected String entityName() {
        return Environment.class.getSimpleName();
    }

    @Override
    protected boolean recordExists(Long id) {
        return repository.existsById(id);
    }

    private EnvironmentTypeEnum getEnvironmentTypeEnum(String name) {
        return BaseEnum.from(EnvironmentTypeEnum.class, name);
    }

    private AuthorizationTypeEnum getAuthorizationTypeEnum(String name) {
        return BaseEnum.from(AuthorizationTypeEnum.class, name);
    }
}
