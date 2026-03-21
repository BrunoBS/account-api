package com.brunobs.core.onboarding.type;
import com.brunobs.core.catalog.common.BaseTypeValidator;
import com.brunobs.shared.BaseEnum;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

/**
 * Validator for OnboardingType catalog.
 * Ensures the DTO complies with business rules and Enum constants before saving.
 */
@Component
public class OnboardingTypeValidator extends BaseTypeValidator<
        OnboardingTypeEnum,
        OnboardingTypeRepository,
        OnboardingTypeDTO> {

    public OnboardingTypeValidator(OnboardingTypeRepository repository, MessageSource messageSource) {
        super(repository, OnboardingTypeEnum.class, messageSource);
    }

    @Override
    public Long getId(OnboardingTypeDTO dto) {
        return dto.id();
    }

    @Override
    public String getName(OnboardingTypeDTO dto) {
        return dto.name();
    }

    @Override
    public String getLabel(OnboardingTypeDTO dto) {
        return dto.label();
    }

    @Override
    public String getDescription(OnboardingTypeDTO dto) {
        return dto.description();
    }

    @Override
    public OnboardingTypeEnum getEnum(String name) {
        return BaseEnum.from(OnboardingTypeEnum.class, name);
    }
}
