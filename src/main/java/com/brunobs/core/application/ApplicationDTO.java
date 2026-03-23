package com.brunobs.core.application;

import com.brunobs.shared.BaseDTO;
import com.fasterxml.jackson.databind.JsonNode;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Data Transfer Object for Application core information.
 * Following English naming conventions and Java Record standards.
 */
public record ApplicationDTO(
        Long id,
        UUID identifier,
        String name,
        String alias,
        Long accountId,
        String languageType,
        String applicationScopeType,
        String infrastructureType,
        String authorizerGroup,
        JsonNode parameters,
        boolean isDefault,
        List<String> tags
) implements BaseDTO<String, Long> {

    @Override
    public Long registrationIdentifier() { // identificadorRegistro -> registrationIdentifier
        return id;
    }

    @Override
    public String registrationName() { // nomeRegistro -> registrationName
        return name;
    }


    public ApplicationDTO withId(Long newId, Long accoountId) {
        return new ApplicationDTO(
                newId,
                newId == null ? UUID.randomUUID() : this.identifier,
                this.name,
                this.alias,
                accountId,
                this.languageType,
                this.applicationScopeType,
                this.infrastructureType,
                this.authorizerGroup,
                this.parameters,
                this.isDefault,
                this.tags
        );
    }

    public static ApplicationDTO toDTO(Long id, Long accountId) {
        return new ApplicationDTO(
                id,
                null,
                null,
                null,
                accountId,
                null,
                null,
                null,
                null,
                null,
                false,
                new ArrayList<>()
        );
    }
}
