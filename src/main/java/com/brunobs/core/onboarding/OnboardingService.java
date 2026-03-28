package com.brunobs.core.onboarding; // Corrigido: 'onbording' para 'onboarding'


import com.brunobs.core.account.Account;
import com.brunobs.core.onboarding.phase.OnboardingPhase;
import com.brunobs.core.onboarding.phase.OnboardingPhaseEnum;
import com.brunobs.core.onboarding.phase.OnboardingPhaseService;
import com.brunobs.exception.ValidationException;
import com.brunobs.message.feature.OnboardingMessages;
import com.brunobs.shared.validation.ValidationResult;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class OnboardingService {

    private final OnboardingRepository onboardingRepository;
    private final OnboardingPhaseService onboardingPhaseService;
    private final OnboardingMessages onboardingMessages;


    public OnboardingService(OnboardingRepository onboardingRepository, OnboardingPhaseService onboardingPhaseService, MessageSource messageSource, OnboardingMessages onboardingMessages) {
        this.onboardingRepository = onboardingRepository;
        this.onboardingPhaseService = onboardingPhaseService;

        this.onboardingMessages = onboardingMessages;
    }

    @Transactional
    public void registerStageCompletion(Long accountId, OnboardingPhaseEnum onboardingPhaseEnum, String user) {
        OnboardingPhase onboardingPhase = onboardingPhaseService.findByName(onboardingPhaseEnum.name());

        AccountOnboardingCompletion record = new AccountOnboardingCompletion();
        record.setAccountId(accountId);
        record.setOnboardingType(onboardingPhase);
        record.setUser(user);
        record.setCompletionDate(LocalDateTime.now());

        onboardingRepository.save(record);
    }


    public List<OnboardingProgressProjection> onboardingProgress(Long accountId) {

        List<OnboardingProgressProjection> progressByAccountId = onboardingRepository.findProgressByAccountId(accountId);
        if (progressByAccountId.isEmpty()) {
            throw new ValidationException(
                    new ValidationResult(Account.class.getSimpleName(), onboardingMessages.notFound()));
        }
        return progressByAccountId;
    }


}
