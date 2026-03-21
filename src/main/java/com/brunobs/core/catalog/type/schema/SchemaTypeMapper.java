package com.brunobs.core.catalog.type.schema;


import com.brunobs.core.catalog.common.BaseTypeMapper;
import com.brunobs.shared.validation.SchemaValidator;
import org.springframework.stereotype.Component;

/**
 * Mapper for SchemaType catalog.
 * Handles the conversion between JsonNode (API) and String (Database) for JSON Schemas.
 */
@Component
public class SchemaTypeMapper
        extends BaseTypeMapper<SchemaTypeDTO, SchemaType, Long> {

    private final SchemaValidator schemaEngine;

    public SchemaTypeMapper(SchemaValidator schemaEngine) {
        super(SchemaType.class);
        this.schemaEngine = schemaEngine;
    }

    @Override
    public SchemaTypeDTO toDTO(SchemaType entity) {
        if (entity == null) return null;

        return new SchemaTypeDTO(
                entity.getId(),
                entity.getName(),
                entity.getLabel(),
                entity.getDescription(),
                entity.getSortOrder(),
                entity.isActive(),
                schemaEngine.fromString(entity.getJsonSchema())
        );
    }

    @Override
    public SchemaType toEntity(SchemaTypeDTO dto) {
        SchemaType entity = super.toEntity(dto);
        entity.setJsonSchema(schemaEngine.toJsonString(dto.jsonSchema()));
        return entity;
    }

    @Override
    public void updateEntity(SchemaType entity, SchemaTypeDTO dto) {
        super.updateEntity(entity, dto);

        entity.setJsonSchema(schemaEngine.toJsonString(dto.jsonSchema()));
    }
}
