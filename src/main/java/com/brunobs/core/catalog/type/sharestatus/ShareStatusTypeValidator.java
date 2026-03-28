package com.brunobs.core.catalog.type.sharestatus;

import com.brunobs.core.catalog.common.BaseTypeValidator;
import com.brunobs.message.feature.CatalogMessages;
import com.brunobs.shared.base.BaseEnum;
import org.springframework.stereotype.Component;


@Component
public class ShareStatusTypeValidator extends BaseTypeValidator<
        ShareStatusTypeEnum,
        ShareStatusTypeRepository,
        ShareStatusTypeDTO> {

    public ShareStatusTypeValidator(ShareStatusTypeRepository repository,
                                    CatalogMessages catalogMessages) {
        super(repository, ShareStatusTypeEnum.class, catalogMessages);
    }

    @Override
    public Long getId(ShareStatusTypeDTO dto) {
        return dto.id();
    }

    @Override
    public String getName(ShareStatusTypeDTO dto) {
        return dto.name();
    }

    @Override
    public String getLabel(ShareStatusTypeDTO dto) {
        return dto.label();
    }

    @Override
    public String getDescription(ShareStatusTypeDTO dto) {
        return dto.description();
    }

    @Override
    public ShareStatusTypeEnum getEnum(String name) {
        return BaseEnum.from(ShareStatusTypeEnum.class, name);
    }
}
