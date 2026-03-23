package com.brunobs.core.catalog.type.sharestatus;

import com.brunobs.core.catalog.common.BaseTypeMapper;
import org.springframework.stereotype.Component;

/**
 * Mapper for ShareStatusType catalog.
 * Leverages BaseTypeMapper to handle shared catalog field conversions.
 */
@Component
public class ShareStatusTypeMapper
        extends BaseTypeMapper<ShareStatusTypeDTO, ShareStatusType, Long> {

    public ShareStatusTypeMapper() {
        super(ShareStatusType.class);
    }

    @Override
    public ShareStatusTypeDTO toDTO(ShareStatusType entity) {
        if (entity == null) return null;

        return new ShareStatusTypeDTO(
                entity.getId(),
                entity.getName(),
                entity.getLabel(),
                entity.getDescription(),
                entity.getSortOrder()
        );
    }
}
