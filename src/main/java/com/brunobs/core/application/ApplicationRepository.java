package com.brunobs.core.application;

import com.brunobs.core.account.Account;
import com.brunobs.core.catalog.type.applicationscope.ApplicationScopeType;
import com.brunobs.core.catalog.type.infrastructure.InfrastructureType;
import com.brunobs.core.catalog.type.language.LanguageType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import org.springframework.data.jpa.repository.EntityGraph;
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

    @EntityGraph(attributePaths = {"tags", "languageType", "applicationScopeType", "infrastructureType"})
    Optional<Application> findByNameAndAccountIdAndDeletedAtIsNullAndAccountDeletedAtIsNull(
            String name,
            Long accountId
    );

    @EntityGraph(attributePaths = {"tags", "languageType", "applicationScopeType", "infrastructureType"})
    Optional<Application> findByIdAndAccountIdAndDeletedAtIsNullAndAccountDeletedAtIsNull(
            Long id,
            Long accountId
    );

    @EntityGraph(attributePaths = {"tags", "languageType", "applicationScopeType", "infrastructureType"})
    Optional<Application> findByIdAndAccountIdAndDeletedAtIsNotNullAndAccountDeletedAtIsNull(
            Long id,
            Long accountId
    );

    @EntityGraph(attributePaths = {"tags", "languageType", "applicationScopeType", "infrastructureType"})
    List<Application> findByAccountIdAndDeletedAtIsNullAndAccountDeletedAtIsNull(Long accountId);

    @EntityGraph(attributePaths = {"tags", "languageType", "applicationScopeType", "infrastructureType"})
    List<Application> findByAccountIdAndDeletedAtIsNullAndAccountDeletedAtIsNotNull(Long accountId);

    boolean existsByNameAndDeletedAtIsNullAndAccountId(String finalName, Long accountId);
}
