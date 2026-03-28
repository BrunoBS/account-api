package com.brunobs.core.catalog.type.environment;

import com.brunobs.core.catalog.common.BaseTypeValidator;
import com.brunobs.message.feature.CatalogMessages;
import com.brunobs.shared.base.BaseEnum;
import org.springframework.stereotype.Component;

@Component
public class EnvironmentTypeValidator extends BaseTypeValidator<EnvironmentTypeEnum, EnvironmentTypeRepository, EnvironmentTypeDTO> {


    public EnvironmentTypeValidator(EnvironmentTypeRepository repository, CatalogMessages catalogMessages) {
        super(repository, EnvironmentTypeEnum.class, catalogMessages);
    }

    @Override
    public Long getId(EnvironmentTypeDTO dto) {
        return dto.id();
    }

    @Override
    public String getName(EnvironmentTypeDTO dto) {
        return dto.name();
    }

    @Override
    public String getDescription(EnvironmentTypeDTO dto) {
        return dto.description();
    }

    @Override
    public String getLabel(EnvironmentTypeDTO dto) {
        return dto.label();
    }

    @Override
    public EnvironmentTypeEnum getEnum(String name) {
        return BaseEnum.from(EnvironmentTypeEnum.class, name);
    }
}
