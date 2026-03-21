package com.brunobs.core.onboarding.type;

import com.brunobs.shared.BaseEnum;

/**
 * Enum representando os tipos de fluxos de Onboarding.
 * As constantes técnicas são curtas, enquanto as descrições ficam no campo 'label'.
 */
public enum OnboardingTypeEnum implements BaseEnum<OnboardingTypeEnum> {


    ACCOUNT_FIRST_ENVIRONMENT,

    ACCOUNT_REGISTRATION,

    APPLICATION_FIRST_ENVIRONMENT,

    FIRST_APPLICATION_REGISTRATION;


}
