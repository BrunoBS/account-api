package com.brunobs.shared;

import com.brunobs.message.general.SchemaMessages;
import com.brunobs.shared.validation.ValidationResult;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.networknt.schema.JsonSchema;
import com.networknt.schema.JsonSchemaFactory;
import com.networknt.schema.SpecVersion;
import com.networknt.schema.ValidationMessage;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;


@Component
public class SchemaValidator {

    private final ObjectMapper objectMapper;
    private final JsonSchemaFactory factory;
    private final SchemaMessages schemaMessages;
    private final Map<String, JsonSchema> schemaCache = new ConcurrentHashMap<>();

    public SchemaValidator(ObjectMapper objectMapper, SchemaMessages schemaMessages) {
        this.objectMapper = objectMapper;
        this.schemaMessages = schemaMessages;

        this.factory = JsonSchemaFactory.getInstance(SpecVersion.VersionFlag.V202012);
    }


    public void validateJson(String schemaStr, JsonNode configNode, String attributeName, ValidationResult
            vr) {
        String field = (attributeName == null) ? "settings" : attributeName;

        if (schemaStr == null || schemaStr.isBlank()) {
            vr.addError("schema", schemaMessages.schemaUndefined());
            return;
        }

        if (configNode == null || configNode.isNull()) {
            vr.addError(field, schemaMessages.valueRequired(field));
            return;
        }

        JsonSchema schema = parseAndValidateSchemaSyntax(schemaStr, vr);
        if (schema != null) {
            Set<ValidationMessage> errors = schema.validate(configNode);
            if (!errors.isEmpty()) {
                errors.forEach(e -> vr.addError(field, e.getMessage()));
            }
        } else {
            vr.addError(field, schemaMessages.jsonInvalidForSchema(field));
        }
    }


    public void validateSchemaSyntax(JsonNode schemaNode, ValidationResult vr) {
        if (schemaNode == null || schemaNode.isEmpty() || !schemaNode.isObject()) {
            vr.addError("jsonSchema", schemaMessages.schemaInvalidSyntax());
            return;
        }

        try {
            factory.getSchema(schemaNode);
        } catch (Exception e) {
            vr.addError("jsonSchema", schemaMessages.schemaInvalidSyntax());
        }
    }

    private JsonSchema parseAndValidateSchemaSyntax(String schemaStr, ValidationResult vr) {
        return schemaCache.computeIfAbsent(schemaStr, key -> {
            try {
                JsonNode schemaNode = objectMapper.readTree(key);
                return factory.getSchema(schemaNode);
            } catch (Exception e) {
                vr.addError("schema", schemaMessages.schemaInvalidSyntax());
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

}
