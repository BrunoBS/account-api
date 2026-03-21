package com.brunobs.core.onboarding.type;

import com.brunobs.shared.BaseTypeDTO;

/**
 * Data Transfer Object for Onboarding Type catalog.
 * Represents different flows of onboarding (e.g., USER, ACCOUNT).
 */
public record OnboardingTypeDTO(
        Long id,
        String name,
        String label,
        String description,
        Integer sortOrder,
        boolean active
) implements BaseTypeDTO<OnboardingTypeDTO, Long> {

    @Override
    public OnboardingTypeDTO withId(Long id) {
        return new OnboardingTypeDTO(
                id,
                this.name,
                this.label,
                this.description,
                this.sortOrder,
                this.active
        );
    }
}
