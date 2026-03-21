package com.brunobs.core.catalog.feature.type;

import com.brunobs.shared.BaseRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for FeatureType.
 * Includes custom validation to ensure unique feature names within the same scope.
 */
@Repository
public interface FeatureTypeRepository extends BaseRepository<FeatureType, Long> {

    /**
     * Checks if a feature with the same name already exists within a specific scope,
     * excluding a specific ID (used during updates).
     *
     * Convention: By{Attribute}{RelationshipAttribute}
     */
    boolean existsByNameAndFeatureScopeNameAndIdNot(String name, String scopeName, Long id);

}
