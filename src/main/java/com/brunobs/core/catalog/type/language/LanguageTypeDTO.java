package com.brunobs.core.catalog.type.language;

import com.brunobs.shared.base.BaseTypeDTO;

/**
 * Data Transfer Object for Programming Language Type catalog.
 * 'name' stores the technical constant (e.g., JAVA)
 * 'label' stores the display name (e.g., Java Enterprise).
 */
public record LanguageTypeDTO(
        Long id,
        String name,
        String label,
        String description,
        Integer sortOrder
) implements BaseTypeDTO<LanguageTypeDTO, Long> {

    @Override
    public LanguageTypeDTO withId(Long id) {
        return new LanguageTypeDTO(
                id,
                this.name,
                this.label,
                this.description,
                this.sortOrder
        );
    }
}
