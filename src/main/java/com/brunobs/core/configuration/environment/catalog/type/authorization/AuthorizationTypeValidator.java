package com.brunobs.core.catalog.type.authorization;


import com.brunobs.core.catalog.common.BaseTypeValidator;
import com.brunobs.core.catalog.type.schema.SchemaTypeEnum;
import com.brunobs.core.catalog.type.schema.SchemaTypeService;
import com.brunobs.shared.BaseEnum;
import com.brunobs.shared.validation.SchemaValidator;
import com.brunobs.shared.validation.ValidationResult;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

/**
 * Validator for AuthorizationType catalog.
 * Performs schema validation on the dynamic 'settings' JSON field.
 */
@Component
public class AuthorizationTypeValidator extends BaseTypeValidator<
        AuthorizationTypeEnum,
        AuthorizationTypeRepository,
        AuthorizationTypeDTO> {

    private final SchemaValidator schemaValidator;
    private final SchemaTypeService schemaTypeService;

    public AuthorizationTypeValidator(AuthorizationTypeRepository repository,
                                      MessageSource messageSource,
                                      SchemaValidator schemaValidator,
                                      SchemaTypeService schemaTypeService) {
        super(repository, AuthorizationTypeEnum.class, messageSource);
        this.schemaValidator = schemaValidator;
        this.schemaTypeService = schemaTypeService;
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
    public AuthorizationTypeEnum getEnum(String name) {
        return BaseEnum.from(AuthorizationTypeEnum.class, name);
    }

    /**
     * Guideline: Uses SchemaValidator to ensure the 'settings' JSON node
     * complies with the required structure.
     */
    @Override
    protected void validateAdditionalFields(AuthorizationTypeDTO dto, ValidationResult vr) {
        schemaValidator.validateJson(
                getJsonSchema(),
                dto.settings(),
                "settings",
                vr
        );
    }

    private String getJsonSchema() {
        try {
            // Busca o schema específico para grupos de autorização no banco
            return schemaTypeService.findByName(SchemaTypeEnum.AUTHORIZATION_GROUP.name()).getJsonSchema();
        } catch (Exception e) {
            // Fallback para um schema vazio ou padrão caso o banco falhe
            return SchemaTypeEnum.DEFAULT_JSON_SCHEMA;
        }
    }
}
