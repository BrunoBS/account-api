package com.brunobs.core.application;

import com.brunobs.core.account.Account;
import com.brunobs.core.catalog.type.applicationscope.ApplicationScopeType;
import com.brunobs.core.catalog.type.infrastructure.InfrastructureType;
import com.brunobs.core.catalog.type.language.LanguageType;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(
        name = "applications",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_application_name_active_account",
                        columnNames = {"name", "deleted_at", "account_id"}
                )
        }
)
public class Application {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "identifier", length = 36, nullable = false, unique = true)
    private UUID identifier;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "alias", nullable = false)
    private String alias;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id")
    private Account account;

    @ManyToOne()
    @JoinColumn(name = "type_languages_id")
    private LanguageType languageType;

    @ManyToOne()
    @JoinColumn(name = "type_application_scopes_id")
    private ApplicationScopeType applicationScopeType;

    @ManyToOne()
    @JoinColumn(name = "type_infrastructures_id")
    private InfrastructureType infrastructureType;

    @Column(name = "authorizer_group", nullable = false)
    private String authorizerGroup;

    @Column(name = "parameter", columnDefinition = "TEXT")
    private String parameters;

    @Column(name = "is_default", nullable = false)
    private boolean isDefault;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "update_at", nullable = false)
    private LocalDateTime updatedAt;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;


    @OneToMany(mappedBy = "application", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ApplicationTag> tags = new ArrayList<>();


    public void addTag(ApplicationTag tag) {
        tags.add(tag);
        tag.setApplication(this);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UUID getIdentifier() {
        return identifier;
    }

    public void setIdentifier(UUID identifier) {
        this.identifier = identifier;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public LanguageType getLanguageType() {
        return languageType;
    }

    public void setLanguageType(LanguageType languageType) {
        this.languageType = languageType;
    }

    public ApplicationScopeType getApplicationScopeType() {
        return applicationScopeType;
    }

    public void setApplicationScopeType(ApplicationScopeType applicationScopeType) {
        this.applicationScopeType = applicationScopeType;
    }

    public InfrastructureType getInfrastructureType() {
        return infrastructureType;
    }

    public void setInfrastructureType(InfrastructureType infrastructureType) {
        this.infrastructureType = infrastructureType;
    }

    public String getAuthorizerGroup() {
        return authorizerGroup;
    }

    public void setAuthorizerGroup(String authorizerGroup) {
        this.authorizerGroup = authorizerGroup;
    }

    public String getParameters() {
        return parameters;
    }

    public void setParameters(String parameters) {
        this.parameters = parameters;
    }

    public boolean isDefault() {
        return isDefault;
    }

    public void setDefault(boolean aDefault) {
        isDefault = aDefault;
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

    public List<ApplicationTag> getTags() {
        return tags;
    }

    public void setTags(List<ApplicationTag> tags) {
        this.tags = tags;
    }
}
