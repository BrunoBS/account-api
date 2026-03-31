package com.brunobs.core.configuration;
import com.fasterxml.jackson.databind.JsonNode;

/**
 * Represents a publisher configuration for a specific account and environment.
 */
public record PublisherConfigDTO(
        String name,        // nome -> name
        Integer order,      // ordem -> order
        JsonNode parameters // parametros -> parameters
) {}
