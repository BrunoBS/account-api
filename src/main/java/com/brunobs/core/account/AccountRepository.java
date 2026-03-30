package com.brunobs.core.account;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {


    @EntityGraph(attributePaths = {"accountType", "accountApprovers", "tags"})
    Optional<Account> findByIdAndDeletedAtIsNull(Long id);

    @EntityGraph(attributePaths = {"accountType", "accountApprovers", "tags"})
    Optional<Account> findByNameAndDeletedAtIsNull(String name);

    @EntityGraph(attributePaths = {"accountType", "accountApprovers", "tags"})
    List<Account> findByDeletedAtIsNull();

    @EntityGraph(attributePaths = {"accountType", "accountApprovers", "tags"})
    List<Account> findByDeletedAtIsNotNull();

    boolean existsByNameAndDeletedAtIsNullAndIdNot(String name, Long id);

    boolean existsByIdAndDeletedAtIsNull(Long id);

    boolean existsByNameAndDeletedAtIsNull(String name);
}
