package com.brunobs.features.sharing.target;

import com.brunobs.core.application.Application;
import com.brunobs.core.catalog.feature.type.FeatureType;
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "application_id", nullable = false)
    private Application application;

    @ElementCollection
    @CollectionTable(name = "sharing_target_features", joinColumns = @JoinColumn(name = "sharing_target_id"))
    @Column(name = "feature_id")
    private List<FeatureType> featureIds;

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

    public Application getApplication() {
        return application;
    }

    public void setApplication(Application application) {
        this.application = application;
    }

    public List<FeatureType> getFeatureIds() {
        return featureIds;
    }

    public void setFeatureIds(List<FeatureType> featureIds) {
        this.featureIds = featureIds;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
