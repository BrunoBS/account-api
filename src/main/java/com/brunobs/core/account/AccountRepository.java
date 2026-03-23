package com.brunobs.core.account;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {


    boolean existsByNameAndDeletedAtIsNullAndIdNot(String name, Long id);

    Optional<Account> findByIdAndDeletedAtIsNull(Long id);

    Optional<Account> findByNameAndDeletedAtIsNull(String id);

    List<Account> findByDeletedAtIsNull();

    List<Account> findByDeletedAtIsNotNull();

    boolean existsByIdAndDeletedAtIsNull(Long id);


    boolean existsByNameAndDeletedAtIsNull(String finalName);
}
