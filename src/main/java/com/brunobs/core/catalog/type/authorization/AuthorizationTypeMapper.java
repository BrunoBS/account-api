package com.brunobs.core.catalog.type.authorization;

import com.brunobs.core.catalog.common.BaseTypeMapper;
import com.brunobs.shared.SchemaValidator;
import org.springframework.stereotype.Component;

@Component
public class AuthorizationTypeMapper
        extends BaseTypeMapper<AuthorizationTypeDTO, AuthorizationType, Long> {

    private final SchemaValidator schemaEngine;

    public AuthorizationTypeMapper(SchemaValidator schemaEngine) {
        super(AuthorizationType.class, schemaEngine);
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
                schemaEngine.fromString(entity.getSettings())
        );
    }
}
