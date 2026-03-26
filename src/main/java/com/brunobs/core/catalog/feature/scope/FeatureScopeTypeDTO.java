package com.brunobs.core.catalog.feature.scope;

import com.brunobs.shared.base.BaseTypeDTO;

/**
 * Data Transfer Object for Feature Scope Type catalog.
 * Defines the scope of a feature (e.g., ACCOUNT, SHARED_APPLICATION).
 */
public record FeatureScopeTypeDTO(
        Long id,
        String name,
        String label,
        String description,
        Integer sortOrder
) implements BaseTypeDTO<FeatureScopeTypeDTO, Long> {

    @Override
    public FeatureScopeTypeDTO withId(Long id) {
        return new FeatureScopeTypeDTO(
                id,
                this.name,
                this.label,
                this.description,
                this.sortOrder
        );
    }
}
