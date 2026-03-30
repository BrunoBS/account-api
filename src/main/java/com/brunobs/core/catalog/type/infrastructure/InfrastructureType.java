package com.brunobs.core.catalog.type.infrastructure;

import com.brunobs.core.catalog.common.BaseType;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

/**
 * Entity representing types of infrastructure (e.g., CLOUD, ON_PREMISE, HYBRID).
 * Inherits common catalog fields from BaseType.
 */
@Entity
@Table(name = "type_infrastructures") // Plural e snake_case
public class InfrastructureType extends BaseType {

}
