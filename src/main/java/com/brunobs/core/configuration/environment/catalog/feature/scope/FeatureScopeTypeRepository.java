package com.brunobs.core.catalog.feature.scope;

import com.brunobs.shared.BaseRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for FeatureScopeType catalog.
 * Inherits generic CRUD and search-by-name operations from BaseRepository.
 */
@Repository
public interface FeatureScopeTypeRepository extends BaseRepository<FeatureScopeType, Long> {

}