package com.brunobs.client;

import com.brunobs.core.onboarding.phase.OnboardingPhaseDTO;

public class OnboardingPhaseClient
        extends BaseCatalogClient<OnboardingPhaseDTO> {

    private static final String BASE_PATH =
            "/api/v1/onboarding-type";

    private OnboardingPhaseClient() {
        super(BASE_PATH, OnboardingPhaseDTO.class);
    }

    public static OnboardingPhaseClient client() {
        return new OnboardingPhaseClient();
    }
}