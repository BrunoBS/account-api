package com.brunobs.core.catalog.feature.type;

import com.brunobs.shared.BaseRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository for FeatureType.
 * Includes custom validation to ensure unique feature names within the same scope.
 */
@Repository
public interface FeatureTypeRepository extends BaseRepository<FeatureType, Long> {

    boolean existsByNameAndFeatureScopeNameAndIdNot(String name, String scopeName, Long id);

}
