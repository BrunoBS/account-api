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
            SELECT
                ROW_NUMBER() OVER (ORDER BY authType.sort_order, env.sort_order, env.id) AS indexRow,
                acc.id AS accountId,
                acc.name AS accountName,
                env.id AS environmentId,
                env.name AS environmentName,
                env.authorizer_group  AS authorizerGroup,
                envType.description AS environmentTypeDescription,
                envType.label AS environmentTypeName,
                authType.description AS authorizationTypeDescription,
                authType.label AS authorizationTypeName,
                env.description AS description,
                accEnv.settings AS settings,
                            
                CASE 
                    WHEN accEnv.account_id IS NOT NULL THEN 1
                    ELSE 0 
                END AS isConfigured
            
            FROM accounts acc 
                CROSS JOIN environments env
                INNER JOIN type_environments envType  ON envType.id = env.type_environments_id
                INNER JOIN type_authorizations authType ON authType.id = env.type_authorizations_id
                LEFT JOIN accounts_environments accEnv
                    ON accEnv.account_id = acc.id
                    AND accEnv.environment_id = env.id
            WHERE
                acc.deleted_at IS NULL
                AND env.active = true
                AND (envType.name = 'DEFAULT' OR env.account_id = acc.id)
                AND (:accountId IS NULL OR acc.id = :accountId)
                AND (:environmentId IS NULL OR env.id = :environmentId)
            ORDER BY authType.sort_order, env.sort_order, env.id;
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
            JOIN publisher_configs pubConfig
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
