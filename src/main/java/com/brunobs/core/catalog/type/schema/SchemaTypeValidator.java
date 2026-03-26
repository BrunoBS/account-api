package com.brunobs.core.catalog.type.schema;


import com.brunobs.core.catalog.common.BaseTypeValidator;
import com.brunobs.shared.base.BaseEnum;
import com.brunobs.shared.SchemaValidator;
import com.brunobs.shared.validation.ValidationResult;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

@Component
public class SchemaTypeValidator extends BaseTypeValidator<
        SchemaTypeEnum,
        SchemaTypeRepository,
        SchemaTypeDTO> {

    private final SchemaValidator schemaEngine;

    public SchemaTypeValidator(SchemaTypeRepository repository,
                               MessageSource messageSource,
                               SchemaValidator schemaEngine) {
        super(repository, SchemaTypeEnum.class, messageSource);
        this.schemaEngine = schemaEngine;
    }

    @Override
    public Long getId(SchemaTypeDTO dto) {
        return dto.id();
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
    protected void validateAdditionalFields(SchemaTypeDTO dto, ValidationResult vr) {

        schemaEngine.validateSchemaSyntax(dto.jsonSchema(), vr);
    }
}
