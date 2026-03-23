package com.brunobs.core.catalog.type.account;

import com.brunobs.core.catalog.common.BaseTypeValidator;
import com.brunobs.core.catalog.type.authorization.AuthorizationTypeDTO;
import com.brunobs.core.catalog.type.schema.SchemaTypeEnum;
import com.brunobs.core.catalog.type.schema.SchemaTypeService;
import com.brunobs.shared.BaseEnum;
import com.brunobs.shared.SchemaValidator;
import com.brunobs.shared.validation.ValidationResult;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

/**
 * Validator for AccountType catalog.
 * Ensures the DTO complies with business rules before saving.
 */
@Component
public class AccountTypeValidator extends BaseTypeValidator<
        AccountTypeEnum,
        AccountTypeRepository,
        AccountTypeDTO> {

    private final SchemaTypeService schemaTypeService;
    private final SchemaValidator schemaValidator;

    public AccountTypeValidator(AccountTypeRepository repository, MessageSource messageSource, SchemaTypeService schemaTypeService, SchemaValidator schemaValidator) {
        super(repository, AccountTypeEnum.class, messageSource);
        this.schemaTypeService = schemaTypeService;
        this.schemaValidator = schemaValidator;
    }

    @Override
    public Long getId(AccountTypeDTO dto) {
        return dto.id();
    }

    @Override
    public String getName(AccountTypeDTO dto) {
        return dto.name();
    }

    @Override
    public String getDescription(AccountTypeDTO dto) {
        return dto.description();
    }

    @Override
    public String getLabel(AccountTypeDTO dto) {
        return dto.label();
    }

    @Override
    public AccountTypeEnum getEnum(String name) {
        return BaseEnum.from(AccountTypeEnum.class, name);
    }

    @Override
    protected void validateAdditionalFields(AccountTypeDTO dto, ValidationResult vr) {
        schemaValidator.validateJson(
                getJsonSchema(),
                dto.settings(),
                "settings",
                vr
        );
    }

    private String getJsonSchema() {
        try {
            return schemaTypeService.findByName(SchemaTypeEnum.FEATURE.name()).getJsonSchema();
        } catch (Exception e) {

            return SchemaTypeEnum.DEFAULT_JSON_SCHEMA;
        }
    }
}
