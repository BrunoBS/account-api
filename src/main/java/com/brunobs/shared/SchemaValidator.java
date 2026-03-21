package com.brunobs.shared.validation;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.networknt.schema.*;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Engine for JSON Schema validation with internationalization support.
 */
@Component
public class SchemaValidator {

    // Constantes de Chaves para o MessageSource
    public static final String MSG_SCHEMA_UNDEFINED = "error.schema.undefined";
    public static final String MSG_SCHEMA_INVALID_SYNTAX = "error.schema.invalid.syntax";
    public static final String MSG_VALUE_REQUIRED = "error.validation.value.required";
    public static final String MSG_JSON_INVALID_FOR_SCHEMA = "error.schema.json.invalid.for.schema";

    private final ObjectMapper objectMapper;
    private final JsonSchemaFactory factory;
    private final MessageSource messageSource;
    private final Map<String, JsonSchema> schemaCache = new ConcurrentHashMap<>();

    public SchemaValidator(ObjectMapper objectMapper, MessageSource messageSource) {
        this.objectMapper = objectMapper;
        this.messageSource = messageSource;
        this.factory = JsonSchemaFactory.getInstance(SpecVersion.VersionFlag.V202012);
    }

    /**
     * Validates a JsonNode against a Schema string.
     */
    public void validateJson(String schemaStr, JsonNode configNode, String attributeName, ValidationResult vr) {
        String field = (attributeName == null) ? "settings" : attributeName;

        if (schemaStr == null || schemaStr.isBlank()) {
            vr.addError("schema", getMessage(MSG_SCHEMA_UNDEFINED));
            return;
        }

        if (configNode == null || configNode.isNull()) {
            vr.addError(field, getMessage(MSG_VALUE_REQUIRED, field));
            return;
        }

        JsonSchema schema = parseAndValidateSchemaSyntax(schemaStr, vr);
        if (schema != null) {
            Set<ValidationMessage> errors = schema.validate(configNode);
            if (!errors.isEmpty()) {
                errors.forEach(e -> vr.addError(field, e.getMessage()));
            }
        } else {
            vr.addError(field, getMessage(MSG_JSON_INVALID_FOR_SCHEMA));
        }
    }

    /**
     * Validates if the content is a valid JSON Schema (Meta-validation).
     */
    public void validateSchemaSyntax(JsonNode schemaNode, ValidationResult vr) {
        if (schemaNode == null || schemaNode.isEmpty() || !schemaNode.isObject()) {
            vr.addError("jsonSchema", getMessage(MSG_SCHEMA_INVALID_SYNTAX));
            return;
        }

        try {
            factory.getSchema(schemaNode);
        } catch (Exception e) {
            vr.addError("jsonSchema", getMessage(MSG_SCHEMA_INVALID_SYNTAX) + ": " + e.getMessage());
        }
    }

    private JsonSchema parseAndValidateSchemaSyntax(String schemaStr, ValidationResult vr) {
        return schemaCache.computeIfAbsent(schemaStr, key -> {
            try {
                JsonNode schemaNode = objectMapper.readTree(key);
                return factory.getSchema(schemaNode);
            } catch (Exception e) {
                vr.addError("schema", getMessage(MSG_SCHEMA_INVALID_SYNTAX));
                return null;
            }
        });
    }

    // --- Helpers de Conversão ---

    public String toJsonString(JsonNode node) {
        try {
            return node != null ? objectMapper.writeValueAsString(node) : null;
        } catch (JsonProcessingException e) {
            return "{}";
        }
    }

    public JsonNode fromString(String json) {
        try {
            return (json == null || json.isBlank()) ? objectMapper.createObjectNode() : objectMapper.readTree(json);
        } catch (JsonProcessingException e) {
            return objectMapper.createObjectNode();
        }
    }

    private String getMessage(String code, Object... args) {
        return messageSource.getMessage(code, args, LocaleContextHolder.getLocale());
    }
}
