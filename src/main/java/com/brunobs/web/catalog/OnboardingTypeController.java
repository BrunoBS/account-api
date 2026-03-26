package com.brunobs.web.catalog;

import com.brunobs.core.onboarding.phase.OnboardingPhase;
import com.brunobs.core.onboarding.phase.OnboardingPhaseDTO;
import com.brunobs.core.onboarding.phase.OnboardingPhaseService;
import com.brunobs.shared.base.BaseController;
import com.brunobs.shared.base.BaseService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/onboarding-type")
public class OnboardingTypeController extends BaseController<OnboardingPhaseDTO, OnboardingPhase, Long> {

    private final OnboardingPhaseService service;

    public OnboardingTypeController(OnboardingPhaseService service) {
        this.service = service;
    }

    @Override
    protected BaseService<OnboardingPhase, OnboardingPhaseDTO, Long> getService() {
        return service;
    }
}
