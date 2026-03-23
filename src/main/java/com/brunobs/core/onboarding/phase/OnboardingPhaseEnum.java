package com.brunobs.core.onboarding.phase;

import com.brunobs.shared.BaseEnum;

/**
 * Enum representando os tipos de fluxos de Onboarding.
 * As constantes técnicas são curtas, enquanto as descrições ficam no campo 'label'.
 */
public enum OnboardingPhaseEnum implements BaseEnum<OnboardingPhaseEnum> {

    ACCOUNT_REGISTRATION,
    ACCOUNT_FIRST_ENVIRONMENT,
    FIRST_APPLICATION_REGISTRATION,
    APPLICATION_FIRST_ENVIRONMENT ;


}
