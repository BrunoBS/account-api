package com.brunobs.core.onboarding;

import com.brunobs.core.onboarding.type.OnboardingType;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "account_onboarding_completions") // Plural para tabelas é o padrão comum
public class AccountOnboardingCompletion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "account_id", nullable = false)
    private Long accountId;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "onboarding_type", nullable = false) // tipo_id -> ACCOUNT_TYPE_ID
    private OnboardingType onboardingType;

    @Column(name = "user_identifier", nullable = false)
    private String user;

    @Column(name = "completion_date", nullable = false)
    private LocalDateTime completionDate;

    // Construtor padrão exigido pelo Hibernate
    public AccountOnboardingCompletion() {}

    // Construtor para facilitar a criação (substitui o builder no código simples)
    public AccountOnboardingCompletion(Long accountId, OnboardingType onboardingType, String user) {
        this.accountId = accountId;
        this.onboardingType = onboardingType;
        this.user = user;
        this.completionDate = LocalDateTime.now();
    }

    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getAccountId() { return accountId; }
    public void setAccountId(Long accountId) { this.accountId = accountId; }

    public OnboardingType getOnboardingType() { return onboardingType; }
    public void setOnboardingType(OnboardingType onboardingType) { this.onboardingType = onboardingType; }

    public String getUser() { return user; }
    public void setUser(String user) { this.user = user; }

    public LocalDateTime getCompletionDate() { return completionDate; }
    public void setCompletionDate(LocalDateTime completionDate) { this.completionDate = completionDate; }
}
