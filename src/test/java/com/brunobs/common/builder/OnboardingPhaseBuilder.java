package com.brunobs.common.builder;

import com.brunobs.core.onboarding.phase.OnboardingPhaseDTO;

public class OnboardingPhaseBuilder extends BaseCatalogBuilder<OnboardingPhaseDTO, OnboardingPhaseBuilder> {


    private String orientation =
            faker.lorem().paragraph();

    public OnboardingPhaseBuilder() {
        this.name = faker.options().option(
                "ACCOUNT_REGISTRATION",
                "ACCOUNT_FIRST_ENVIRONMENT",
                "FIRST_APPLICATION_REGISTRATION",
                "APPLICATION_FIRST_ENVIRONMENT"
        );
    }

    public static OnboardingPhaseBuilder builder() {
        return new OnboardingPhaseBuilder();
    }

    public OnboardingPhaseBuilder withOrientation(
            String orientation
    ) {
        this.orientation = orientation;
        return this;
    }

    @Override
    public OnboardingPhaseDTO build() {
        return new OnboardingPhaseDTO(
                id,
                name,
                label,
                description,
                sortOrder,
                orientation,
                settings
        );
    }
}