package com.brunobs.common.builder;

import com.brunobs.core.catalog.type.account.AccountTypeDTO;

public class AccountTypeBuilder extends BaseCatalogBuilder<AccountTypeDTO, AccountTypeBuilder> {

    public AccountTypeBuilder() {
        this.name = faker.options()
                .option("ADMIN", "MANAGER", "CATALOG");
    }

    public static AccountTypeBuilder builder() {
        return new AccountTypeBuilder();
    }

    @Override
    public AccountTypeDTO build() {
        return new AccountTypeDTO(
                id,
                name,
                label,
                description,
                sortOrder,
                settings
        );
    }
}