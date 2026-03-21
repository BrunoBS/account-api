package com.brunobs.core.catalog.type.schema;

import com.brunobs.shared.BaseTypeDTO;
import com.fasterxml.jackson.databind.JsonNode;

/**
 * Data Transfer Object for JSON Schema definitions.
 * 'jsonSchema' stores the structural rules used to validate other dynamic fields.
 */
public record SchemaTypeDTO(
        Long id,
        String name,
        String label,
        String description,
        Integer sortOrder,
        boolean active,
        JsonNode jsonSchema // Traduzido de 'schema' para maior clareza técnica
) implements BaseTypeDTO<SchemaTypeDTO, Long> {

    @Override
    public SchemaTypeDTO withId(Long id) {
        return new SchemaTypeDTO(
                id,
                this.name,
                this.label,
                this.description,
                this.sortOrder,
                this.active,
                this.jsonSchema
        );
    }
}
