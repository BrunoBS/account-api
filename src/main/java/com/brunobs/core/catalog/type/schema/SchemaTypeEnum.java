package com.brunobs.core.catalog.type.schema;

import com.brunobs.shared.base.BaseEnum;

/**
 * Enum representing the different contexts for JSON Schema validation.
 * Each constant maps to a specific validation rule in the system.
 */
public enum SchemaTypeEnum implements BaseEnum<SchemaTypeEnum> {
    ACCOUNT,
    APPLICATION,
    ENVIRONMENT,
    ACCOUNT_ENVIRONMENT,
    APPLICATION_ENVIRONMENT,
    AUTHORIZATION_GROUP,
    TYPE,
    FEATURE;


    public static final String DEFAULT_JSON_SCHEMA = """
            {
              "$schema": "https://json-schema.org/draft/2020-12/schema",
              "title": "Default Dynamic Schema",
              "type": "object",
              "additionalProperties": {
                "type": ["string", "number", "boolean", "null"]
              }
            }
            """;

}
