package com.brunobs.core.onboarding.phase;


import com.brunobs.message.feature.CatalogMessages;
import com.brunobs.shared.base.BaseService;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;


@Service
public class OnboardingPhaseService extends BaseService<OnboardingPhase, OnboardingPhaseDTO, Long> {

    public OnboardingPhaseService(OnboardingPhaseRepository repository,
                                  OnboardingPhaseMapper mapper,
                                  OnboardingPhaseValidator validator,
                                  CatalogMessages catalogMessages) {
        super(repository, mapper, validator, catalogMessages);
    }

    @Override
    public String getServiceIdentifier() {
        return "Onboarding Type";
    }
}
