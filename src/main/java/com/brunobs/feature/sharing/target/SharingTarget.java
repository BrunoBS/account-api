package com.brunobs.feature.sharing.target;

import com.brunobs.core.application.Application;
import com.brunobs.core.catalog.feature.type.FeatureType;
import jakarta.persistence.*;

import java.util.List;
import java.util.UUID;

@Entity
@Table(
        name = "sharing_targets",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_app_name", columnNames = {"application_id", "name"}),
                @UniqueConstraint(name = "uk_app_hash", columnNames = {"application_id", "hash_features"})
        },
        indexes = {
                @Index(name = "idx_application", columnList = "application_id")
        }
)
public class SharingTarget {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "identifier", length = 36, nullable = false, unique = true)
    private String identifier;


    @Column(nullable = false)
    private String name;


    @Column(name = "hash_features", nullable = false, length = 64)
    private String hashFeatures;

    @Column(nullable = false, length = 500)
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "application_id", nullable = false)
    private Application application;

    @ManyToMany
    @JoinTable(
            name = "sharing_target_features", // tabela de join
            joinColumns = @JoinColumn(name = "sharing_targets_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "type_features_id", referencedColumnName = "id")
    )
    @OrderBy("sortOrder ASC")
    private List<FeatureType> features;


    public SharingTarget() {
        identifier = UUID.randomUUID().toString();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
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

    public String getHashFeatures() {
        return hashFeatures;
    }

    public void setHashFeatures(String hashFeatures) {
        this.hashFeatures = hashFeatures;
    }
}
