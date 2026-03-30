package com.brunobs.core.catalog.type.language;

import com.brunobs.core.catalog.common.BaseType;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

/**
 * Entity representing programming languages (e.g., JAVA, PYTHON, NODEJS).
 * Inherits common catalog fields from BaseType.
 */
@Entity
@Table(name = "type_languages") // Plural, snake_case e inglês
public class LanguageType extends BaseType {
}
