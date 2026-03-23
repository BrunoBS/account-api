package com.brunobs.core.onboarding.phase;


import com.brunobs.shared.BaseService;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

/**
 * Service responsible for managing OnboardingType catalog lifecycle.
 * Inherits generic CRUD, Validation, and Mapping logic from BaseService.
 */
@Service
public class OnboardingPhaseService extends BaseService<OnboardingPhase, OnboardingPhaseDTO, Long> {

    public OnboardingPhaseService(OnboardingPhaseRepository repository,
                                  OnboardingPhaseMapper mapper,
                                  OnboardingPhaseValidator validator,
                                  MessageSource messageSource) {
        super(repository, mapper, validator, messageSource);
    }

    /**
     * Unique identifier used for auditing and error contexts.
     */
    @Override
    public String getServiceIdentifier() {
        return "Onboarding Type";
    }
}
