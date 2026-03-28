package com.brunobs.feature.configuration.account.dto;

import com.brunobs.core.configuration.PublisherProjection;

import java.util.List;

/**
 * Response DTO that lists all publishers configured for a specific account and environment.
 */
public record AccountEnvironmentPublishersResponseDTO(
        Long accountId,
        String accountName,
        Long environmentId,
        String environmentName,
        List<PublisherProjection> publishers
) {
}
