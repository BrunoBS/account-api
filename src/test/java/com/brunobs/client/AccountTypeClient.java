package com.brunobs.client;

import com.brunobs.core.catalog.type.account.AccountTypeDTO;

public class AccountTypeClient
        extends BaseCatalogClient<AccountTypeDTO> {

    private static final String BASE_PATH =
            "/api/v1/account-type";

    private AccountTypeClient() {
        super(BASE_PATH, AccountTypeDTO.class);
    }

    public static AccountTypeClient client() {
        return new AccountTypeClient();
    }
}