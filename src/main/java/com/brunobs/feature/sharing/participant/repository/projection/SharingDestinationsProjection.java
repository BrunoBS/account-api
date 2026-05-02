package com.brunobs.feature.sharing.participant.repository.projection;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Value;

public interface SharingDestinationsProjection {
    @JsonProperty(index = 1)
    Integer getEnvironmentId();

    @JsonProperty(index = 2)
    Long getApplicationOriginId();

    @JsonProperty(index = 3)
    String getTargetName();

    @JsonProperty(index = 4)
    String getTargetIdentifier();

    @JsonProperty(index = 5)
    Long getAccountDestinationId();

    @JsonProperty(index = 6)
    String getAccountDestinationIdentifier();

    @JsonProperty(index = 7)
    String getAccountDestinationName();

    @JsonProperty(index = 8)
    Long getApplicationDestinationId();

    @JsonProperty(index = 9)
    String getApplicationDestinationName();

    @JsonProperty(index = 10)
    @Value("#{@schemaValidator.fromString(target.publishers)}")
    JsonNode getPublishers();
}
