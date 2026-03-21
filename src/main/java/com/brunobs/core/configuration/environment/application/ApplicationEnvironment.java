package com.brunobs.core.configuration.environment.application;


import com.brunobs.core.configuration.EnvironmentConfig;
import com.brunobs.core.configuration.PublisherConfig;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "application_environment")
public class ApplicationEnvironment extends EnvironmentConfig {

    @EmbeddedId
    private ApplicationEnvironmentId id;

    @OneToMany(
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @JoinTable(
            name = "APPLICATION_ENVIRONMENT_PUBLISHERS",
            joinColumns = {
                    @JoinColumn(name = "APPLICATION_ID", referencedColumnName = "APPLICATION_ID"),
                    @JoinColumn(name = "ENVIRONMENT_ID", referencedColumnName = "ENVIRONMENT_ID")
            },
            inverseJoinColumns = @JoinColumn(name = "PUBLISHER_CONFIGURATION_ID")
    )
    @OrderBy("order ASC")
    private List<PublisherConfig> publishers = new ArrayList<>();

    public Long getApplicationId() {
        return id != null ? id.getApplicationId() : null;
    }

    public Long getEnvironmentId() {
        return id != null ? id.getEnvironmentId() : null;
    }

    public void setApplicationId(Long applicationId) {
        if (id == null) id = new ApplicationEnvironmentId();
        id.setApplicationId(applicationId);
    }

    public void setEnvironmentId(Long environmentId) {
        if (id == null) id = new ApplicationEnvironmentId();
        id.setEnvironmentId(environmentId);
    }

    public List<PublisherConfig> getPublishers() {
        return publishers;
    }

    public void setPublishers(List<PublisherConfig> publishers) {
        this.publishers = publishers;
    }
}
