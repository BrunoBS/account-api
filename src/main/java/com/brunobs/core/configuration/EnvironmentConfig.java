package com.brunobs.core.configuration;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class EnvironmentConfig {

    @Column(name = "AUTHORIZER_GROUP", nullable = false)
    private String authorizerGroup;

    public String getAuthorizerGroup() {
        return authorizerGroup;
    }

    public void setAuthorizerGroup(String authorizerGroup) {
        this.authorizerGroup = authorizerGroup;
    }
}
