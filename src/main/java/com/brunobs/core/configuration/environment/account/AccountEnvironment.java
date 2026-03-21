package com.brunobs.core.configuration.environment.account;


import com.brunobs.core.configuration.EnvironmentConfig;
import com.brunobs.core.configuration.PublisherConfig;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "ACCOUNT_ENVIRONMENT")
public class AccountEnvironment extends EnvironmentConfig {

    @EmbeddedId
    private AccountEnvironmentId id;

    @OneToMany(
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @JoinTable(
            name = "ACCOUNT_ENV_PUBLISHERS",
            joinColumns = {
                    @JoinColumn(name = "ACCOUNT_ID", referencedColumnName = "ACCOUNT_ID"),
                    @JoinColumn(name = "ENVIRONMENT_ID", referencedColumnName = "ENVIRONMENT_ID")
            },
            inverseJoinColumns = @JoinColumn(name = "PUBLISHER_CONFIG_ID")
    )
    @OrderBy("sortOrder ASC")
    private final List<PublisherConfig> publishers = new ArrayList<>();


    public List<PublisherConfig> getPublishers() {
        return publishers;
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
