package com.brunobs.core.catalog.type.applicationscope;

import com.brunobs.shared.BaseTypeDTO;

/**
 * Data Transfer Object for Application Scope Type catalog.
 * Implements BaseTypeDTO for standardized catalog operations.
 */
public record ApplicationScopeTypeDTO(
        Long id,
        String name,
        String label,
        String description,
        Integer sortOrder,
        boolean active
) implements BaseTypeDTO<ApplicationScopeTypeDTO, Long> {

    @Override
    public ApplicationScopeTypeDTO withId(Long id) {
        return new ApplicationScopeTypeDTO(
                id,
                this.name,
                this.label,
                this.description,
                this.sortOrder,
                this.active
        );
    }
}
