package com.brunobs.core.account;

import com.brunobs.shared.BaseDTO;
import com.fasterxml.jackson.databind.JsonNode;

import java.util.List;
import java.util.UUID;


public record AccountDTO(
        Long id,
        UUID identifier,
        String type,
        String name,
        String description,
        String requester,
        String initials,
        String authorizerGroup,
        JsonNode parameters,
        String emailGroup,
        boolean active,
        List<ApproverDTO> approvers,
        List<String> tags
) implements BaseDTO<String, Long> {

    @Override
    public Long registrationIdentifier() {
        return id;
    }

    @Override
    public String registrationName() {
        return name;
    }


    public AccountDTO withId(Long newId) {
        return new AccountDTO(
                newId,
                newId == null ? UUID.randomUUID() : this.identifier,
                this.type,
                this.name,
                this.description,
                this.requester,
                this.authorizerGroup,
                this.initials,
                this.parameters,
                this.emailGroup,
                this.active,
                this.approvers,
                this.tags
        );
    }


    public static AccountDTO toDTO(Long id, boolean active) {
        return new AccountDTO(id, null, null, null, null, null, null, null,
                null, null, active, null,null);
    }
}
