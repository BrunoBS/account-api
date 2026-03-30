package com.brunobs.core.catalog.type.schema;

import com.brunobs.shared.base.BaseTypeDTO;
import com.fasterxml.jackson.databind.JsonNode;

public record SchemaTypeDTO(
        Long id,
        String name,
        String label,
        String description,
        Integer sortOrder,
        JsonNode jsonSchema,
        JsonNode settings
) implements BaseTypeDTO<SchemaTypeDTO, Long> {

    @Override
    public SchemaTypeDTO withId(Long id) {
        return new SchemaTypeDTO(
                id,
                this.name,
                this.label,
                this.description,
                this.sortOrder,
                this.jsonSchema,
                this.settings
        );
    }
}
