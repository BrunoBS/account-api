package com.brunobs.core.onboarding.phase;

import com.brunobs.core.catalog.common.BaseTypeMapper;
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
                schemaEngine.fromString(entity.getSettings())
        );
    }
}
