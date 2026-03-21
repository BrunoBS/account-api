package com.brunobs.core.onboarding.type;

public interface OnboardingProgressProjection {
    Long getId();
    String getName();
    Integer getOrder();
    String getStatus(); // Retornará 'COMPLETED' ou null
}