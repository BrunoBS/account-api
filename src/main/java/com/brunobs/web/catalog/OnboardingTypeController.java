package com.brunobs.web.catalog;

import com.brunobs.core.onboarding.type.OnboardingType;
import com.brunobs.core.onboarding.type.OnboardingTypeService;
import com.brunobs.shared.BaseController;
import com.brunobs.shared.BaseService;
import com.brunobs.core.onboarding.type.OnboardingTypeDTO;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/onboarding-type")
public class OnboardingTypeController extends BaseController<OnboardingTypeDTO, OnboardingType, Long> {

    private final OnboardingTypeService service;

    public OnboardingTypeController(OnboardingTypeService service) {
        this.service = service;
    }

    @Override
    protected BaseService<OnboardingType, OnboardingTypeDTO, Long> getService() {
        return service;
    }
}
