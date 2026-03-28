package com.brunobs.core.application;

import com.brunobs.core.catalog.type.applicationscope.ApplicationScopeTypeEnum;
import com.brunobs.core.catalog.type.infrastructure.InfrastructureTypeEnum;
import com.brunobs.core.catalog.type.language.LanguageTypeEnum;
import com.brunobs.message.feature.ApplicationMessages;
import com.brunobs.shared.base.BaseEnum;
import com.brunobs.shared.base.BaseValidator;
import com.brunobs.shared.validation.ValidationResult;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class ApplicationValidator extends BaseValidator<ApplicationDTO, Long> {


    private final ApplicationRepository repository;
    private final ApplicationMessages applicationMessages;


    public ApplicationValidator(ApplicationRepository repository, ApplicationMessages applicationMessages) {
        this.repository = repository;
        this.applicationMessages = applicationMessages;
    }

    @Override
    public void validateAttributes(ApplicationDTO dto, ValidationResult vr) {
        ApplicationScopeTypeEnum scopeEnum = getApplicationScopeTypeEnum(dto.applicationScopeType());
        LanguageTypeEnum languageEnum = getLanguageTypeEnum(dto.languageType());
        InfrastructureTypeEnum infraEnum = getInfrastructureTypeEnum(dto.infrastructureType());


        if (dto.name() == null || dto.name().isBlank()) {
            vr.addError("name", applicationMessages.nameRequired());
        } else if (dto.name().length() < 3 || dto.name().length() > 100) {
            vr.addError("name", applicationMessages.nameInvalid());
        }

        if (dto.alias() == null || dto.alias().isBlank()) {
            vr.addError("alias", applicationMessages.aliasRequired());
        }

        if (dto.accountId() == null) {
            vr.addError("accountId", applicationMessages.accountRequired());
        }

        if (languageEnum == null) {
            List<LanguageTypeEnum> list = Arrays.stream(LanguageTypeEnum.values()).toList();
            String languagesNamesValid = getListOr(list, applicationMessages.getMessageSource());
            vr.addError("languageType", applicationMessages.languageInvalid(languagesNamesValid));
        }

        if (scopeEnum == null) {
            List<ApplicationScopeTypeEnum> list = Arrays.stream(ApplicationScopeTypeEnum.values()).toList();
            String scopeNamesValid = getListOr(list, applicationMessages.getMessageSource());
            vr.addError("applicationScopeType", applicationMessages.scopeInvalid(scopeNamesValid));
        }

        if (infraEnum == null) {
            List<InfrastructureTypeEnum> list = Arrays.stream(InfrastructureTypeEnum.values()).toList();
            String infrastructureNamesValid = getListOr(list, applicationMessages.getMessageSource());
            vr.addError("infrastructureType", applicationMessages.infrastructureInvalid(infrastructureNamesValid));
        }
    }

    @Override
    protected void validateIntegrity(ApplicationDTO dto, ValidationResult vr) {
        Long id = dto.registrationIdentifier() == null ? 0L : dto.registrationIdentifier();
        if (dto.name() != null && dto.accountId() != null &&
                repository.existsByNameAndAccountIdAndDeletedAtIsNullAndAccountDeletedAtIsNullAndIdNot(dto.name(), dto.accountId(), id)) {
            vr.addError("name", applicationMessages.applicationExists());
        }
    }


    @Override
    public String recordRequired() {
        return applicationMessages.recordRequired();
    }

    @Override
    public String entityName() {
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
