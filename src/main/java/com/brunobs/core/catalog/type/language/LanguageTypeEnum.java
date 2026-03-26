package com.brunobs.core.catalog.type.language;

import com.brunobs.shared.base.BaseEnum;

/**
 * Enum representing supported programming languages.
 * These constants are used as technical identifiers in the 'name' column.
 */
public enum LanguageTypeEnum implements BaseEnum<LanguageTypeEnum> {
    JAVA,
    DOTNET,
    GO,
    PYTHON,
    OTHER;

}
