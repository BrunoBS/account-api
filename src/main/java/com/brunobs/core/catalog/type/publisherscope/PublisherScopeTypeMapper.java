package com.brunobs.core.catalog.type.publisherscope;


import com.brunobs.core.catalog.common.BaseTypeMapper;
import org.springframework.stereotype.Component;

/**
 * Mapper for PublisherScopeType catalog.
 * Leverages BaseTypeMapper to handle common catalog field conversions including 'label'.
 */
@Component
public class PublisherScopeTypeMapper
        extends BaseTypeMapper<PublisherScopeTypeDTO, PublisherScopeType, Long> {

    public PublisherScopeTypeMapper() {
        super(PublisherScopeType.class);
    }

    @Override
    public PublisherScopeTypeDTO toDTO(PublisherScopeType entity) {
        if (entity == null) return null;

        return new PublisherScopeTypeDTO(
                entity.getId(),
                entity.getName(),
                entity.getLabel(),
                entity.getDescription(),
                entity.getSortOrder(),
                entity.isActive()
        );
    }
}
