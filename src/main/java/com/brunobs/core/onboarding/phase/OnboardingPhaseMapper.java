package com.brunobs.core.onboarding.phase;

import com.brunobs.core.catalog.common.BaseTypeMapper;
import com.brunobs.core.catalog.feature.scope.FeatureScopeType;
import com.brunobs.core.catalog.feature.type.FeatureType;
import com.brunobs.core.catalog.feature.type.FeatureTypeDTO;
import com.brunobs.shared.SchemaValidator;
import org.springframework.stereotype.Component;

/**
 * Mapper for OnboardingType catalog.
 * Leverages BaseTypeMapper to handle common field conversions including 'label'.
 */
@Component
public class OnboardingPhaseMapper
        extends BaseTypeMapper<OnboardingPhaseDTO, OnboardingPhase, Long> {

    private final SchemaValidator schemaEngine;

    public OnboardingPhaseMapper(SchemaValidator schemaEngine) {
        super(OnboardingPhase.class, schemaEngine);
        this.schemaEngine = schemaEngine;
    }

    @Override
    public OnboardingPhaseDTO toDTO(OnboardingPhase entity) {
        if (entity == null) return null;

        return new OnboardingPhaseDTO(
                entity.getId(),
                entity.getName(),
                entity.getLabel(),
                entity.getDescription(),
                entity.getSortOrder(),
                entity.getOrientation(),
                schemaEngine.fromString(entity.getSettings())
        );
    }

    @Override
    public OnboardingPhase toEntity(OnboardingPhaseDTO dto) {
        OnboardingPhase entity = super.toEntity(dto);
        mapAdditionalFields(entity, dto);
        return entity;
    }

    @Override
    public void updateEntity(OnboardingPhase entity, OnboardingPhaseDTO dto) {
        super.updateEntity(entity, dto);
        mapAdditionalFields(entity, dto);
    }

    private void mapAdditionalFields(OnboardingPhase entity, OnboardingPhaseDTO dto) {
        entity.setSettings(schemaEngine.toJsonString(dto.settings()));
        entity.setOrientation(dto.orientation() != null ? dto.orientation() : "");

    }
}
