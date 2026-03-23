package com.brunobs.core.catalog.type.applicationscope;
import com.brunobs.core.catalog.common.BaseTypeMapper;
import org.springframework.stereotype.Component;

/**
 * Mapper for ApplicationScopeType catalog.
 * Extends BaseTypeMapper to reuse common catalog field mapping logic.
 */
@Component
public class ApplicationScopeTypeMapper
        extends BaseTypeMapper<ApplicationScopeTypeDTO, ApplicationScopeType, Long> {

    public ApplicationScopeTypeMapper() {
        super(ApplicationScopeType.class);
    }

    @Override
    public ApplicationScopeTypeDTO toDTO(ApplicationScopeType entity) {
        if (entity == null) return null;

        return new ApplicationScopeTypeDTO(
                entity.getId(),
                entity.getName(),
                entity.getLabel(),
                entity.getDescription(),
                entity.getSortOrder()
        );
    }
}
