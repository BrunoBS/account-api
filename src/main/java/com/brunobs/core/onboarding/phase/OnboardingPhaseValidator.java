package com.brunobs.core.onboarding.phase;

import com.brunobs.core.catalog.common.BaseTypeValidator;
import com.brunobs.message.feature.CatalogMessages;
import com.brunobs.shared.base.BaseEnum;
import org.springframework.stereotype.Component;


@Component
public class OnboardingPhaseValidator extends BaseTypeValidator<
        OnboardingPhaseEnum,
        OnboardingPhaseRepository,
        OnboardingPhaseDTO> {

    public OnboardingPhaseValidator(OnboardingPhaseRepository repository, CatalogMessages catalogMessages) {
        super(repository, OnboardingPhaseEnum.class, catalogMessages);
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
