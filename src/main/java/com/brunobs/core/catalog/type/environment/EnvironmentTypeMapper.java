package com.brunobs.core.catalog.type.environment;


import com.brunobs.core.catalog.common.BaseTypeMapper;
import com.brunobs.core.catalog.type.account.AccountType;
import com.brunobs.core.catalog.type.account.AccountTypeDTO;
import com.brunobs.shared.SchemaValidator;
import org.springframework.stereotype.Component;

@Component
public class EnvironmentTypeMapper
        extends BaseTypeMapper<EnvironmentTypeDTO, EnvironmentType, Long> {

    private final SchemaValidator schemaEngine;

    public EnvironmentTypeMapper(SchemaValidator schemaEngine) {
        super(EnvironmentType.class, schemaEngine);
        this.schemaEngine = schemaEngine;
    }

    @Override
    public EnvironmentTypeDTO toDTO(EnvironmentType entity) {
        if (entity == null) return null;

        return new EnvironmentTypeDTO(
                entity.getId(),
                entity.getName(),
                entity.getLabel(),
                entity.getDescription(),
                entity.getSortOrder(),
                schemaEngine.fromString(entity.getSettings())
        );
    }
}
