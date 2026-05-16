package com.brunobs.core.catalog.type.applicationscope;

import com.brunobs.core.catalog.common.BaseTypeValidator;
import com.brunobs.core.catalog.type.account.AccountTypeDTO;
import com.brunobs.core.catalog.type.schema.SchemaTypeEnum;
import com.brunobs.core.catalog.type.schema.SchemaTypeRepository;
import com.brunobs.core.catalog.type.schema.SchemaTypeService;
import com.brunobs.message.feature.CatalogMessages;
import com.brunobs.shared.SchemaValidator;
import com.brunobs.shared.base.BaseEnum;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.stereotype.Component;


@Component
public class ApplicationScopeTypeValidator extends BaseTypeValidator<
        ApplicationScopeTypeEnum,
        ApplicationScopeTypeRepository,
        ApplicationScopeTypeDTO> {


    public ApplicationScopeTypeValidator(ApplicationScopeTypeRepository repository,
                                         CatalogMessages catalogMessages,
                                         SchemaTypeRepository schemaTypeRepository,
                                         SchemaValidator schemaValidator, SchemaValidator schemaValidator1) {
        super(repository, ApplicationScopeTypeEnum.class, catalogMessages, schemaValidator, schemaTypeRepository);
    }


    @Override
    public String getName(ApplicationScopeTypeDTO dto) {
        return dto.name();
    }

    @Override
    public String getLabel(ApplicationScopeTypeDTO dto) {
        return dto.label();
    }

    @Override
    public String getDescription(ApplicationScopeTypeDTO dto) {
        return dto.description();
    }

    @Override
    public ApplicationScopeTypeEnum getEnum(String name) {
        return BaseEnum.from(ApplicationScopeTypeEnum.class, name);
    }

    @Override
    public JsonNode getSettings(ApplicationScopeTypeDTO dto) {
        return dto.settings();
    }

    @Override
    public SchemaTypeEnum getTypeSchema() {
        return SchemaTypeEnum.TYPE;
    }
}
