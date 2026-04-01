package com.brunobs.core.environment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EnvironmentRepository extends JpaRepository<Environment, Long> {

    List<Environment> findByAccountIdAndTypeIdAndActiveOrderByAuthorizationTypeSortOrderAsc(Long accountId, Long typeId, boolean active);

    Optional<Environment> findByTypeIdAndIdAndActiveTrue(Long typeId, Long id);

    Optional<Environment> findByIdAndActiveTrue(Long id);

    List<Environment> findByTypeIdAndActiveOrderByAuthorizationTypeSortOrderAsc(Long typeId, boolean active);


    Optional<Environment> findFirstByTypeIdAndAccountIsNullAndActiveTrueOrderByIdDesc(Long typeId);

    Optional<Environment> findFirstByTypeIdAndAccountIdAndActiveTrueOrderByIdDesc(Long typeId, Long accountId);

    boolean existsByNameAndTypeNameAndIdNotAndActiveTrue(String name, String typeName, Long id);

    boolean existsByNameAndAccountIdAndIdNotAndActiveTrue(String name, Long accountId, Long id);

    Optional<Environment>  findByIdAndTypeIdAndAccountIdAndActive(Long id, Long  idTypeName, Long accountId, Boolean active);
    Optional<Environment>  findByIdAndTypeIdAndAccountIdIsNullAndActive(Long id, Long idTypeName, Boolean active);
}
