package com.brunobs.config.security.repository;

import com.brunobs.core.account.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorizationRepository extends JpaRepository<Account, Long> {

    @Query(value = """
            
            SELECT
                                   ac.initials as sigla,
                                   COALESCE(at_env.name, :defaultName) AS name,
                                   COALESCE(
                                       ae.authorizer_group,
                                       ap.authorizer_group,
                                       ac_env.authorizer_group,
                                       ac.authorizer_group
                                   ) AS authorizerGroup
                               FROM accounts ac
                   				LEFT JOIN environments env   ON env.id = :environmentId  AND env.active = 1
                   				LEFT JOIN authorization_types at_env ON at_env.id = env.authorization_type_id
                   				LEFT JOIN account_environment ac_env ON ac_env.account_id = ac.id AND ( ac_env.environment_id = :environmentId)
                   				LEFT JOIN application_environment ae ON ( ae.environment_id = :environmentId)  AND ( ae.application_id = :applicationId)
                   				LEFT JOIN applications ap  ON ( ap.id = NULL)  AND ap.account_id = ac.id  AND ap.deleted_at IS NULL
                               WHERE ac.id = :accountId  and ac.deleted_at is null
                               LIMIT 1
            """, nativeQuery = true)
    AuthorizationResult findAuthorization(
            String accountId,
            String environmentId,
            String applicationId,
            String defaultName
    );
}