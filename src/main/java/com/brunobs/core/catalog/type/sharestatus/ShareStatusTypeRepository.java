package com.brunobs.core.catalog.type.sharestatus;

import com.brunobs.shared.base.BaseRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for ShareStatusType catalog.
 * Inherits generic CRUD and search-by-name operations from BaseRepository.
 */
@Repository
public interface ShareStatusTypeRepository extends BaseRepository<ShareStatusType, Long> {

}
