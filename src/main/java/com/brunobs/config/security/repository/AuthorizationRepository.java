package com.brunobs.config.security.repository;

import com.brunobs.core.account.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorizationRepository extends JpaRepository<Account, Long> {

    @Query(value = """
            
                    SELECT
                                                  ac.acronym AS sigla,
                                                  COALESCE(at_env.name, :defaultName) AS name,
                                                  CONCAT(
                                                      'PM5-',
                                                      COALESCE(at_env.name, :defaultName),
                                                      '_',
                                                   --    ac.acronym,
            
                                                         CASE
                                                             WHEN ac.authorizer_group IS NOT NULL AND TRIM(ac.authorizer_group) != ''
                                                             THEN CONCAT('_', ac.authorizer_group)
                                                             ELSE ''
                                                         END,
            
                                                         -- 2º AMBIENTE (CUSTOM)
                                                         CASE
                                                             WHEN ty_env.name = 'CUSTOM' AND env.authorizer_group IS NOT NULL AND TRIM(env.authorizer_group) != ''
                                                             THEN CONCAT('_', env.authorizer_group)
                                                             ELSE ''
                                                         END,
            
                                                         -- 3º APLICAÇÃO
                                                         CASE
                                                             WHEN ap.authorizer_group IS NOT NULL AND TRIM(ap.authorizer_group) != ''
                                                             THEN CONCAT('_', ap.authorizer_group)
                                                             ELSE ''
                                                         END
                                                  ) AS authorizerGroup
                                              FROM accounts ac
                                              LEFT JOIN environments env ON env.id = :environmentId AND env.active = 1
                                              LEFT JOIN type_environments ty_env ON ty_env.id = env.type_environments_id
                                              LEFT JOIN type_authorizations at_env ON at_env.id = env.type_authorizations_id
                                              LEFT JOIN accounts_environments ac_env ON ac_env.account_id = ac.id AND ac_env.environment_id = env.id
                                              LEFT JOIN applications ap ON ap.id = :applicationId AND ap.account_id = ac.id AND ap.deleted_at IS NULL
                                              WHERE ac.id = :accountId AND ac.deleted_at IS NULL
                                              LIMIT 1
            """, nativeQuery = true)
    AuthorizationResult findAuthorization(
            String accountId,
            String environmentId,
            String applicationId,
            String defaultName
    );
}