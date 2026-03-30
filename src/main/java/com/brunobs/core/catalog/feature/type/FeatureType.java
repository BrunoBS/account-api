package com.brunobs.core.catalog.feature.type;

import com.brunobs.core.catalog.common.BaseType;
import com.brunobs.core.catalog.feature.scope.FeatureScopeType;
import jakarta.persistence.*;

/**
 * Entity representing the Nature of a Feature (e.g., TOGGLE, MENU, RULE).
 * Linked to a specific Scope (ACCOUNT, APPLICATION).
 */
@Entity
@Table(name = "type_features", indexes = {
        @Index(name = "idx_scope_name", columnList = "type_feature_scopes_id, name", unique = true)
})
@AttributeOverride(
        name = "name",
        column = @Column(name = "name", nullable = false, length = 50)
)
public class FeatureType extends BaseType {

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "type_feature_scopes_id", nullable = false)
    private FeatureScopeType featureScope;

    @Column(nullable = false)
    private boolean available;

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

}
