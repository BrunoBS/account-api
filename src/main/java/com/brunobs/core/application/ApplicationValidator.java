package com.brunobs.core.application;

import com.brunobs.core.catalog.type.applicationscope.ApplicationScopeTypeEnum;
import com.brunobs.core.catalog.type.infrastructure.InfrastructureTypeEnum;
import com.brunobs.core.catalog.type.language.LanguageTypeEnum;
import com.brunobs.shared.BaseEnum;
import com.brunobs.shared.validation.BaseValidator;
import com.brunobs.shared.validation.ValidationResult;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

@Component
public class ApplicationValidator extends BaseValidator<ApplicationDTO, Long> {

    // Chaves de i18n para mensagens (messages_en.properties)
    public static final String MSG_NAME_REQUIRED = "validator.application.name-required";
    public static final String MSG_NAME_INVALID = "validator.application.name-invalid";
    public static final String MSG_NAME_DUPLICATED = "validator.application.name-duplicated";
    public static final String MSG_ALIAS_REQUIRED = "validator.application.alias-required";
    public static final String MSG_ACCOUNT_REQUIRED = "validator.application.account-required";

    private final ApplicationRepository repository;

    public ApplicationValidator(ApplicationRepository repository, MessageSource messageSource) {
        super(messageSource);
        this.repository = repository;
    }

    @Override
    public void validateAttributes(ApplicationDTO dto, ValidationResult vr) {
        ApplicationScopeTypeEnum scopeEnum = getApplicationScopeTypeEnum(dto.applicationScopeType());
        LanguageTypeEnum languageEnum = getLanguageTypeEnum(dto.languageType());
        InfrastructureTypeEnum infraEnum = getInfrastructureTypeEnum(dto.infrastructureType());

        // Name Validation
        if (dto.name() == null || dto.name().isBlank()) {
            vr.addError("name", MSG_NAME_REQUIRED);
        } else if (dto.name().length() < 3 || dto.name().length() > 100) {
            vr.addError("name", MSG_NAME_INVALID);
        }

        // Alias Validation
        if (dto.alias() == null || dto.alias().isBlank()) {
            vr.addError("alias", MSG_ALIAS_REQUIRED);
        }

        // Account Association Validation
        if (dto.accountId() == null) {
            vr.addError("accountId", MSG_ACCOUNT_REQUIRED);
        }

        // Enum Validations (Catalog)
        if (languageEnum == null) {
            vr.addError("languageType", BaseEnum.getOptionsValid(LanguageTypeEnum.class, messageSource));
        }

        if (scopeEnum == null) {
            vr.addError("applicationScopeType", BaseEnum.getOptionsValid(ApplicationScopeTypeEnum.class, messageSource));
        }

        if (infraEnum == null) {
            vr.addError("infrastructureType", BaseEnum.getOptionsValid(InfrastructureTypeEnum.class, messageSource));
        }
    }

    @Override
    protected void validateIntegrity(ApplicationDTO dto, ValidationResult vr) {
        Long id = dto.registrationIdentifier() == null ? 0L : dto.registrationIdentifier();
        // Valida duplicidade de nome dentro da mesma conta
        if (dto.name() != null && dto.accountId() != null &&
                repository.existsByNameAndAccountIdAndIdNot(dto.name(), dto.accountId(), id)) {
            vr.addError("name", MSG_NAME_DUPLICATED);
        }
    }

    @Override
    protected boolean recordExists(Long id) {
        return repository.existsById(id);
    }

    @Override
    protected String entityName() {
        return Application.class.getSimpleName();
    }

    private ApplicationScopeTypeEnum getApplicationScopeTypeEnum(String name) {
        return BaseEnum.from(ApplicationScopeTypeEnum.class, name);
    }

    private LanguageTypeEnum getLanguageTypeEnum(String name) {
        return BaseEnum.from(LanguageTypeEnum.class, name);
    }

    private InfrastructureTypeEnum getInfrastructureTypeEnum(String name) {
        return BaseEnum.from(InfrastructureTypeEnum.class, name);
    }
}
