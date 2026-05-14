package com.brunobs.common.scenario;

import com.brunobs.client.AccountClient;
import com.brunobs.client.AccountTypeClient;
import com.brunobs.client.OnboardingPhaseClient;
import com.brunobs.common.factory.AccountTypeFactory;
import com.brunobs.common.factory.OnboardingPhaseFactory;
import com.brunobs.core.catalog.type.account.AccountTypeDTO;
import com.brunobs.core.onboarding.phase.OnboardingPhaseDTO;

import java.util.ArrayList;
import java.util.List;

public class CatalogScenario {

    private final AccountTypeFactory accountTypeFactory = new AccountTypeFactory();
    private final OnboardingPhaseFactory onboardingPhaseFactory = new OnboardingPhaseFactory();
    private final List<AccountTypeDTO> accountTypes = new ArrayList<>();
    private final List<OnboardingPhaseDTO> onboardingPhases = new ArrayList<>();

    public static CatalogScenario builder() {
        return new CatalogScenario();
    }

    private CatalogScenario() {
    }

    public CatalogScenario withAccountTypes() {
        this.accountTypes.clear();
        this.accountTypes.addAll(accountTypeFactory.all());
        return this;
    }

    public CatalogScenario withOnboardingPhases() {
        this.onboardingPhases.clear();
        this.onboardingPhases.addAll(onboardingPhaseFactory.all());
        return this;
    }


    public void setup() {

        if (!accountTypes.isEmpty()) {
            AccountTypeClient.client()
                    .create(accountTypes)
                    .expect2xx();
        }

        if (!onboardingPhases.isEmpty()) {
            OnboardingPhaseClient.client()
                    .create(onboardingPhases)
                    .expect2xx();
        }
    }



    public List<AccountTypeDTO> accountTypes() {
        return List.copyOf(accountTypes);
    }

    public List<OnboardingPhaseDTO> onboardingPhases() {
        return List.copyOf(onboardingPhases);
    }
}