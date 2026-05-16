package com.brunobs.core.catalog.type.publisherscope;

import com.brunobs.core.catalog.common.BaseTypeValidator;
import com.brunobs.core.catalog.type.environment.EnvironmentTypeEnum;
import com.brunobs.core.catalog.type.language.LanguageTypeDTO;
import com.brunobs.core.catalog.type.schema.SchemaTypeEnum;
import com.brunobs.core.catalog.type.schema.SchemaTypeRepository;
import com.brunobs.core.catalog.type.schema.SchemaTypeService;
import com.brunobs.message.feature.CatalogMessages;
import com.brunobs.shared.SchemaValidator;
import com.brunobs.shared.base.BaseEnum;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.stereotype.Component;


@Component
public class PublisherScopeTypeValidator extends BaseTypeValidator<
        PublisherScopeTypeEnum,
        PublisherScopeTypeRepository,
        PublisherScopeTypeDTO> {

    public PublisherScopeTypeValidator(PublisherScopeTypeRepository repository,
                                       CatalogMessages catalogMessages,
                                       SchemaValidator schemaValidator,
                                       SchemaTypeRepository schemaTypeRepository) {
        super(repository, PublisherScopeTypeEnum.class, catalogMessages, schemaValidator, schemaTypeRepository);
    }


    @Override
    public String getName(PublisherScopeTypeDTO dto) {
        return dto.name();
    }

    @Override
    public String getLabel(PublisherScopeTypeDTO dto) {
        return dto.label();
    }

    @Override
    public String getDescription(PublisherScopeTypeDTO dto) {
        return dto.description();
    }

    @Override
    public PublisherScopeTypeEnum getEnum(String name) {
        return BaseEnum.from(PublisherScopeTypeEnum.class, name);
    }

    @Override
    public JsonNode getSettings(PublisherScopeTypeDTO dto) {
        return dto.settings();
    }

    @Override
    public SchemaTypeEnum getTypeSchema() {
        return SchemaTypeEnum.TYPE;
    }
}