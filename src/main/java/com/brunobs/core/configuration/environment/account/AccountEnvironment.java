package com.brunobs.core.configuration.environment.account;


import com.brunobs.core.configuration.PublisherConfig;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "accounts_environments")
public class AccountEnvironment {

    @EmbeddedId
    private AccountEnvironmentId id;

    @OneToMany(
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @JoinTable(
            name = "accounts_environment_publishers",
            joinColumns = {
                    @JoinColumn(name = "account_id", referencedColumnName = "account_id"),
                    @JoinColumn(name = "environment_id", referencedColumnName = "environment_id")
            },
            inverseJoinColumns = @JoinColumn(name = "publisher_id")
    )
    @OrderBy("order_index ASC")
    private final List<PublisherConfig> publishers = new ArrayList<>();

    public List<PublisherConfig> getPublishers() {
        return publishers;
    }

    @Column(name = "settings", columnDefinition = "TEXT")
    private String settings;

    public String getSettings() {
        return settings;
    }

    public void setSettings(String settings) {
        this.settings = settings;
    }

    public AccountEnvironmentId getId() {
        return id;
    }

    public void setId(AccountEnvironmentId id) {
        this.id = id;
    }

    public Long getAccountId() {
        return id != null ? id.getAccountId() : null;
    }

    public void setAccountId(Long accountId) {
        if (id == null) id = new AccountEnvironmentId();
        id.setAccountId(accountId);
    }

    public Long getEnvironmentId() {
        return id != null ? id.getEnvironmentId() : null;
    }

    public void setEnvironmentId(Long environmentId) {
        if (id == null) id = new AccountEnvironmentId();
        id.setEnvironmentId(environmentId);
    }
}
