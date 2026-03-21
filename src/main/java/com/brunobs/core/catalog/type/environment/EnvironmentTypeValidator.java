package com.brunobs.core.catalog.type.environment;

import com.brunobs.core.catalog.common.BaseTypeValidator;
import com.brunobs.shared.BaseEnum;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

/**
 * Validator for EnvironmentType catalog.
 * Connects the DTO, Repository, and Enum to perform business validations.
 */
@Component
public class EnvironmentTypeValidator extends BaseTypeValidator<EnvironmentTypeEnum, EnvironmentTypeRepository, EnvironmentTypeDTO> {


    public EnvironmentTypeValidator(EnvironmentTypeRepository repository, MessageSource messageSource) {
        super(repository, EnvironmentTypeEnum.class, messageSource);
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
