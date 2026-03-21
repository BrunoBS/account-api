package com.brunobs.core.catalog.type.account;

import com.brunobs.core.catalog.common.BaseTypeMapper;
import org.springframework.stereotype.Component;


@Component
public class AccountTypeMapper
        extends BaseTypeMapper<AccountTypeDTO, AccountType, Long> {

    public AccountTypeMapper() {
        super(AccountType.class);
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
                entity.isActive()
        );
    }
}
