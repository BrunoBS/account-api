package com.brunobs.core.catalog.type.authorization;

import com.brunobs.core.catalog.common.BaseType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

/**
 * Entity representing the type of authorization (e.g., OAUTH2, API_KEY, BASIC).
 * Includes a flexible 'settings' field for JSON or specific configurations.
 */
@Entity
@Table(name = "type_authorizations") // Plural e inglês
public class AuthorizationType extends BaseType {



}
