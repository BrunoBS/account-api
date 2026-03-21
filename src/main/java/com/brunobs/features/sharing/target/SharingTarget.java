package com.brunobs.features.sharing.target;

import jakarta.persistence.*;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "sharing_target")
public class SharingTarget {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private UUID identifier = UUID.randomUUID();

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, length = 500)
    private String description;

    @Column(name = "application_id", nullable = false)
    private Long applicationId;

    @ElementCollection
    @CollectionTable(name = "sharing_target_features", joinColumns = @JoinColumn(name = "sharing_target_id"))
    @Column(name = "feature_id")
    private List<Long> featureIds;

    @Column(nullable = false)
    private boolean active = true;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public Long getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(Long applicationId) {
        this.applicationId = applicationId;
    }

    public List<Long> getFeatureIds() {
        return featureIds;
    }

    public void setFeatureIds(List<Long> featureIds) {
        this.featureIds = featureIds;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
