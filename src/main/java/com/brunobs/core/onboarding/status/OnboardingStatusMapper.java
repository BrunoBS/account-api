package com.brunobs.core.onboarding.status;

import com.brunobs.core.catalog.common.BaseTypeMapper;
import org.springframework.stereotype.Component;

/**
 * Mapper for OnboardingType catalog.
 * Leverages BaseTypeMapper to handle common field conversions including 'label'.
 */
@Component
public class OnboardingStatusMapper
        extends BaseTypeMapper<OnboardingStatusDTO, OnboardingStatus, Long> {

    public OnboardingStatusMapper() {
        super(OnboardingStatus.class);
    }

    @Override
    public OnboardingStatusDTO toDTO(OnboardingStatus entity) {
        if (entity == null) return null;

        return new OnboardingStatusDTO(
                entity.getId(),
                entity.getName(),
                entity.getLabel(),
                entity.getDescription(),
                entity.getSortOrder(),
                entity.isActive()
        );
    }
}
