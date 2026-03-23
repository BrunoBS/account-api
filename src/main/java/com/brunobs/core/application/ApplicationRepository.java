package com.brunobs.core.application;

import com.brunobs.core.account.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ApplicationRepository extends JpaRepository<Application, Long> {


    boolean existsByNameAndAccountIdAndDeletedAtIsNullAndAccountDeletedAtIsNullAndIdNot(
            String name,
            Long accountId,
            Long id
    );

    Optional<Application> findByNameAndAccountIdAndDeletedAtIsNullAndAccountDeletedAtIsNull(
            String name,
            Long accountId
    );

    Optional<Application> findByIdAndAccountIdAndDeletedAtIsNullAndAccountDeletedAtIsNull(
            Long id,
            Long accountId
    );

    Optional<Application> findByIdAndAccountIdAndDeletedAtIsNullAndAccountDeletedAtIsNotNull(
            Long id,
            Long accountId
    );


    List<Application> findByAccountIdAndDeletedAtIsNullAndAccountDeletedAtIsNull(Long accountId);

    List<Application> findByAccountIdAndDeletedAtIsNullAndAccountDeletedAtIsNotNull(Long accountId);
}