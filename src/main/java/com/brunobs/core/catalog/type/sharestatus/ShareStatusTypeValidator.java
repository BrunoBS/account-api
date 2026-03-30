package com.brunobs.core.catalog.type.sharestatus;

import com.brunobs.core.catalog.common.BaseTypeValidator;
import com.brunobs.core.catalog.type.publisherscope.PublisherScopeTypeEnum;
import com.brunobs.core.catalog.type.schema.SchemaTypeDTO;
import com.brunobs.core.catalog.type.schema.SchemaTypeEnum;
import com.brunobs.core.catalog.type.schema.SchemaTypeRepository;
import com.brunobs.core.catalog.type.schema.SchemaTypeService;
import com.brunobs.message.feature.CatalogMessages;
import com.brunobs.shared.SchemaValidator;
import com.brunobs.shared.base.BaseEnum;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.stereotype.Component;


@Component
public class ShareStatusTypeValidator extends BaseTypeValidator<
        ShareStatusTypeEnum,
        ShareStatusTypeRepository,
        ShareStatusTypeDTO> {

    public ShareStatusTypeValidator(ShareStatusTypeRepository repository,
                                    CatalogMessages catalogMessages,
                                    SchemaValidator schemaValidator,
                                    SchemaTypeRepository schemaTypeRepository) {
        super(repository, ShareStatusTypeEnum.class, catalogMessages, schemaValidator, schemaTypeRepository);
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

    @Override
    public JsonNode getSettings(ShareStatusTypeDTO dto) {
        return dto.settings();
    }

    @Override
    public SchemaTypeEnum getTypeSchema() {
        return SchemaTypeEnum.TYPE;
    }

}
