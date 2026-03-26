package com.brunobs.core.catalog.feature.type;

import com.brunobs.core.catalog.common.BaseType;
import com.brunobs.core.catalog.feature.scope.FeatureScopeType;
import jakarta.persistence.*;

/**
 * Entity representing the Nature of a Feature (e.g., TOGGLE, MENU, RULE).
 * Linked to a specific Scope (ACCOUNT, APPLICATION).
 */
@Entity
@Table(name = "feature_types")
public class FeatureType extends BaseType {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "feature_scope_type_id", nullable = false)
    private FeatureScopeType featureScope;

    @Column(nullable = false)
    private boolean available;

    @Column(columnDefinition = "TEXT")
    private String settings;

    public FeatureType() {
        super();
    }

    public FeatureScopeType getFeatureScope() {
        return featureScope;
    }

    public void setFeatureScope(FeatureScopeType featureScope) {
        this.featureScope = featureScope;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public String getSettings() {
        return settings;
    }

    public void setSettings(String settings) {
        this.settings = settings;
    }
}
