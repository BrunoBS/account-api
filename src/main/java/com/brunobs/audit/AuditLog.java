package com.brunobs.audit;

import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;

import java.time.Instant;


@Entity
@Table(name = "api_audit_events")
public class AuditLog {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(length = 36, updatable = false, nullable = false)
    private String codIdefEvent;

    @Column(nullable = false)
    private String codIdefEnti; //identificador do registro (entidade)

    @Column(nullable = false)
    private String codIdefAmbi; //  default:  yaml audit.product

    @Column(nullable = false)
    private Instant datHorEven = Instant.now();

    @Column(nullable = false)
    private String entityEvent;  // action

    @Column(columnDefinition = "TEXT")
    private String metadataEvent;  // json do response

    @Column(nullable = false)
    private String origemEvent;   //  yaml audit.origin

    @Column(nullable = false)
    private String productEvent;   //  yaml audit.product

    @Column(nullable = false)
    private String typeEvent;  //INSERT, UPDATE, DELETE

    @Column(nullable = false)
    private String userEvent;  //usuario

    public String getCodIdefEvent() {
        return codIdefEvent;
    }

    public void setCodIdefEvent(String codIdefEvent) {
        this.codIdefEvent = codIdefEvent;
    }

    public String getCodIdefEnti() {
        return codIdefEnti;
    }

    public void setCodIdefEnti(String codIdefEnti) {
        this.codIdefEnti = codIdefEnti;
    }

    public String getCodIdefAmbi() {
        return codIdefAmbi;
    }

    public void setCodIdefAmbi(String codIdefAmbi) {
        this.codIdefAmbi = codIdefAmbi;
    }

    public Instant getDatHorEven() {
        return datHorEven;
    }

    public void setDatHorEven(Instant datHorEven) {
        this.datHorEven = datHorEven;
    }

    public String getEntityEvent() {
        return entityEvent;
    }

    public void setEntityEvent(String entityEvent) {
        this.entityEvent = entityEvent;
    }

    public String getMetadataEvent() {
        return metadataEvent;
    }

    public void setMetadataEvent(String metadataEvent) {
        this.metadataEvent = metadataEvent;
    }

    public String getOrigemEvent() {
        return origemEvent;
    }

    public void setOrigemEvent(String origemEvent) {
        this.origemEvent = origemEvent;
    }

    public String getProductEvent() {
        return productEvent;
    }

    public void setProductEvent(String productEvent) {
        this.productEvent = productEvent;
    }

    public String getTypeEvent() {
        return typeEvent;
    }

    public void setTypeEvent(String typeEvent) {
        this.typeEvent = typeEvent;
    }

    public String getUserEvent() {
        return userEvent;
    }

    public void setUserEvent(String userEvent) {
        this.userEvent = userEvent;
    }
}




