package com.brunobs.core.onboarding;

import com.fasterxml.jackson.annotation.JsonProperty;

public interface OnboardingProgressProjection {
    @JsonProperty(index = 1)
    Integer getOrderPhase();

    @JsonProperty(index = 2)
    String getName();

    @JsonProperty(index = 3)
    String getLabel();

    @JsonProperty(index = 4)
    String getDescription();

    @JsonProperty(index = 5)
    String getOrientation();

    @JsonProperty(index = 6)
    OnboardingStatusEnum getStatus();
}