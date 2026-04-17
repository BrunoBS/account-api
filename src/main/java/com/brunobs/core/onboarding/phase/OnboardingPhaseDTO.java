package com.brunobs.core.onboarding.phase;

import com.brunobs.shared.base.BaseTypeDTO;
import com.fasterxml.jackson.databind.JsonNode;

/**
 * Data Transfer Object for Onboarding Type catalog.
 * Represents different flows of onboarding (e.g., USER, ACCOUNT).
 */
public record OnboardingPhaseDTO(
        Long id,
        String name,
        String label,
        String description,
        Integer sortOrder,
        String orientation,
        JsonNode settings
) implements BaseTypeDTO<OnboardingPhaseDTO, Long> {

    @Override
    public OnboardingPhaseDTO withId(Long id) {
        return new OnboardingPhaseDTO(
                id,
                this.name,
                this.label,
                this.description,
                this.sortOrder,
                orientation,
                this.settings
        );
    }
}
