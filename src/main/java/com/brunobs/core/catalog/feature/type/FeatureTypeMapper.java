package com.brunobs.core.catalog.feature.type;

import com.brunobs.core.catalog.common.BaseTypeMapper;
import com.brunobs.core.catalog.feature.scope.FeatureScopeType;
import com.brunobs.core.catalog.feature.scope.FeatureScopeTypeRepository;
import com.brunobs.core.catalog.feature.scope.FeatureScopeTypeService;
import com.brunobs.shared.SchemaValidator;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.stereotype.Component;

@Component
public class FeatureTypeMapper extends BaseTypeMapper<FeatureTypeDTO, FeatureType, Long> {

    private final SchemaValidator schemaEngine;
    private final FeatureScopeTypeService featureScopeTypeService;

    public FeatureTypeMapper(SchemaValidator schemaEngine, FeatureScopeTypeService featureScopeTypeService) {
        super(FeatureType.class, schemaEngine);
        this.schemaEngine = schemaEngine;
        this.featureScopeTypeService = featureScopeTypeService;
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


    private void mapAdditionalFields(FeatureType entity, FeatureTypeDTO dto) {
        entity.setSettings(schemaEngine.toJsonString(dto.settings()));
        entity.setAvailable(dto.available() != null && dto.available());
        if (dto.scope() != null) {
            FeatureScopeType byName = featureScopeTypeService.findByName(dto.scope());
            entity.setFeatureScope(byName);
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
                schemaEngine.fromString(entity.getSettings()),
                entity.getFeatureScope() != null ? entity.getFeatureScope().getName() : null,
                entity.isAvailable()
        );
    }
}
