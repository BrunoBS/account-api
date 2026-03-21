package com.brunobs.core.catalog.type.environment;


import com.brunobs.core.catalog.common.BaseTypeMapper;
import org.springframework.stereotype.Component;

@Component
public class EnvironmentTypeMapper
        extends BaseTypeMapper<EnvironmentTypeDTO, EnvironmentType, Long> {

    public EnvironmentTypeMapper() {
        super(EnvironmentType.class);
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
                entity.isActive()
        );
    }
}
