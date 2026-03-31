package com.brunobs.core.configuration.environment.application.dto;

import com.brunobs.core.application.Application;
import com.brunobs.core.configuration.PublisherConfig;
import com.brunobs.core.environment.Environment;
import com.brunobs.shared.base.BaseDTO;
import com.fasterxml.jackson.databind.JsonNode;

import java.util.List;

public record ApplicationEnvironmentDTO(
        Application application,
        Environment environment,
        List<PublisherConfig> publishers,
        JsonNode settings
) implements BaseDTO<String, ApplicationEnvironmentIdDTO> {

    @Override
    public ApplicationEnvironmentIdDTO
    registrationIdentifier() {
        return new ApplicationEnvironmentIdDTO(
                environment == null ? null : environment.getId(),
                application == null ? null : application.getId()
        );
    }

    @Override
    public String registrationName() {
        return "";
    }

    // Helper methods para facilitar o acesso aos IDs
    public Long getApplicationId() {
        return application != null ? application.getId() : null;
    }

    public Long getEnvironmentId() {
        return environment != null ? environment.getId() : null;
    }
}
