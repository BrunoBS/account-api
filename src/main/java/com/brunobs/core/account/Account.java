package com.brunobs.core.account;

import com.brunobs.core.catalog.type.account.AccountType; // TipoConta -> AccountType
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "ACCOUNT") // conta -> ACCOUNT
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "IDENTIFIER", length = 36, nullable = false, unique = true) // identificador -> IDENTIFIER
    private UUID identifier;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ACCOUNT_TYPE_ID", nullable = false) // tipo_id -> ACCOUNT_TYPE_ID
    private AccountType type;

    @Column(name = "NAME", nullable = false) // nome -> NAME
    private String name;

    @Column(name = "DESCRIPTION", nullable = false) // descricao -> DESCRIPTION
    private String description;

    @Column(name = "REQUESTER", nullable = false) // solicitante -> REQUESTER
    private String requester;

    @Column(name = "INITIALS", nullable = false) // sigla -> INITIALS (ou ACRONYM)
    private String initials;

    @Column(name = "PARAMETERS", columnDefinition = "TEXT")
    private String parameters;

    @Column(name = "AUTHORIZER_GROUP", nullable = false) // Tradução de grupo_autorizador
    private String authorizerGroup;

    @Column(name = "EMAIL_GROUP", nullable = false) // grupoEmail -> EMAIL_GROUP
    private String emailGroup;

    @Column(name = "IS_ONBOARDING", nullable = false) // onboarding -> IS_ONBOARDING
    private boolean onboarding;

    @Column(name = "IS_ACTIVE", nullable = false) // situacao -> IS_ACTIVE (mais semântico que situacao)
    private boolean active;

    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Approver> approvers = new ArrayList<>(); // aprovadores -> approvers

    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AccountTag> tags = new ArrayList<>();

    // Helper methods para sincronização de coleções bidirecionais (Boa prática JPA)
    public void addApprover(Approver approver) {
        approvers.add(approver);
        approver.setAccount(this);
    }

    public void addTag(AccountTag tag) {
        tags.add(tag);
        tag.setAccount(this);
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public UUID getIdentifier() { return identifier; }
    public void setIdentifier(UUID identifier) { this.identifier = identifier; }

    public AccountType getType() { return type; }
    public void setType(AccountType type) { this.type = type; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getRequester() { return requester; }
    public void setRequester(String requester) { this.requester = requester; }

    public String getInitials() { return initials; }
    public void setInitials(String initials) { this.initials = initials; }

    public String getParameters() { return parameters; }
    public void setParameters(String parameters) { this.parameters = parameters; }

    public String getAuthorizerGroup() {return authorizerGroup;}

    public void setAuthorizerGroup(String authorizerGroup) {this.authorizerGroup = authorizerGroup;}

    public String getEmailGroup() { return emailGroup; }
    public void setEmailGroup(String emailGroup) { this.emailGroup = emailGroup; }

    public boolean isOnboarding() { return onboarding; }
    public void setOnboarding(boolean onboarding) { this.onboarding = onboarding; }

    public boolean isActive() { return active; } // isSituacao -> isActive
    public void setActive(boolean active) { this.active = active; }

    public List<Approver> getApprovers() { return approvers; }
    public void setApprovers(List<Approver> approvers) { this.approvers = approvers; }

    public List<AccountTag> getTags() { return tags; }
    public void setTags(List<AccountTag> tags) { this.tags = tags; }
}
