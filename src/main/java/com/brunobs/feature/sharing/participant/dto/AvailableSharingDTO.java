package com.brunobs.feature.sharing.participant.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record AvailableSharingDTO(
        @JsonProperty(index = 1) Long id,
        @JsonProperty(index = 2) String name,
        @JsonProperty(index = 3) Destination destination,
        @JsonProperty(index = 4) List<Feature> features,
        @JsonProperty(index = 5) Status sharingStatus,
        @JsonProperty(index = 6) Validation validation
) {

    public record Validation(
            @JsonProperty(index = 1) boolean valid,
            @JsonProperty(index = 2) List<Error> errors
    ) {
    }

    public record Error(
            @JsonProperty(index = 1) String name,
            @JsonProperty(index = 2) String label
    ) {
    }

    public record Feature(
            @JsonProperty(index = 1) String name,
            @JsonProperty(index = 2) String label
    ) {
    }

    public record Destination(
            @JsonProperty(index = 1) Long accountId,
            @JsonProperty(index = 2) String accountName,
            @JsonProperty(index = 3) Long applicationId,
            @JsonProperty(index = 4) String applicationName

    ) {
    }

    public record Status(
            @JsonProperty(index = 1) String name,
            @JsonProperty(index = 2) String label
    ) {
    }
}