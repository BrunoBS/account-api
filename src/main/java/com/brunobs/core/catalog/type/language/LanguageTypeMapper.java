package com.brunobs.core.catalog.type.language;

import com.brunobs.core.catalog.common.BaseTypeMapper;
import com.brunobs.core.catalog.type.environment.EnvironmentType;
import com.brunobs.core.catalog.type.environment.EnvironmentTypeDTO;
import com.brunobs.shared.SchemaValidator;
import org.springframework.stereotype.Component;


@Component
public class LanguageTypeMapper extends BaseTypeMapper<LanguageTypeDTO, LanguageType, Long> {


    private final SchemaValidator schemaEngine;

    public LanguageTypeMapper(SchemaValidator schemaEngine) {
        super(LanguageType.class, schemaEngine);
        this.schemaEngine = schemaEngine;
    }

    @Override
    public LanguageTypeDTO toDTO(LanguageType entity) {
        if (entity == null) return null;

        return new LanguageTypeDTO(
                entity.getId(),
                entity.getName(),
                entity.getLabel(),
                entity.getDescription(),
                entity.getSortOrder(),
                schemaEngine.fromString(entity.getSettings())
        );
    }
}
