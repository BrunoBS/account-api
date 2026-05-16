package com.brunobs.core.catalog.type.infrastructure;

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
public class InfrastructureTypeValidator extends BaseTypeValidator<
        InfrastructureTypeEnum,
        InfrastructureTypeRepository,
        InfrastructureTypeDTO> {

    public InfrastructureTypeValidator(InfrastructureTypeRepository repository,
                                       CatalogMessages catalogMessages,
                                       SchemaValidator schemaValidator,
                                       SchemaTypeRepository schemaTypeRepository) {
        super(repository, InfrastructureTypeEnum.class, catalogMessages, schemaValidator, schemaTypeRepository);
    }

    @Override
    public String getName(InfrastructureTypeDTO dto) {
        return dto.name();
    }

    @Override
    public String getLabel(InfrastructureTypeDTO dto) {
        return dto.label();
    }

    @Override
    public String getDescription(InfrastructureTypeDTO dto) {
        return dto.description();
    }

    @Override
    public InfrastructureTypeEnum getEnum(String name) {
        return BaseEnum.from(InfrastructureTypeEnum.class, name);
    }

    @Override
    public JsonNode getSettings(InfrastructureTypeDTO dto) {
        return dto.settings();
    }

    @Override
    public SchemaTypeEnum getTypeSchema() {
        return SchemaTypeEnum.TYPE;
    }
}
