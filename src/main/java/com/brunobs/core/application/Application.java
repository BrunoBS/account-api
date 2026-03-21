package com.brunobs.core.application;

import com.brunobs.core.account.Account;
import com.brunobs.core.catalog.type.infrastructure.InfrastructureType;
import com.brunobs.core.catalog.type.language.LanguageType;
import com.brunobs.core.catalog.type.applicationscope.ApplicationScopeType;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "APPLICATION")
public class Application {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "IDENTIFIER", length = 36, nullable = false, unique = true) // identificador -> IDENTIFIER
    private UUID identifier;

    @Column(name = "NAME", nullable = false)
    private String name;

    @Column(name = "ALIAS", nullable = false)
    private String alias;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ACCOUNT_ID")
    private Account account;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "LANGUAGE_TYPE_ID")
    private LanguageType languageType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "APPLICATION_SCOPE_TYPE_ID")
    private ApplicationScopeType applicationScopeType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "INFRASTRUCTURE_TYPE_ID")
    private InfrastructureType infrastructureType;

    @Column(name = "AUTHORIZER_GROUP", nullable = false) // Tradução de grupo_autorizador
    private String authorizerGroup;

    @Column(name = "PARAMETERS", columnDefinition = "TEXT")
    private String parameters;

    @Column(name = "IS_DEFAULT", nullable = false)
    private boolean isDefault;

    @Column(name = "IS_ACTIVE", nullable = false)
    private boolean active;

    @OneToMany(mappedBy = "application", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ApplicationTag> tags = new ArrayList<>();


    public void addTag(ApplicationTag tag) {
        tags.add(tag);
        tag.setApplication(this);
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public UUID getIdentifier() { return identifier; }
    public void setIdentifier(UUID identifier) { this.identifier = identifier; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getAlias() { return alias; }
    public void setAlias(String alias) { this.alias = alias; }

    public Account getAccount() { return account; }
    public void setAccount(Account account) { this.account = account; }

    public LanguageType getLanguageType() { return languageType; }
    public void setLanguageType(LanguageType languageType) { this.languageType = languageType; }

    public ApplicationScopeType getApplicationScopeType() { return applicationScopeType; }
    public void setApplicationScopeType(ApplicationScopeType applicationScopeType) { this.applicationScopeType = applicationScopeType; }

    public InfrastructureType getInfrastructureType() { return infrastructureType; }
    public void setInfrastructureType(InfrastructureType infrastructureType) { this.infrastructureType = infrastructureType; }

    public String getParameters() { return parameters; }
    public void setParameters(String parameters) { this.parameters = parameters; }

    public String getAuthorizerGroup() {return authorizerGroup;}

    public void setAuthorizerGroup(String authorizerGroup) {this.authorizerGroup = authorizerGroup;}

    public boolean isDefault() { return isDefault; }
    public void setDefault(boolean isDefault) { this.isDefault = isDefault; }

    public boolean isActive() { return active; }
    public void setActive(boolean active) { this.active = active; }

    public List<ApplicationTag> getTags() { return tags; }
    public void setTags(List<ApplicationTag> tags) { this.tags = tags; }
}
