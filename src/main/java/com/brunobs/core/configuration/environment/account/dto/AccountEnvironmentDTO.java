package com.brunobs.core.configuration.environment.account.dto;

import com.brunobs.core.account.Account;
import com.brunobs.core.configuration.PublisherConfig;
import com.brunobs.core.environment.Environment;
import com.brunobs.shared.base.BaseDTO;

import java.util.List;

public record AccountEnvironmentDTO(
        Account account,
        Environment environment,
        List<PublisherConfig> publishers,
        String authorizerGroup
) implements BaseDTO<String, AccountEnvironmentIdDTO> {

    @Override
    public AccountEnvironmentIdDTO
    registrationIdentifier() {
        return new AccountEnvironmentIdDTO(
                environment == null ? null : environment.getId(),
                account == null ? null : account.getId()
        );
    }

    @Override
    public String registrationName() {
        return "";
    }

    // Helper methods para facilitar o acesso aos IDs
    public Long getAccountId() {
        return account != null ? account.getId() : null;
    }

    public Long getEnvironmentId() {
        return environment != null ? environment.getId() : null;
    }
}
