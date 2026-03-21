package com.brunobs.core.catalog.feature.type;

import com.brunobs.shared.BaseEnum;

/**
 * Enum representing all available feature types in the portal.
 * Grouped by their business context (Applications, Engines, Configuration, MFE).
 */
public enum FeatureTypeEnum implements BaseEnum<FeatureTypeEnum> {

    // Application Scopes / Groups
    SHARED_APPLICATIONS,
    BACKEND_APPLICATIONS,
    MFE_APPLICATIONS,
    PROMOTION_ENGINE,
    CONFIGURATION,

    // Core Features
    KEY_MANAGEMENT,
    RULE_ENGINE,
    FLEXIBLE_MENU,
    FLEXIBLE_ROUTE,
    ALLOW_LIST,

    // Micro Front-End (MFE) Specifics
    MFE_MANAGEMENT,
    MFE_CATALOG,

    // Promotion / Package Management
    EMERGENCY_PACKAGE,
    CREATE_PACKAGE,
    MANAGE_PACKAGE;

}
