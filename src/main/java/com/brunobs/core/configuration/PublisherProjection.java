package com.brunobs.core.configuration;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Value;

/**
 * Projection for Publisher configurations within an account environment.
 */
public interface PublisherProjection {

    @JsonProperty(index = 1)
    Integer getOrderIndex();

    @JsonProperty(index = 2)
    String getName();

    @JsonProperty(index = 3)
    @Value("#{@schemaValidator.fromString(target.parameters)}")
    JsonNode getParameters();
}
