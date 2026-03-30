package com.brunobs.core.catalog.feature.scope;


import com.brunobs.core.catalog.common.BaseTypeMapper;
import com.brunobs.shared.SchemaValidator;
import org.springframework.stereotype.Component;

@Component
public class FeatureScopeTypeMapper
        extends BaseTypeMapper<FeatureScopeTypeDTO, FeatureScopeType, Long> {
    private final SchemaValidator schemaEngine;

    public FeatureScopeTypeMapper(SchemaValidator schemaEngine) {
        super(FeatureScopeType.class, schemaEngine);
        this.schemaEngine = schemaEngine;
    }

    @Override
    public FeatureScopeTypeDTO toDTO(FeatureScopeType entity) {
        if (entity == null) return null;

        return new FeatureScopeTypeDTO(
                entity.getId(),
                entity.getName(),
                entity.getLabel(),
                entity.getDescription(),
                entity.getSortOrder(),
                schemaEngine.fromString(entity.getSettings())
        );
    }
}
