package com.brunobs.common.factory;

import com.brunobs.common.builder.AccountTypeBuilder;
import com.brunobs.core.catalog.type.account.AccountTypeDTO;

import java.util.List;

public class AccountTypeFactory
        extends BaseCatalogFactory<AccountTypeDTO, AccountTypeBuilder> {

    @Override
    protected AccountTypeBuilder builder() {
        return AccountTypeBuilder.builder();
    }

    // =========================================================
    // VALID STATES
    // =========================================================

    public AccountTypeDTO admin() {
        return builder()
                .withName("ADMIN")
                .withLabel("Admin")
                .withDescription("Perfil com acesso total")
                .withSortOrder(1)
                .build();
    }

    public AccountTypeDTO manager() {
        return builder()
                .withName("MANAGER")
                .withLabel("Manager")
                .withDescription("Perfil de gestão")
                .withSortOrder(2)
                .build();
    }

    public AccountTypeDTO catalog() {
        return builder()
                .withName("CATALOG")
                .withLabel("Catalog")
                .withDescription("Gestão de catálogo")
                .withSortOrder(3)
                .build();
    }

    public List<AccountTypeDTO> all() {
        return List.of(
                admin(),
                manager(),
                catalog()
        );
    }

    public static AccountTypeFactory factory() {
        return new AccountTypeFactory();
    }
}