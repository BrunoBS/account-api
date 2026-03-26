package com.brunobs.core.catalog.type.applicationscope;

import com.brunobs.shared.base.BaseEnum;

/**
 * Enum representing the valid scopes for an application.
 * 'BACKEND', 'FRONTEND', and 'CONTEXT' are the technical constants.
 */
public enum ApplicationScopeTypeEnum implements BaseEnum<ApplicationScopeTypeEnum> {
    BACKEND,
    FRONTEND,
    SHARED;

}
