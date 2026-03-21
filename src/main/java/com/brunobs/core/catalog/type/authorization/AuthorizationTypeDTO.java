package com.brunobs.core.catalog.type.authorization;

import com.brunobs.shared.BaseTypeDTO;
import com.fasterxml.jackson.databind.JsonNode;


public record AuthorizationTypeDTO(
        Long id,
        String name,
        String label,
        String description,
        Integer sortOrder,
        boolean active,
        JsonNode settings // Traduzido de 'configuracoes'
) implements BaseTypeDTO<AuthorizationTypeDTO, Long> {

    @Override
    public AuthorizationTypeDTO withId(Long id) {
        return new AuthorizationTypeDTO(
                id,
                this.name,
                this.label,
                this.description,
                this.sortOrder,
                this.active,
                this.settings
        );
    }
}
