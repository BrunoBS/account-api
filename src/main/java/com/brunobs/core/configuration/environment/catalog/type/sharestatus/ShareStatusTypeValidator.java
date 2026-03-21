package com.brunobs.core.catalog.type.sharestatus;

import com.brunobs.core.catalog.common.BaseTypeValidator;
import com.brunobs.shared.BaseEnum;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

/**
 * Validator for ShareStatusType catalog.
 * Connects DTO, Repository, and Enum to perform standardized business validations.
 */
@Component
public class ShareStatusTypeValidator extends BaseTypeValidator<
        ShareStatusTypeEnum,
        ShareStatusTypeRepository,
        ShareStatusTypeDTO> {

    public ShareStatusTypeValidator(ShareStatusTypeRepository repository, MessageSource messageSource) {
        super(repository, ShareStatusTypeEnum.class, messageSource);
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
