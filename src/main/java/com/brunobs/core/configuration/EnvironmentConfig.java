package com.brunobs.core.configuration;

import jakarta.persistence.*;

/**
 * Base class for environment-specific settings.
 * Using @MappedSuperclass to allow field sharing with child entities.
 */
@MappedSuperclass
public abstract class EnvironmentConfig {

    @Column(name = "AUTHORIZER_GROUP", nullable = false) // Tradução de grupo_autorizador
    private String authorizerGroup;

    public String getAuthorizerGroup() {
        return authorizerGroup;
    }

    public void setAuthorizerGroup(String authorizerGroup) {
        this.authorizerGroup = authorizerGroup;
    }
}
