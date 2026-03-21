package com.brunobs.core.onboarding.type;

import com.brunobs.core.catalog.common.BaseType;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

/**
 * Entity representing types of onboarding processes (e.g., USER, ACCOUNT, PARTNER).
 * Inherits common catalog fields from BaseType.
 */
@Entity
@Table(name = "onboarding_types") // Plural, snake_case e inglês
public class OnboardingType extends BaseType {


}
