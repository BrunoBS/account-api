package com.brunobs.core.account;

import com.brunobs.shared.BaseDTO;
import com.fasterxml.jackson.databind.JsonNode;

import java.util.List;
import java.util.UUID;


public record AccountDTO(
        Long id,
        String identifier,
        String accountType,
        String name,
        String description,
        String requester,
        String initials,
        String authorizerGroup,
        JsonNode parameters,
        String emailGroup,
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
                null,
                this.accountType,
                this.name,
                this.description,
                this.requester,
                this.initials,
                this.authorizerGroup,
                this.parameters,
                this.emailGroup,
                this.approvers,
                this.tags
        );
    }


    public static AccountDTO toDTO(Long id) {
        return new AccountDTO(id, null, null, null, null, null, null, null,
                null, null, null, null);
    }
}
