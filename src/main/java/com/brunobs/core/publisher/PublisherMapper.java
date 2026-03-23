package com.brunobs.core.publisher;
import com.brunobs.core.catalog.type.publisherscope.PublisherScopeType;
import com.brunobs.shared.SchemaValidator;
import org.springframework.stereotype.Component;

/**
 * Mapper for Publisher domain.
 * Bridges API DTOs and Database Entities with JSON serialization support.
 */
@Component
public class PublisherMapper {

    private final SchemaValidator schemaEngine;

    public PublisherMapper(SchemaValidator schemaEngine) {
        this.schemaEngine = schemaEngine;
    }

    public Publisher toEntity(PublisherDTO dto, PublisherScopeType scope) {
        if (dto == null) return null;

        Publisher entity = new Publisher();
        updateEntity(entity, scope, dto);
        entity.setId(dto.id()); // O ID só é setado explicitamente na criação se necessário

        return entity;
    }

    public PublisherDTO toDTO(Publisher entity) {
        if (entity == null) return null;

        return new PublisherDTO(
                entity.getId(),
                entity.getName(),
                entity.getLabel(),
                entity.getDescription(),
                schemaEngine.fromString(entity.getJsonSchema()), // Converte String do banco para JsonNode
                entity.getPublisherScope() != null ? entity.getPublisherScope().getName() : null,
                entity.isActive(),
                entity.isDeprecated()
        );
    }

    public void updateEntity(Publisher entity, PublisherScopeType scope, PublisherDTO dto) {
        if (entity == null || dto == null) return;

        entity.setName(dto.name());
        entity.setLabel(dto.label());
        entity.setDescription(dto.description());
        entity.setJsonSchema(schemaEngine.toJsonString(dto.jsonSchema())); // Converte JsonNode para String
        entity.setPublisherScope(scope);
        entity.setActive(dto.active() != null && dto.active());
        entity.setDeprecated(dto.deprecated() != null && dto.deprecated());
    }
}
