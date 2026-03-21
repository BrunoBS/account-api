package com.brunobs.core.onboarding.type;

import com.brunobs.core.catalog.common.BaseTypeMapper;
import org.springframework.stereotype.Component;

/**
 * Mapper for OnboardingType catalog.
 * Leverages BaseTypeMapper to handle common field conversions including 'label'.
 */
@Component
public class OnboardingTypeMapper
        extends BaseTypeMapper<OnboardingTypeDTO, OnboardingType, Long> {

    public OnboardingTypeMapper() {
        super(OnboardingType.class);
    }

    @Override
    public OnboardingTypeDTO toDTO(OnboardingType entity) {
        if (entity == null) return null;

        return new OnboardingTypeDTO(
                entity.getId(),
                entity.getName(),
                entity.getLabel(),
                entity.getDescription(),
                entity.getSortOrder(),
                entity.isActive()
        );
    }
}
