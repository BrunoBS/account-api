package com.brunobs.core.catalog.type.authorization;

import com.brunobs.shared.base.BaseTypeDTO;
import com.fasterxml.jackson.databind.JsonNode;


public record AuthorizationTypeDTO(
        Long id,
        String name,
        String label,
        String description,
        Integer sortOrder,
        JsonNode settings
) implements BaseTypeDTO<AuthorizationTypeDTO, Long> {

    @Override
    public AuthorizationTypeDTO withId(Long id) {
        return new AuthorizationTypeDTO(
                id,
                this.name,
                this.label,
                this.description,
                this.sortOrder,
                this.settings
        );
    }
}
