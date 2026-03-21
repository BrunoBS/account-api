package com.brunobs.core.environment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EnvironmentRepository extends JpaRepository<Environment, Long> {

    List<Environment> findByAccountIdAndTypeIdAndActiveOrderByAuthorizationTypeSortOrderAsc(Long accountId, Long typeId, boolean active);

    Optional<Environment> findByTypeIdAndIdAndActive(Long typeId, Long id, boolean active);

    Optional<Environment> findByIdAndActive(Long id, boolean active);

    List<Environment> findByTypeIdAndActiveOrderByAuthorizationTypeSortOrderAsc(Long typeId, boolean active);

    Optional<Environment> findFirstByTypeIdAndAccountIdAndActiveOrderByIdDesc(Long typeId, Long accountId, boolean active);

    Optional<Environment> findFirstByTypeIdAndAccountIsNullAndActiveOrderByIdDesc(Long typeId, boolean active);

    boolean existsByNameAndTypeNameAndIdNot(String name, String typeName, Long id);

    boolean existsByNameAndAccountIdAndIdNot(String name, Long accountId, Long id);
}
