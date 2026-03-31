package com.brunobs.core.configuration;

import com.fasterxml.jackson.databind.JsonNode;

import java.util.List;


public record EnvironmentConfigDTO(
        Long environmentId,
        List<PublisherConfigDTO> publishers,
        JsonNode settings
) {

    public EnvironmentConfigDTO withEnvironmentId(Long environmentId) {
        return new EnvironmentConfigDTO(
                environmentId,
                this.publishers,
                this.settings
        );
    }
}
