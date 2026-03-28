package com.brunobs.core.catalog.type.applicationscope;

import com.brunobs.core.catalog.common.BaseTypeValidator;
import com.brunobs.message.feature.CatalogMessages;
import com.brunobs.shared.base.BaseEnum;
import org.springframework.stereotype.Component;


@Component
public class ApplicationScopeTypeValidator extends BaseTypeValidator<
        ApplicationScopeTypeEnum,
        ApplicationScopeTypeRepository,
        ApplicationScopeTypeDTO> {

    public ApplicationScopeTypeValidator(ApplicationScopeTypeRepository repository, CatalogMessages catalogMessages) {
        super(repository, ApplicationScopeTypeEnum.class, catalogMessages);
    }

    @Override
    public Long getId(ApplicationScopeTypeDTO dto) {
        return dto.id();
    }

    @Override
    public String getName(ApplicationScopeTypeDTO dto) {
        return dto.name();
    }

    @Override
    public String getLabel(ApplicationScopeTypeDTO dto) {
        return dto.label();
    }

    @Override
    public String getDescription(ApplicationScopeTypeDTO dto) {
        return dto.description();
    }

    @Override
    public ApplicationScopeTypeEnum getEnum(String name) {
        return BaseEnum.from(ApplicationScopeTypeEnum.class, name);
    }


}
