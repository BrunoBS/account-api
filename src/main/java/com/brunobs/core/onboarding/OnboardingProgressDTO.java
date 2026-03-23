package com.brunobs.core.onboarding;

public class OnboardingProgressDTO {
    private String fase;
    private OnboardingStatusEnum status;

    public OnboardingProgressDTO(String fase, OnboardingStatusEnum status){
        this.fase = fase;
        this.status = status;
    }

    // getters e setters
}