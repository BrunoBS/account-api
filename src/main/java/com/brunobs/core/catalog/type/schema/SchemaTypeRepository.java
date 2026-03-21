package com.brunobs.core.catalog.type.schema;

import com.brunobs.shared.BaseRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for SchemaType catalog.
 * Used to retrieve JSON Schema definitions for dynamic validation.
 */
@Repository
public interface SchemaTypeRepository extends BaseRepository<SchemaType, Long> {

}
