package com.brunobs.core.catalog.type.schema;


import com.brunobs.core.catalog.common.BaseTypeValidator;
import com.brunobs.core.catalog.type.environment.EnvironmentTypeDTO;
import com.brunobs.core.catalog.type.environment.EnvironmentTypeEnum;
import com.brunobs.message.feature.CatalogMessages;
import com.brunobs.shared.base.BaseEnum;
import com.brunobs.shared.SchemaValidator;
import com.brunobs.shared.validation.ValidationResult;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.stereotype.Component;

@Component
public class SchemaTypeValidator extends BaseTypeValidator<
        SchemaTypeEnum,
        SchemaTypeRepository,
        SchemaTypeDTO> {

    private final SchemaValidator schemaValidator;

    public SchemaTypeValidator(SchemaTypeRepository repository,
                               CatalogMessages catalogMessages,
                               SchemaValidator schemaValidator,
                               SchemaTypeRepository schemaTypeRepository) {
        super(repository, SchemaTypeEnum.class, catalogMessages, schemaValidator, schemaTypeRepository);
        this.schemaValidator = schemaValidator;
    }


    @Override
    public String getName(SchemaTypeDTO dto) {
        return dto.name();
    }

    @Override
    public String getDescription(SchemaTypeDTO dto) {
        return dto.description();
    }

    @Override
    public String getLabel(SchemaTypeDTO dto) {
        return dto.label();
    }

    @Override
    public SchemaTypeEnum getEnum(String name) {
        return BaseEnum.from(SchemaTypeEnum.class, name);
    }

    @Override
    public JsonNode getSettings(SchemaTypeDTO dto) {
        return dto.settings();
    }

    @Override
    public SchemaTypeEnum getTypeSchema() {
        return SchemaTypeEnum.TYPE;
    }

    @Override
    public void validateAdditionalFields(SchemaTypeDTO dto, ValidationResult vr) {
        super.validateAdditionalFields(dto, vr);
        schemaValidator.validateSchemaSyntax(dto.jsonSchema(), vr);
    }
}
