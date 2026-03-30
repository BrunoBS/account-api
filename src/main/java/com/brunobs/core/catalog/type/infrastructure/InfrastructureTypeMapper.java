package com.brunobs.core.catalog.type.infrastructure;

import com.brunobs.core.catalog.common.BaseTypeMapper;
import com.brunobs.core.catalog.type.environment.EnvironmentType;
import com.brunobs.core.catalog.type.environment.EnvironmentTypeDTO;
import com.brunobs.shared.SchemaValidator;
import org.springframework.stereotype.Component;

/**
 * Mapper for InfrastructureType catalog.
 * Leverages BaseTypeMapper to handle common field conversions including 'label'.
 */
@Component
public class InfrastructureTypeMapper
        extends BaseTypeMapper<InfrastructureTypeDTO, InfrastructureType, Long> {

    private final SchemaValidator schemaEngine;

    public InfrastructureTypeMapper(SchemaValidator schemaEngine) {
        super(InfrastructureType.class, schemaEngine);
        this.schemaEngine = schemaEngine;
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
                schemaEngine.fromString(entity.getSettings())
        );
    }
}
