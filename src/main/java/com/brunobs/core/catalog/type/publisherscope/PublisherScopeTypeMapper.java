package com.brunobs.core.catalog.type.publisherscope;


import com.brunobs.core.catalog.common.BaseTypeMapper;
import com.brunobs.core.catalog.type.language.LanguageType;
import com.brunobs.core.catalog.type.language.LanguageTypeDTO;
import com.brunobs.shared.SchemaValidator;
import org.springframework.stereotype.Component;

/**
 * Mapper for PublisherScopeType catalog.
 * Leverages BaseTypeMapper to handle common catalog field conversions including 'label'.
 */
@Component
public class PublisherScopeTypeMapper
        extends BaseTypeMapper<PublisherScopeTypeDTO, PublisherScopeType, Long> {

    private final SchemaValidator schemaEngine;
    public PublisherScopeTypeMapper(SchemaValidator schemaEngine) {
        super(PublisherScopeType.class, schemaEngine);
        this.schemaEngine = schemaEngine;
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
                schemaEngine.fromString(entity.getSettings())
        );
    }
}
