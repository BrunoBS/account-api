package com.brunobs.core.onboarding.phase;
import com.brunobs.core.catalog.common.BaseTypeValidator;
import com.brunobs.shared.BaseEnum;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;


@Component
public class OnboardingPhaseValidator extends BaseTypeValidator<
        OnboardingPhaseEnum,
        OnboardingPhaseRepository,
        OnboardingPhaseDTO> {

    public OnboardingPhaseValidator(OnboardingPhaseRepository repository, MessageSource messageSource) {
        super(repository, OnboardingPhaseEnum.class, messageSource);
    }

    @Override
    public Long getId(OnboardingPhaseDTO dto) {
        return dto.id();
    }

    @Override
    public String getName(OnboardingPhaseDTO dto) {
        return dto.name();
    }

    @Override
    public String getLabel(OnboardingPhaseDTO dto) {
        return dto.label();
    }

    @Override
    public String getDescription(OnboardingPhaseDTO dto) {
        return dto.description();
    }

    @Override
    public OnboardingPhaseEnum getEnum(String name) {
        return BaseEnum.from(OnboardingPhaseEnum.class, name);
    }
}
