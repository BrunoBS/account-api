package com.brunobs.core.catalog.type.authorization;

import com.brunobs.core.catalog.common.BaseTypeMapper;
import com.brunobs.core.catalog.type.applicationscope.ApplicationScopeType;
import com.brunobs.core.catalog.type.applicationscope.ApplicationScopeTypeDTO;
import com.brunobs.shared.SchemaValidator;
import org.springframework.stereotype.Component;

@Component
public class ApplicationScopeTypeMapper
        extends BaseTypeMapper<ApplicationScopeTypeDTO, ApplicationScopeType, Long> {

    private final SchemaValidator schemaEngine;

    public ApplicationScopeTypeMapper(SchemaValidator schemaEngine) {
        super(ApplicationScopeType.class, schemaEngine);
        this.schemaEngine = schemaEngine;
    }

    @Override
    public ApplicationScopeTypeDTO toDTO(ApplicationScopeType entity) {
        if (entity == null) return null;

        return new ApplicationScopeTypeDTO(
                entity.getId(),
                entity.getName(),
                entity.getLabel(),
                entity.getDescription(),
                entity.getSortOrder(),
                schemaEngine.fromString(entity.getSettings())
        );
    }
}
