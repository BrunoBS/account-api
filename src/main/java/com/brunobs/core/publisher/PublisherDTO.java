package com.brunobs.core.publisher;

import com.brunobs.shared.BaseDTO;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.JsonNode;

/**
 * Data Transfer Object for Publisher management.
 * Uses JsonNode for 'jsonSchema' to allow dynamic structure validation.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public record PublisherDTO(
        Long id,
        String name,
        String label,
        String description,
        JsonNode jsonSchema,
        String publisherScope,
        Boolean active,
        Boolean deprecated
) implements BaseDTO<String, Long> {

    @Override
    public Long registrationIdentifier() {
        return id;
    }

    @Override
    public String registrationName() {
        return name;
    }


    public PublisherDTO withId(Long id) {
        return new PublisherDTO(
                id,
                this.name,
                this.label,
                this.description,
                this.jsonSchema,
                this.publisherScope,
                id == null || (this.active != null && this.active),
                this.deprecated
        );
    }


    public static PublisherDTO of(Long id, String name, String label) {
        return new PublisherDTO(id, name, label, null, null, null, null, null);
    }
}
