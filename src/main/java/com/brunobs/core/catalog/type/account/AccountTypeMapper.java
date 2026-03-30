package com.brunobs.core.catalog.type.account;

import com.brunobs.core.catalog.common.BaseTypeMapper;
import com.brunobs.core.catalog.type.authorization.AuthorizationType;
import com.brunobs.core.catalog.type.authorization.AuthorizationTypeDTO;
import com.brunobs.shared.SchemaValidator;
import org.springframework.stereotype.Component;


@Component
public class AccountTypeMapper
        extends BaseTypeMapper<AccountTypeDTO, AccountType, Long> {
    private final SchemaValidator schemaEngine;

    public AccountTypeMapper(SchemaValidator schemaEngine) {
        super(AccountType.class, schemaEngine);
        this.schemaEngine = schemaEngine;
    }

    @Override
    public AccountTypeDTO toDTO(AccountType entity) {
        if (entity == null) return null;

        return new AccountTypeDTO(
                entity.getId(),
                entity.getName(),
                entity.getLabel(),
                entity.getDescription(),
                entity.getSortOrder(),
                schemaEngine.fromString(entity.getSettings())
        );
    }
}
