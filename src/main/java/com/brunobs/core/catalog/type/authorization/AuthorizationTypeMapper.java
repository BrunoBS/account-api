package com.brunobs.core.catalog.type.authorization;
import com.brunobs.core.catalog.common.BaseTypeMapper;
import com.brunobs.shared.validation.SchemaValidator;
import org.springframework.stereotype.Component;

/**
 * Mapper for AuthorizationType catalog.
 * Handles the conversion between JsonNode (API) and String (Database) for settings.
 */
@Component
public class AuthorizationTypeMapper
        extends BaseTypeMapper<AuthorizationTypeDTO, AuthorizationType, Long> {

    private final SchemaValidator schemaEngine;

    public AuthorizationTypeMapper(SchemaValidator schemaEngine) {
        super(AuthorizationType.class);
        this.schemaEngine = schemaEngine;
    }

    @Override
    public AuthorizationTypeDTO toDTO(AuthorizationType entity) {
        if (entity == null) return null;

        return new AuthorizationTypeDTO(
                entity.getId(),
                entity.getName(),
                entity.getLabel(),
                entity.getDescription(),
                entity.getSortOrder(),
                entity.isActive(),
                schemaEngine.fromString(entity.getSettings())
        );
    }

    @Override
    public AuthorizationType toEntity(AuthorizationTypeDTO dto) {
        AuthorizationType entity = super.toEntity(dto);
        // Converte o JsonNode do DTO para String para salvar no banco
        entity.setSettings(schemaEngine.toJsonString(dto.settings()));
        return entity;
    }

    @Override
    public void updateEntity(AuthorizationType entity, AuthorizationTypeDTO dto) {
        super.updateEntity(entity, dto);
         entity.setSettings(schemaEngine.toJsonString(dto.settings()));
    }
}
