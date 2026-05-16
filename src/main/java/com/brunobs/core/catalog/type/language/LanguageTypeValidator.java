package com.brunobs.core.catalog.type.language;

import com.brunobs.core.catalog.common.BaseTypeValidator;
import com.brunobs.core.catalog.type.environment.EnvironmentTypeDTO;
import com.brunobs.core.catalog.type.schema.SchemaTypeEnum;
import com.brunobs.core.catalog.type.schema.SchemaTypeRepository;
import com.brunobs.core.catalog.type.schema.SchemaTypeService;
import com.brunobs.message.feature.CatalogMessages;
import com.brunobs.shared.SchemaValidator;
import com.brunobs.shared.base.BaseEnum;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.stereotype.Component;

@Component
public class LanguageTypeValidator extends BaseTypeValidator<
        LanguageTypeEnum,
        LanguageTypeRepository,
        LanguageTypeDTO> {

    public LanguageTypeValidator(LanguageTypeRepository repository,
                                 CatalogMessages catalogMessages,
                                 SchemaValidator schemaValidator,
                                 SchemaTypeRepository schemaTypeRepository) {
        super(repository, LanguageTypeEnum.class, catalogMessages, schemaValidator, schemaTypeRepository);
    }

    @Override
    public String getName(LanguageTypeDTO dto) {
        return dto.name();
    }

    @Override
    public String getLabel(LanguageTypeDTO dto) {
        return dto.label();
    }

    @Override
    public String getDescription(LanguageTypeDTO dto) {
        return dto.description();
    }

    @Override
    public LanguageTypeEnum getEnum(String name) {
        return BaseEnum.from(LanguageTypeEnum.class, name);
    }

    @Override
    public JsonNode getSettings(LanguageTypeDTO dto) {
        return dto.settings();
    }

    @Override
    public SchemaTypeEnum getTypeSchema() {
        return SchemaTypeEnum.TYPE;
    }
}
