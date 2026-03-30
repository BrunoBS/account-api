package com.brunobs.audit;

import jakarta.persistence.*;

import java.time.Instant;


@Entity
@Table(name = "api_audit_events")
public class AuditLog {

    @Id
    private Long codIdefEvent;

    @Column(nullable = false)
    private Long codIdefEnti;

    @Column(nullable = false)
    private Long codIdefAmbi;

    @Column(nullable = false)
    private Instant datHorEven = Instant.now();

    @Column(nullable = false)
    private String entityEvent;

    @Column(columnDefinition = "TEXT")
    private String metadataEvent;

    @Column(nullable = false)
    private String origemEvent;

    @Column(nullable = false)
    private String productEvent;

    @Column(nullable = false)
    private String typeEvent;

    @Column(nullable = false)
    private String userEvent;
}