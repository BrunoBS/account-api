package com.brunobs.core.catalog.type.applicationscope;

import com.brunobs.shared.base.BaseTypeDTO;
import com.fasterxml.jackson.databind.JsonNode;


public record ApplicationScopeTypeDTO(
        Long id,
        String name,
        String label,
        String description,
        Integer sortOrder,
        JsonNode settings
) implements BaseTypeDTO<ApplicationScopeTypeDTO, Long> {

    @Override
    public ApplicationScopeTypeDTO withId(Long id) {
        return new ApplicationScopeTypeDTO(
                id,
                this.name,
                this.label,
                this.description,
                this.sortOrder,
                this.settings
        );
    }
}
