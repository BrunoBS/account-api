package com.brunobs.shared;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;
import java.util.Optional;

/**
 * Base repository for all catalog entities.
 * Includes generic search by technical name, existence checks,
 * and sequence-based ordering.
 */
@NoRepositoryBean
public interface BaseRepository<T, ID> extends JpaRepository<T, ID> {

    /** Finds a record by its unique technical name (e.g., 'PRODUCTION'). */
    Optional<T> findByName(String name);

    /** Checks if a name is already taken by another record during updates. */
    boolean existsByNameAndIdNot(String name, ID id);

    /** Checks if a name exists (useful for creation). */
    boolean existsByName(String name);

    /** Finds all active records sorted by their display order. */
    Iterable<T> findAllByActiveTrueOrderBySortOrderAsc();

    /**
     * Finds the latest record in the sequence, useful for
     * automatically suggesting the next sortOrder.
     */
    Optional<T> findFirstByOrderBySortOrderDesc();

    /** Finds the previous record relative to a specific ID. */
    Optional<T> findFirstByIdNotOrderBySortOrderDesc(ID id);
}
