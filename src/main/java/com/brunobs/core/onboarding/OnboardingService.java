package com.brunobs.core.onboarding; // Corrigido: 'onbording' para 'onboarding'


import com.brunobs.core.onboarding.type.OnboardingProgressProjection;
import com.brunobs.core.onboarding.type.OnboardingType;
import com.brunobs.core.onboarding.type.OnboardingTypeEnum;
import com.brunobs.core.onboarding.type.OnboardingTypeService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class OnboardingService {

    private final OnboardingRepository onboardingRepository;
    private final OnboardingTypeService onboardingTypeService;

    public OnboardingService(OnboardingRepository onboardingRepository, OnboardingTypeService onboardingTypeService) {
        this.onboardingRepository = onboardingRepository;
        this.onboardingTypeService = onboardingTypeService;
    }

    @Transactional
    public void registerStageCompletion(Long accountId, OnboardingTypeEnum onboardingTypeEnum, String user) {
        OnboardingType onboardingType = onboardingTypeService.findByName(onboardingTypeEnum.name());

        AccountOnboardingCompletion record = new AccountOnboardingCompletion();
        record.setAccountId(accountId);
        record.setOnboardingType(onboardingType);
        record.setUser(user);
        record.setCompletionDate(LocalDateTime.now());

        onboardingRepository.save(record);
    }

    @Transactional(readOnly = true)
    public List<OnboardingProgressProjection> getAccountProgress(Long accountId) {
        List<OnboardingProgressProjection> progressByAccountId = onboardingRepository.findProgressByAccountId(accountId);
        return progressByAccountId;
    }
}
