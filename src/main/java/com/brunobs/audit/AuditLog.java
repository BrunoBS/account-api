package com.brunobs.audit;

import jakarta.persistence.*;

import java.time.Instant;

@Entity
@Table(name = "audit_logs") // Nome da tabela no plural e em inglês
public class AuditLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(name = "system_name", nullable = false)
    private String system;


    @Column(name = "user_name", nullable = false)
    private String username;

    @Column(name = "auditable_action", nullable = false)
    private String action;

    @Column(name = "entity_id") // Snake_case para colunas
    private String entityId;

    @Column(name = "payload", nullable = false, columnDefinition = "LONGTEXT")
    private String payload;

    @Column(name = "http_status")
    private Integer httpStatus;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    // Construtor obrigatório JPA
    public AuditLog() {
    }

    public AuditLog(String system, String username, String action, String entityId,
                    Integer httpStatus, String payload) {
        this.system = system;
        this.username = username;
        this.action = action;
        this.entityId = entityId;
        this.httpStatus = httpStatus;
        this.payload = payload;
        this.createdAt = Instant.now(); // Data gerada automaticamente na criação
    }

    // Getters e Setters padronizados
    public Long getId() {
        return id;
    }

    public String getSystem() {
        return system;
    }

    public void setSystem(String system) {
        this.system = system;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getEntityId() {
        return entityId;
    }

    public void setEntityId(String entityId) {
        this.entityId = entityId;
    }

    public String getPayload() {
        return payload;
    }

    public void setPayload(String payload) {
        this.payload = payload;
    }

    public Integer getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(Integer httpStatus) {
        this.httpStatus = httpStatus;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }
}
