package com.brunobs.core.onboarding.status;

import com.brunobs.shared.BaseTypeDTO;

/**
 * Data Transfer Object for Onboarding Type catalog.
 * Represents different flows of onboarding (e.g., USER, ACCOUNT).
 */
public record OnboardingStatusDTO(
        Long id,
        String name,
        String label,
        String description,
        Integer sortOrder,
        boolean active
) implements BaseTypeDTO<OnboardingStatusDTO, Long> {

    @Override
    public OnboardingStatusDTO withId(Long id) {
        return new OnboardingStatusDTO(
                id,
                this.name,
                this.label,
                this.description,
                this.sortOrder,
                this.active
        );
    }
}
