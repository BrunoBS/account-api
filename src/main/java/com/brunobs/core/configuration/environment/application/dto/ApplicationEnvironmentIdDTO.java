package com.brunobs.core.configuration.environment.application.dto;

import java.io.Serializable;

public record ApplicationEnvironmentIdDTO(
        Long environmentId,
        Long applicationId
) implements Serializable {
}
