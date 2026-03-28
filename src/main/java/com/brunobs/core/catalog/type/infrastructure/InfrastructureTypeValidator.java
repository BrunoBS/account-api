package com.brunobs.core.catalog.type.infrastructure;
import com.brunobs.core.catalog.common.BaseTypeValidator;
import com.brunobs.message.feature.CatalogMessages;
import com.brunobs.shared.base.BaseEnum;
import org.springframework.stereotype.Component;


@Component
public class InfrastructureTypeValidator extends BaseTypeValidator<
        InfrastructureTypeEnum,
        InfrastructureTypeRepository,
        InfrastructureTypeDTO> {

    public InfrastructureTypeValidator(InfrastructureTypeRepository repository, CatalogMessages catalogMessages) {
        super(repository, InfrastructureTypeEnum.class, catalogMessages);
    }

    @Override
    public Long getId(InfrastructureTypeDTO dto) {
        return dto.id();
    }

    @Override
    public String getName(InfrastructureTypeDTO dto) {
        return dto.name();
    }

    @Override
    public String getLabel(InfrastructureTypeDTO dto) {
        return dto.label();
    }

    @Override
    public String getDescription(InfrastructureTypeDTO dto) {
        return dto.description();
    }

    @Override
    public InfrastructureTypeEnum getEnum(String name) {
        return BaseEnum.from(InfrastructureTypeEnum.class, name);
    }
}
