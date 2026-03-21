package com.brunobs.core.configuration.environment.account.dto;

import java.io.Serializable;

public record AccountEnvironmentIdDTO(
        Long environmentId, // idAmbiente -> environmentId
        Long accountId      // idConta -> accountId
) implements Serializable {
}
