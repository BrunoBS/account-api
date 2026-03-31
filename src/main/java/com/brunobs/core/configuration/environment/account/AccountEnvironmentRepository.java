package com.brunobs.core.configuration.environment.account;

import com.brunobs.core.configuration.PublisherProjection;
import com.brunobs.core.configuration.environment.account.dto.AccountConfigurationProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AccountEnvironmentRepository extends JpaRepository<AccountEnvironment, AccountEnvironmentId> {


    boolean existsByIdAccountIdAndIdEnvironmentId(Long accountId, Long environmentId);
    List<AccountEnvironment> findByIdAccountId(Long accountId);

    List<AccountEnvironment> findByIdEnvironmentId(Long environmentId);

    Optional<AccountEnvironment> findByIdAccountIdAndIdEnvironmentId(Long accountId, Long environmentId);

    void deleteByIdAccountIdAndIdEnvironmentId(Long accountId, Long environmentId);

    @Query(value = """
            
            SELECT\s
                     ROW_NUMBER() OVER (ORDER BY authType.sort_order, env.sort_order, env.id) AS indexRow,
                     COALESCE(accEnv.account_id, COALESCE(env.account_id, :accountId)) AS accountId,
                     env.id AS environmentId,
                     env.name AS environmentName,
                     envType.description AS environmentTypeDescription,
                     envType.name AS environmentTypeName,
                     authType.description AS authorizationTypeDescription,
                     authType.name AS authorizationTypeName,
                     env.description AS description,
                     env.active AS active,
                     CASE\s
                         WHEN accEnv.account_id IS NOT NULL THEN 1
                         ELSE 0 
                     END AS isConfigured
                 FROM environments env
                 INNER JOIN type_environments envType\s
                     ON envType.id = env.type_environments_id
                 INNER JOIN type_authorizations authType\s
                     ON authType.id = env.type_authorizations_id
                 LEFT JOIN accounts_environments accEnv\s
                     ON env.id = accEnv.environment_id \s
                     AND accEnv.account_id = :accountId
                 WHERE env.active = true
                 AND (envType.name = 'DEFAULT' OR env.account_id = :accountId)
                 AND (:environmentId IS NULL OR env.id = :environmentId)
                 ORDER BY authType.sort_order, env.sort_order, env.id
            """, nativeQuery = true)
    List<AccountConfigurationProjection> findConfigurationsByAccount(
            @Param("accountId") Long accountId,
            @Param("environmentId") Long environmentId
    );

    @Query(value = """
            
            SELECT
                pub.name AS name,
                pubConfig.parameters AS parameters,
                pubConfig.order_index AS orderIndex
            FROM accounts_environment_publishers accEnvPub
            JOIN publisher_configs pubConfig\s
                 ON pubConfig.id = accEnvPub.publisher_id
            JOIN publishers pub
                 ON pub.id = pubConfig.publisher_id
            WHERE accEnvPub.account_id = :accountId
              AND accEnvPub.environment_id = :environmentId
            ORDER BY pubConfig.order_index
            """, nativeQuery = true)
    List<PublisherProjection> findPublishersByEnvironment(
            @Param("accountId") Long accountId,
            @Param("environmentId") Long environmentId
    );
}
