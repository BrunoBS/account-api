package com.brunobs.core.account;

import com.brunobs.proxy.Authorizable;
import com.brunobs.shared.base.BaseDTO;
import com.fasterxml.jackson.databind.JsonNode;

import java.util.HashSet;
import java.util.Set;


public record AccountDTO(
        Long id,
        String identifier,
        String accountType,
        String name,
        String description,
        String requester,
        String acronym,
        String authorizerGroup,
        JsonNode settings,
        String emailGroup,
        Set<ApproverDTO> approvers,
        Set<String> tags
) implements BaseDTO<String, Long>, Authorizable {

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
                this.acronym,
                this.authorizerGroup == null ? "" : this.authorizerGroup,
                this.settings,
                this.emailGroup,
                this.approvers,
                (this.tags == null) ? new HashSet<>() : this.tags
        );
    }


    public static AccountDTO toDTO(Long id) {
        return new AccountDTO(id, null, null, null, null, null, null, null,
                null, null, null, null);
    }

    @Override
    public String getAuthorizerGroup() {
        return authorizerGroup;
    }
}
