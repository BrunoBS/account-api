package com.brunobs.core.catalog.type.language;

import com.brunobs.shared.base.BaseTypeDTO;
import com.fasterxml.jackson.databind.JsonNode;

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
        Integer sortOrder,
        JsonNode settings

) implements BaseTypeDTO<LanguageTypeDTO, Long> {

    @Override
    public LanguageTypeDTO withId(Long id) {
        return new LanguageTypeDTO(
                id,
                this.name,
                this.label,
                this.description,
                this.sortOrder,
                this.settings
        );
    }
}
