package com.brunobs.core.catalog.feature.type;

import com.brunobs.core.catalog.common.BaseTypeMapper;
import com.brunobs.core.catalog.feature.scope.FeatureScopeTypeRepository;
import com.brunobs.shared.validation.SchemaValidator;
import org.springframework.stereotype.Component;

/**
 * Mapper for FeatureType.
 * Handles complex mapping between JSON settings, Scope strings, and Entities.
 */
@Component
public class FeatureTypeMapper extends BaseTypeMapper<FeatureTypeDTO, FeatureType, Long> {

    private final SchemaValidator schemaEngine;
    private final FeatureScopeTypeRepository scopeRepository;

    public FeatureTypeMapper(SchemaValidator schemaEngine, FeatureScopeTypeRepository scopeRepository) {
        super(FeatureType.class);
        this.schemaEngine = schemaEngine;
        this.scopeRepository = scopeRepository;
    }

    @Override
    public FeatureType toEntity(FeatureTypeDTO dto) {
        FeatureType entity = super.toEntity(dto);
        mapAdditionalFields(entity, dto);
        return entity;
    }

    @Override
    public void updateEntity(FeatureType entity, FeatureTypeDTO dto) {
        super.updateEntity(entity, dto);
        mapAdditionalFields(entity, dto);
    }

    /**
     * Centralizes logic for complex fields (JSON and Relationships).
     */
    private void mapAdditionalFields(FeatureType entity, FeatureTypeDTO dto) {
        // Convert JsonNode to String for Database
        entity.setSettings(schemaEngine.toJsonString(dto.settings()));

        // Logical flag
        entity.setAvailable(dto.available() != null && dto.available());

        // Bridge: Scope Name (String) -> FeatureScope (Entity)
        if (dto.scope() != null) {
            scopeRepository.findByName(dto.scope()).ifPresent(entity::setFeatureScope);
        }
    }

    @Override
    public FeatureTypeDTO toDTO(FeatureType entity) {
        if (entity == null) return null;

        return new FeatureTypeDTO(
                entity.getId(),
                entity.getName(),
                entity.getLabel(),
                entity.getDescription(),
                entity.getSortOrder(),
                entity.isActive(),
                schemaEngine.fromString(entity.getSettings()),
                // Export only the Name of the Scope to the API
                entity.getFeatureScope() != null ? entity.getFeatureScope().getName() : null,
                entity.isAvailable()
        );
    }
}
