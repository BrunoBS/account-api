package com.brunobs.core.account;

import com.brunobs.core.catalog.type.account.AccountType;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.*;

@Entity
@Table(
        name = "accounts",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_account_name_active",
                        columnNames = {"name", "deleted_at"}
                )
        }
)
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "identifier", length = 36, nullable = false, unique = true)
    private String identifier;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "type_accounts_id", nullable = false)
    private AccountType accountType;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "request", nullable = false)
    private String requester;

    @Column(name = "acronym", nullable = false)
    private String acronym;

    @Column(name = "settings", columnDefinition = "TEXT")
    private String settings;

    @Column(name = "authorizer_group", nullable = false)
    private String authorizerGroup;

    @Column(name = "email_group", nullable = false)
    private String emailGroup;

    @Column(name = "onbording", nullable = false)
    private boolean onboarding;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "update_at", nullable = false)
    private LocalDateTime updatedAt;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<AccountAprover> accountApprovers = new LinkedHashSet<>(); // aprovadores -> approvers

    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<AccountTag> tags = new LinkedHashSet<>();

    public Account() {
        this.identifier = UUID.randomUUID().toString();
    }

    public void addApprover(AccountAprover accountApprover) {
        accountApprovers.add(accountApprover);
        accountApprover.setAccount(this);
    }

    public void addTag(AccountTag tag) {
        tags.add(tag);
        tag.setAccount(this);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public AccountType getAccountType() {
        return accountType;
    }

    public void setAccountType(AccountType accountType) {
        this.accountType = accountType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRequester() {
        return requester;
    }

    public void setRequester(String requester) {
        this.requester = requester;
    }

    public String getAcronym() {
        return acronym;
    }

    public void setAcronym(String acronym) {
        this.acronym = acronym;
    }

    public String getSettings() {
        return settings;
    }

    public void setSettings(String settings) {
        this.settings = settings;
    }

    public String getAuthorizerGroup() {
        return authorizerGroup;
    }

    public void setAuthorizerGroup(String authorizerGroup) {
        this.authorizerGroup = authorizerGroup;
    }

    public String getEmailGroup() {
        return emailGroup;
    }

    public void setEmailGroup(String emailGroup) {
        this.emailGroup = emailGroup;
    }

    public boolean isOnboarding() {
        return onboarding;
    }

    public void setOnboarding(boolean onboarding) {
        this.onboarding = onboarding;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public LocalDateTime getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(LocalDateTime deletedAt) {
        this.deletedAt = deletedAt;
    }

    public Set<AccountAprover> getApprovers() {
        return accountApprovers;
    }

    public void setApprovers(Set<AccountAprover> accountAprovers) {
        this.accountApprovers = accountAprovers;
    }

    public Set<AccountTag> getTags() {
        return tags;
    }

    public void setTags(Set<AccountTag> tags) {
        this.tags = tags;
    }
}
