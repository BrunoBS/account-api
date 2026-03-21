package com.brunobs.core.configuration;

import com.brunobs.core.configuration.environment.account.dto.PublisherConfigDTO;

import java.util.List;


public record EnvironmentConfigDTO(
        Long environmentId,
        String authorizerGroup,
        List<PublisherConfigDTO> publishers
) {

    public EnvironmentConfigDTO withEnvironmentId(Long environmentId) {
        return new EnvironmentConfigDTO(
                environmentId,
                this.authorizerGroup,
                this.publishers
        );
    }
}
