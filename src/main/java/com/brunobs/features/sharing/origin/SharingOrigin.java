package com.brunobs.features.sharing.origin;

import jakarta.persistence.*;

@Entity
@Table(name = "sharing_origin")
public class SharingOrigin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "sharing_id", nullable = false)
    private Long sharingId;

    @Column(name = "application_id", nullable = false)
    private Long applicationId;

    @Column(nullable = false, length = 500)
    private String reason;

    @Column(nullable = false)
    private boolean active = true;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSharingId() {
        return sharingId;
    }

    public void setSharingId(Long sharingId) {
        this.sharingId = sharingId;
    }

    public Long getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(Long applicationId) {
        this.applicationId = applicationId;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
