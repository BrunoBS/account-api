package com.brunobs.core.configuration.environment.account;

import com.brunobs.core.configuration.environment.account.dto.AccountConfigurationProjection;
import com.brunobs.core.configuration.PublisherProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AccountEnvironmentRepository extends JpaRepository<AccountEnvironment, AccountEnvironmentId> {


    List<AccountEnvironment> findByIdAccountId(Long accountId);

    List<AccountEnvironment> findByIdEnvironmentId(Long environmentId);

    Optional<AccountEnvironment> findByIdAccountIdAndIdEnvironmentId(Long accountId, Long environmentId);

    void deleteByIdAccountIdAndIdEnvironmentId(Long accountId, Long environmentId);

    @Query(value = """
            SELECT 
                ROW_NUMBER() OVER (ORDER BY authType.ORDER_INDEX, env.ORDER_INDEX, env.ID) AS index_row,
                COALESCE(accEnv.ACCOUNT_ID, COALESCE(env.ACCOUNT_ID, :accountId)) AS accountId,
                env.ID AS environmentId,
                env.NAME AS environmentName,
                envType.DESCRIPTION AS environmentTypeDescription,
                envType.NAME AS environmentTypeName,
                authType.DESCRIPTION AS authorizationTypeDescription,
                authType.NAME AS authorizationTypeName,
                accEnv.AUTHORIZER_GROUP AS authorizerGroup,
                env.DESCRIPTION AS description,
                env.IS_ACTIVE AS active,
                CASE 
                    WHEN accEnv.ACCOUNT_ID IS NOT NULL THEN TRUE 
                    ELSE FALSE 
                END AS isConfigured
            FROM ENVIRONMENT env
            INNER JOIN ENVIRONMENT_TYPE envType 
                ON envType.ID = env.ENVIRONMENT_TYPE_ID
            INNER JOIN AUTHORIZATION_TYPE authType 
                ON authType.ID = env.AUTHORIZATION_TYPE_ID
            LEFT JOIN ACCOUNT_ENVIRONMENT accEnv 
                ON env.ID = accEnv.ENVIRONMENT_ID  
                AND accEnv.ACCOUNT_ID = :accountId
            WHERE env.IS_ACTIVE = true
            AND (envType.NAME = 'DEFAULT' OR env.ACCOUNT_ID = :accountId)
            AND (:environmentId IS NULL OR env.ID = :environmentId)
            ORDER BY authType.ORDER_INDEX, env.ORDER_INDEX, env.ID
            """, nativeQuery = true)
    List<AccountConfigurationProjection> findConfigurationsByAccount(
            @Param("accountId") Long accountId,
            @Param("environmentId") Long environmentId
    );

    @Query(value = """
        SELECT
            pub.NAME AS name,
            pubConfig.PARAMETERS AS parameters,
            pubConfig.ORDER_INDEX AS orderIndex
        FROM ACCOUNT_ENV_PUBLISHERS accEnvPub
        JOIN PUBLISHER_CONFIG pubConfig
             ON pubConfig.ID = accEnvPub.PUBLISHER_CONFIG_ID
        JOIN PUBLISHER pub
             ON pub.ID = pubConfig.PUBLISHER_ID
        WHERE accEnvPub.ACCOUNT_ID = :accountId
          AND accEnvPub.ENVIRONMENT_ID = :environmentId
        ORDER BY pubConfig.ORDER_INDEX
        """, nativeQuery = true)
    List<PublisherProjection> findPublishersByEnvironment(
            @Param("accountId") Long accountId,
            @Param("environmentId") Long environmentId
    );
}
