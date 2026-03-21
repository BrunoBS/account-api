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

    @Column(name = "IDENTIFIER", length = 36, nullable = false, unique = true)
    private UUID identifier;


    @Column(nullable = false)
    private String name;

    @Column(nullable = false, length = 500)
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "application_id", nullable = false)
    private Application application;

    @ManyToMany
    @JoinTable(
            name = "sharing_target_features", // tabela de join
            joinColumns = @JoinColumn(name = "sharing_target_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "feature_type_id", referencedColumnName = "id")
    )
    @OrderBy("sortOrder ASC")
    private List<FeatureType> features;

    @Column(nullable = false)
    private boolean active = true;


    public SharingTarget() {
        identifier = UUID.randomUUID();
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Application getApplication() {
        return application;
    }

    public void setApplication(Application application) {
        this.application = application;
    }

    public List<FeatureType> getFeatures() {
        return features;
    }

    public void setFeatures(List<FeatureType> features) {
        this.features = features;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
