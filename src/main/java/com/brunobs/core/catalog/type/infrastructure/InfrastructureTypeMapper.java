package com.brunobs.core.catalog.type.infrastructure;
import com.brunobs.core.catalog.common.BaseTypeMapper;
import org.springframework.stereotype.Component;

/**
 * Mapper for InfrastructureType catalog.
 * Leverages BaseTypeMapper to handle common field conversions including 'label'.
 */
@Component
public class InfrastructureTypeMapper
        extends BaseTypeMapper<InfrastructureTypeDTO, InfrastructureType, Long> {

    public InfrastructureTypeMapper() {
        super(InfrastructureType.class);
    }

    @Override
    public InfrastructureTypeDTO toDTO(InfrastructureType entity) {
        if (entity == null) return null;

        return new InfrastructureTypeDTO(
                entity.getId(),
                entity.getName(),
                entity.getLabel(),
                entity.getDescription(),
                entity.getSortOrder(),
                entity.isActive()
        );
    }
}
