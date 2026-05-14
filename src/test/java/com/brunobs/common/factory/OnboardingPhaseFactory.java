package com.brunobs.common.factory;

import com.brunobs.common.builder.OnboardingPhaseBuilder;
import com.brunobs.core.onboarding.phase.OnboardingPhaseDTO;

import java.util.List;

public class OnboardingPhaseFactory
        extends BaseCatalogFactory<OnboardingPhaseDTO, OnboardingPhaseBuilder> {

    @Override
    protected OnboardingPhaseBuilder builder() {
        return OnboardingPhaseBuilder.builder();
    }

    // =========================================================
    // VALID STATES
    // =========================================================

    public OnboardingPhaseDTO accountRegistration() {
        return builder()
                .withName("ACCOUNT_REGISTRATION")
                .withLabel("Account Registration")
                .withDescription("Registro inicial dos dados da conta no sistema.")
                .withSortOrder(1)
                .build();
    }

    public OnboardingPhaseDTO accountFirstEnvironment() {
        return builder()
                .withName("ACCOUNT_FIRST_ENVIRONMENT")
                .withLabel("Account First Environment")
                .withDescription("Configuração do primeiro ambiente da conta.")
                .withSortOrder(2)
                .build();
    }

    public OnboardingPhaseDTO firstApplicationRegistration() {
        return builder()
                .withName("FIRST_APPLICATION_REGISTRATION")
                .withLabel("First Application Registration")
                .withDescription("Criação da primeira aplicação associada à conta.")
                .withSortOrder(3)
                .build();
    }

    public OnboardingPhaseDTO applicationFirstEnvironment() {
        return builder()
                .withName("APPLICATION_FIRST_ENVIRONMENT")
                .withLabel("Application First Environment")
                .withDescription("Vinculação da aplicação ao seu primeiro ambiente.")
                .withSortOrder(4)
                .build();
    }

    // =========================================================
    // COLLECTION
    // =========================================================

    public List<OnboardingPhaseDTO> all() {
        return List.of(
                accountRegistration(),
                accountFirstEnvironment(),
                firstApplicationRegistration(),
                applicationFirstEnvironment()
        );
    }

    // =========================================================
    // CUSTOM
    // =========================================================

    public OnboardingPhaseDTO custom(
            String name,
            String label,
            String description,
            Integer sortOrder,
            String orientation,
            String settings
    ) {
        return builder()
                .withName(name)
                .withLabel(label)
                .withDescription(description)
                .withSortOrder(sortOrder)
                .withOrientation(orientation)
                .withSettings(settings)
                .build();
    }

    public static OnboardingPhaseFactory factory() {
        return new OnboardingPhaseFactory();
    }
}