package com.brunobs.core.configuration.environment.account;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class AccountEnvironmentId implements Serializable {

    @Column(name = "ENVIRONMENT_ID") // ID_AMBIENTE -> ENVIRONMENT_ID
    private Long environmentId;

    @Column(name = "ACCOUNT_ID") // ID_CONTA -> ACCOUNT_ID
    private Long accountId;

    public AccountEnvironmentId() {
    }

    public AccountEnvironmentId(Long environmentId, Long accountId) {
        this.environmentId = environmentId;
        this.accountId = accountId;
    }

    // Getters and Setters
    public Long getEnvironmentId() {
        return environmentId;
    }

    public void setEnvironmentId(Long environmentId) {
        this.environmentId = environmentId;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    // Importante: Chaves compostas (@Embeddable) devem sobrescrever equals e hashCode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AccountEnvironmentId that = (AccountEnvironmentId) o;
        return Objects.equals(environmentId, that.environmentId) &&
                Objects.equals(accountId, that.accountId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(environmentId, accountId);
    }
}
