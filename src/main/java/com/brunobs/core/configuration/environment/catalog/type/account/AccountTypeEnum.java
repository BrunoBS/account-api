package com.brunobs.core.catalog.type.account;

import com.brunobs.shared.BaseEnum;
import jakarta.annotation.Nonnull;
import java.util.List;

/**
 * Enumeration for Account Types.
 * Following English naming conventions and i18n support.
 */
public enum AccountTypeEnum implements BaseEnum<AccountTypeEnum> {
    MANAGER,
    CATALOG,
    ADMIN;

    @Nonnull
    @Override
    public List<String> getOptions() {
        // Retorna apenas as opções selecionáveis pelo usuário
        return List.of(MANAGER.name(), ADMIN.name());
    }

    /**
     * Returns the i18n key for the account type label.
     * Useful for UI display.
     */
    public String getLabelKey() {
        return "enum.account-type." + this.name().toLowerCase();
    }
}
