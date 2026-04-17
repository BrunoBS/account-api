package com.brunobs.core.account.repository;

import com.brunobs.core.account.Account;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
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

    @Query(value = """
            SELECT 
                a.id AS id, 
                a.name AS name, 
                a.description AS description,
                DATE_FORMAT(a.created_at, '%d/%m/%Y') AS createdAt,
                ta.name AS type,
                a.identifier AS identifier,
                a.acronym AS acronym,
                a.authorizer_group AS authorizerGroup,
                COUNT(DISTINCT e.account_id) AS totalEnvironments,
                COUNT(DISTINCT aep.publisher_id) AS totalPublishers,
                COUNT(DISTINCT app.id) AS totalApplications
            FROM accounts a
            INNER JOIN accounts_tags t ON a.id = t.account_id 
            INNER JOIN type_accounts ta ON ta.id = a.type_accounts_id
            LEFT JOIN accounts_environments e ON e.account_id = a.id
            LEFT JOIN accounts_environment_publishers aep ON aep.account_id = e.account_id
            LEFT JOIN applications app ON app.account_id = a.id
            WHERE ((:active IS TRUE AND a.deleted_at IS NULL) OR (:active IS FALSE AND a.deleted_at IS NOT NULL))
            
            AND (:typeName IS NULL OR LOWER(ta.name) = LOWER(:typeName))
            AND (:tagName IS NULL OR LOWER(t.name) LIKE LOWER(CONCAT('%', :tagName, '%')))
            GROUP BY a.id, a.name, a.identifier, a.authorizer_group, a.acronym, a.description, ta.name, a.created_at
            """, nativeQuery = true)
    List<AccountSummaryProjection> findAllSummaries(
            @Param("active") Boolean active,
            @Param("typeName") String typeName,
            @Param("tagName") String tagName
    );

    @EntityGraph(attributePaths = {"tags", "accountApprovers", "accountType"})
    @Query("""
            SELECT DISTINCT a FROM Account a
            LEFT JOIN FETCH a.tags
            LEFT JOIN FETCH a.accountApprovers
            INNER JOIN a.accountType ta
            INNER JOIN a.tags filterTag 
            WHERE (
                (:active = true AND a.deletedAt IS NULL) 
                OR 
                (:active = false AND a.deletedAt IS NOT NULL)
            )
            AND (:typeName IS NULL OR LOWER(ta.name) = LOWER(:typeName))
            AND (:tagName IS NULL OR LOWER(filterTag.name) LIKE LOWER(CONCAT('%', :tagName, '%')))
            """)
    List<Account> findAllFull(
            @Param("active") Boolean active,
            @Param("typeName") String typeName,
            @Param("tagName") String tagName
    );


}
