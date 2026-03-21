package com.brunobs.core.configuration.environment.application;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class ApplicationEnvironmentId implements Serializable {
    @Column(name = "ENVIRONMENT_ID")
    private Long environmentId;

    @Column(name = "APPLICATION_ID")
    private Long applicationId;

    public ApplicationEnvironmentId() {}

    public ApplicationEnvironmentId(Long environmentId, Long applicationId) {
        this.environmentId = environmentId;
        this.applicationId = applicationId;
    }


    public Long getEnvironmentId() { return environmentId; }
    public void setEnvironmentId(Long environmentId) { this.environmentId = environmentId; }

    public Long getApplicationId() { return applicationId; }
    public void setApplicationId(Long applicationId) { this.applicationId = applicationId; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ApplicationEnvironmentId that = (ApplicationEnvironmentId) o;
        return Objects.equals(environmentId, that.environmentId) && 
               Objects.equals(applicationId, that.applicationId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(environmentId, applicationId);
    }
}
