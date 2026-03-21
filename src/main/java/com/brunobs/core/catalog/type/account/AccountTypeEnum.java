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

}
