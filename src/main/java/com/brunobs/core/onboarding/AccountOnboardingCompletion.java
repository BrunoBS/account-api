package com.brunobs.core.onboarding;

import com.brunobs.core.onboarding.phase.OnboardingPhase;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "accounts_onboarding_completions")
public class AccountOnboardingCompletion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "account_id", nullable = false)
    private Long accountId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "type_onboardings_id", nullable = false)
    private OnboardingPhase onboardingPhase;

    @Column(name = "user_identifier", nullable = false)
    private String user;

    @Column(name = "completion_date", nullable = false)
    private LocalDateTime completionDate;

    public AccountOnboardingCompletion() {}

    public AccountOnboardingCompletion(Long accountId, OnboardingPhase onboardingPhase, String user) {
        this.accountId = accountId;
        this.onboardingPhase = onboardingPhase;
        this.user = user;
        this.completionDate = LocalDateTime.now();
    }

    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getAccountId() { return accountId; }
    public void setAccountId(Long accountId) { this.accountId = accountId; }

    public OnboardingPhase getOnboardingType() { return onboardingPhase; }
    public void setOnboardingType(OnboardingPhase onboardingPhase) { this.onboardingPhase = onboardingPhase; }

    public String getUser() { return user; }
    public void setUser(String user) { this.user = user; }

    public LocalDateTime getCompletionDate() { return completionDate; }
    public void setCompletionDate(LocalDateTime completionDate) { this.completionDate = completionDate; }
}
