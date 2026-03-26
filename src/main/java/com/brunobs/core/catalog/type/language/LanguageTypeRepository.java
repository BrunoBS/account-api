package com.brunobs.core.catalog.type.language;


import com.brunobs.shared.base.BaseRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for LanguageType catalog.
 * Inherits generic CRUD and search-by-name operations from BaseRepository.
 */
@Repository
public interface LanguageTypeRepository extends BaseRepository<LanguageType, Long> {

}
