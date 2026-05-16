package com.brunobs.core.catalog.type.account;

import com.brunobs.core.catalog.common.BaseTypeValidator;
import com.brunobs.core.catalog.type.schema.SchemaTypeEnum;
import com.brunobs.core.catalog.type.schema.SchemaTypeRepository;
import com.brunobs.core.catalog.type.schema.SchemaTypeService;
import com.brunobs.message.feature.CatalogMessages;
import com.brunobs.shared.base.BaseEnum;
import com.brunobs.shared.SchemaValidator;
import com.brunobs.shared.validation.ValidationResult;
import com.fasterxml.jackson.databind.JsonNode;
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


    public AccountTypeValidator(AccountTypeRepository repository,
                                SchemaTypeRepository schemaTypeRepository,
                                SchemaValidator schemaValidator,
                                CatalogMessages catalogMessages
    ) {
        super(repository, AccountTypeEnum.class, catalogMessages, schemaValidator, schemaTypeRepository);
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
    public JsonNode getSettings(AccountTypeDTO dto) {
        return dto.settings();
    }

    @Override
    public SchemaTypeEnum getTypeSchema() {
        return SchemaTypeEnum.FEATURE;
    }

    @Override
    public AccountTypeEnum getEnum(String name) {
        return BaseEnum.from(AccountTypeEnum.class, name);
    }


}
