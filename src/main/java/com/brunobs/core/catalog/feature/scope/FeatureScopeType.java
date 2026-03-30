package com.brunobs.core.catalog.feature.scope;

import com.brunobs.core.catalog.common.BaseType;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;


@Entity
@Table(name = "type_feature_scopes") // Plural, snake_case e inglês
public class FeatureScopeType extends BaseType {

}
