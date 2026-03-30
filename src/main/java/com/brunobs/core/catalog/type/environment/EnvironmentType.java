package com.brunobs.core.catalog.type.environment;

import com.brunobs.core.catalog.common.BaseType;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

/**
 * Entity representing the type of environment (e.g., Development, Production).
 * Extends BaseType to inherit common fields like ID, Name, and Code.
 */
@Entity
@Table(name = "type_environments") // Plural e snake_case
public class EnvironmentType extends BaseType {

}
