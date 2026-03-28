package com.brunobs.message.general;

import com.brunobs.message.MessageAbstract;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

@Service
public class SchemaMessages extends MessageAbstract {

    private final String UNDEFINED = "schema.undefined";
    private final String INVALID_SYNTAX = "schema.invalid.syntax";
    private final String VALUE_REQUIRED = "schema.value.required";
    private final String JSON_INVALID = "schema.json.invalid.for.schema";

    public SchemaMessages(MessageSource messageSource) {
        super(messageSource);
    }

    public String schemaUndefined() {
        return getMessage(UNDEFINED);
    }

    public String schemaInvalidSyntax() {
        return getMessage(INVALID_SYNTAX);
    }

    public String valueRequired() {
        return getMessage(VALUE_REQUIRED);
    }


    public String jsonInvalidForSchema(String details) {
        return getMessage(JSON_INVALID, details);
    }
}
