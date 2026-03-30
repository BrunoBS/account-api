package com.brunobs.core.catalog.type.authorization;


import com.brunobs.core.catalog.common.BaseTypeValidator;
import com.brunobs.core.catalog.type.applicationscope.ApplicationScopeTypeDTO;
import com.brunobs.core.catalog.type.publisherscope.PublisherScopeTypeEnum;
import com.brunobs.core.catalog.type.schema.SchemaTypeEnum;
import com.brunobs.core.catalog.type.schema.SchemaTypeRepository;
import com.brunobs.core.catalog.type.schema.SchemaTypeService;
import com.brunobs.message.feature.CatalogMessages;
import com.brunobs.shared.base.BaseEnum;
import com.brunobs.shared.SchemaValidator;
import com.brunobs.shared.validation.ValidationResult;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.stereotype.Component;

@Component
public class AuthorizationTypeValidator extends BaseTypeValidator<
        AuthorizationTypeEnum,
        AuthorizationTypeRepository,
        AuthorizationTypeDTO> {


    public AuthorizationTypeValidator(AuthorizationTypeRepository repository,
                                      CatalogMessages catalogMessages,
                                      SchemaValidator schemaValidator,
                                      SchemaTypeRepository schemaTypeRepository) {
        super(repository, AuthorizationTypeEnum.class, catalogMessages, schemaValidator, schemaTypeRepository);

    }

    @Override
    public Long getId(AuthorizationTypeDTO dto) {
        return dto.id();
    }

    @Override
    public String getName(AuthorizationTypeDTO dto) {
        return dto.name();
    }

    @Override
    public String getDescription(AuthorizationTypeDTO dto) {
        return dto.description();
    }

    @Override
    public String getLabel(AuthorizationTypeDTO dto) {
        return dto.label();

    }

    @Override
    public JsonNode getSettings(AuthorizationTypeDTO dto) {
        return dto.settings();
    }

    @Override
    public SchemaTypeEnum getTypeSchema() {
        return SchemaTypeEnum.AUTHORIZATION_GROUP;
    }

    @Override
    public AuthorizationTypeEnum getEnum(String name) {
        return BaseEnum.from(AuthorizationTypeEnum.class, name);
    }


}
