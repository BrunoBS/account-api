package com.brunobs.core.configuration.environment.application;


import com.brunobs.core.configuration.PublisherConfig;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "applications_environments")
public class ApplicationEnvironment {

    @EmbeddedId
    private ApplicationEnvironmentId id;

    @OneToMany(
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @JoinTable(
            name = "applications_environment_publishers",
            joinColumns = {
                    @JoinColumn(name = "application_id", referencedColumnName = "application_id"),
                    @JoinColumn(name = "environment_id", referencedColumnName = "environment_id")
            },
            inverseJoinColumns = @JoinColumn(name = "publisher_id")
    )
    @OrderBy("order ASC")
    private List<PublisherConfig> publishers = new ArrayList<>();

    @Column(name = "settings", columnDefinition = "TEXT")
    private String settings;

    public String getSettings() {
        return settings;
    }

    public void setSettings(String settings) {
        this.settings = settings;
    }

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
