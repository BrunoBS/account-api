package com.brunobs.core.catalog.type.sharestatus;

import com.brunobs.core.catalog.common.BaseTypeMapper;
import com.brunobs.core.catalog.type.language.LanguageType;
import com.brunobs.core.catalog.type.language.LanguageTypeDTO;
import com.brunobs.shared.SchemaValidator;
import org.springframework.stereotype.Component;


@Component
public class ShareStatusTypeMapper
        extends BaseTypeMapper<ShareStatusTypeDTO, ShareStatusType, Long> {

    private final SchemaValidator schemaEngine;
    public ShareStatusTypeMapper(SchemaValidator schemaEngine) {
        super(ShareStatusType.class, schemaEngine);
        this.schemaEngine = schemaEngine;
    }

    @Override
    public ShareStatusTypeDTO toDTO(ShareStatusType entity) {
        if (entity == null) return null;

        return new ShareStatusTypeDTO(
                entity.getId(),
                entity.getName(),
                entity.getLabel(),
                entity.getDescription(),
                entity.getSortOrder(),
                schemaEngine.fromString(entity.getSettings())
        );
    }
}
