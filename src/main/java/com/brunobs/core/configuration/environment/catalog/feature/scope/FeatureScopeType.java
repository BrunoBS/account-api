package com.brunobs.core.catalog.feature.scope;

import com.brunobs.core.catalog.common.BaseType;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

/**
 * Entity representing the scope of a feature (e.g., GLOBAL, ACCOUNT, APPLICATION).
 * Extends BaseType to inherit common catalog fields like Name, Label, and Active status.
 */
@Entity
@Table(name = "feature_scope_types") // Plural, snake_case e inglês
public class FeatureScopeType extends BaseType {

}
