
package com.brunobs.core.configuration.environment.application;

import com.brunobs.core.configuration.PublisherProjection;
import com.brunobs.core.configuration.environment.account.AccountEnvironmentId;
import com.brunobs.core.configuration.environment.application.dto.ApplicationConfigurationProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface ApplicationEnvironmentRepository extends JpaRepository<ApplicationEnvironment, AccountEnvironmentId> {

    List<ApplicationEnvironment> findByIdEnvironmentId(Long environmentId);

    Collection<ApplicationEnvironment> findByIdApplicationId(Long applicationId);

    void deleteByIdApplicationIdAndIdEnvironmentId(Long applicationId, Long environmentId);

    Optional<ApplicationEnvironment> findByIdApplicationIdAndIdEnvironmentId(Long applicationId, Long environmentId);

    @Query(value = """
            
                SELECT
                ROW_NUMBER() OVER (ORDER BY authType.sort_order, env.sort_order, env.id) AS indexRow,
                app.id AS applicationId,
            	app.name AS applicationName,
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
                appEnv.settings AS settings,
                CASE WHEN appEnv.application_id IS NOT NULL THEN 1   ELSE 0  END AS isConfigured
            
            FROM applications app
            INNER JOIN accounts acc on acc.id = app.account_id
            CROSS JOIN environments env
            INNER JOIN type_environments envType  ON envType.id = env.type_environments_id
            INNER JOIN type_authorizations authType ON authType.id = env.type_authorizations_id
            
            LEFT JOIN applications_environments appEnv
                ON appEnv.application_id = app.id
                AND appEnv.environment_id = env.id
            
            WHERE env.active = true
              AND (:applicationId IS NULL OR app.id =:applicationId)
              AND (:environmentId IS NULL OR env.id = :environmentId)
              AND app.account_id =:accountId
              AND (envType.name = 'DEFAULT' OR env.account_id = acc.id)
              AND app.deleted_at IS NULL
              AND acc.deleted_at IS NULL
            ORDER BY authType.sort_order, env.sort_order, env.id;
            """, nativeQuery = true)
    List<ApplicationConfigurationProjection> findConfigurationsByApplication(Long accountId,Long applicationId, Long environmentId);

    @Query(value = """
       SELECT
                pub.name AS name,
                pubConfig.parameters AS parameters,
                pubConfig.order_index AS orderIndex
            FROM applications_environments appEnvPub
            JOIN publisher_configs pubConfig
                 ON pubConfig.id = appEnvPub.publisher_id
            JOIN publishers pub
                 ON pub.id = pubConfig.publisher_id
            WHERE appEnvPub.application_id = :applicationId
              AND appEnvPub.environment_id = :environmentId
            ORDER BY pubConfig.order_index
        """, nativeQuery = true)
    List<PublisherProjection> findPublishersByEnvironment(Long applicationId, Long environmentId);

}