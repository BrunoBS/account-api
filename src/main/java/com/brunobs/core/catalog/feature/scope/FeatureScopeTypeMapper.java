package com.brunobs.core.catalog.feature.scope;


import com.brunobs.core.catalog.common.BaseTypeMapper;
import org.springframework.stereotype.Component;

/**
 * Mapper for FeatureScopeType catalog.
 * Leverages BaseTypeMapper to handle common field conversions including 'label'.
 */
@Component
public class FeatureScopeTypeMapper
        extends BaseTypeMapper<FeatureScopeTypeDTO, FeatureScopeType, Long> {

    public FeatureScopeTypeMapper() {
        super(FeatureScopeType.class);
    }

    @Override
    public FeatureScopeTypeDTO toDTO(FeatureScopeType entity) {
        if (entity == null) return null;

        return new FeatureScopeTypeDTO(
                entity.getId(),
                entity.getName(),
                entity.getLabel(),
                entity.getDescription(),
                entity.getSortOrder()
        );
    }
}
