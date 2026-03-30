package com.brunobs.core.environment;

import com.brunobs.core.account.Account;
import com.brunobs.core.catalog.type.authorization.AuthorizationType;
import com.brunobs.core.catalog.type.environment.EnvironmentType;
import jakarta.persistence.*;

@Entity
@Table(name = "environments")
public class Environment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "type_authorizations_id", nullable = false)
    private AuthorizationType authorizationType;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "type_environments_id", nullable = false)
    private EnvironmentType type;

    @Column(nullable = false)
    private boolean active;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "account_id")
    private Account account;

    @Column(nullable = false, name = "sort_order")
    private Integer sortOrder;

    // Construtor padrão para JPA
    public Environment() {}

    // Getters e Setters Padronizados
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public AuthorizationType getAuthorizationType() { return authorizationType; }
    public void setAuthorizationType(AuthorizationType authorizationType) { this.authorizationType = authorizationType; }

    public EnvironmentType getType() { return type; }
    public void setType(EnvironmentType type) { this.type = type; }

    public boolean isActive() { return active; }
    public void setActive(boolean active) { this.active = active; }

    public Account getAccount() { return account; }
    public void setAccount(Account account) { this.account = account; }

    public Integer getSortOrder() { return sortOrder; }
    public void setSortOrder(Integer sortOrder) { this.sortOrder = sortOrder; }
}
