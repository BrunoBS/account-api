package com.brunobs.cache.sync.repository;

import com.brunobs.core.account.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuthorizationRepository extends JpaRepository<Account, Long> {


    @Query(value = """
            -- 1. NÍVEL: CONTA
            SELECT 
                ac.id AS idAccount, NULL AS idEnvironment, NULL AS idApplication,
                CONCAT('PM5-', :defaultType, '_', ac.acronym, IF(ac.authorizer_group > '', CONCAT('_', TRIM(ac.authorizer_group)), '')) AS authorizerGroup
            FROM accounts ac
            WHERE ac.deleted_at IS NULL AND (:idConta IS NULL OR ac.id = :idConta)
            
            UNION ALL
            
            -- 2. NÍVEL: CONTA + AMBIENTE
            SELECT 
                ac.id, env.id, NULL,
                CONCAT('PM5-', COALESCE(at_env.name, :defaultType), '_', ac.acronym, 
                    IF(ac.authorizer_group > '', CONCAT('_', TRIM(ac.authorizer_group)), ''),
                    IF(env.authorizer_group > '', CONCAT('_', TRIM(env.authorizer_group)), ''))
            FROM accounts ac
            JOIN environments env ON (env.id IN (SELECT environment_id FROM accounts_environments WHERE account_id = ac.id) OR env.type_environments_id = 1)
            LEFT JOIN type_authorizations at_env ON at_env.id = env.type_authorizations_id
            WHERE ac.deleted_at IS NULL AND env.active = 1 AND (:idConta IS NULL OR ac.id = :idConta)
            
            UNION ALL
            
            -- 3. NÍVEL: CONTA + AMBIENTE + APP
            SELECT 
                ac.id, env.id, ap.id,
                CONCAT('PM5-', COALESCE(at_env.name, :defaultType), '_', ac.acronym, 
                    IF(ac.authorizer_group > '', CONCAT('_', TRIM(ac.authorizer_group)), ''),
                    IF(env.authorizer_group > '', CONCAT('_', TRIM(env.authorizer_group)), ''),
                    IF(ap.authorizer_group > '', CONCAT('_', TRIM(ap.authorizer_group)), ''))
            FROM accounts ac
            JOIN environments env ON (env.id IN (SELECT environment_id FROM accounts_environments WHERE account_id = ac.id) OR env.type_environments_id = 1)
            JOIN applications ap ON ap.account_id = ac.id
            LEFT JOIN type_authorizations at_env ON at_env.id = env.type_authorizations_id
            WHERE ac.deleted_at IS NULL AND env.active = 1 AND ap.deleted_at IS NULL AND (:idConta IS NULL OR ac.id = :idConta)
            
            UNION ALL
            
            -- 4. NOVO NÍVEL: CONTA + APP (GERA UMA LINHA PARA CADA TIPO EXISTENTE NA TABELA)
            SELECT 
                ac.id AS idAccount,
                ta.name AS idEnvironment, -- Pega o nome do tipo direto da tabela (DEV, TST, ADM, etc)
                ap.id AS idApplication,
                CONCAT('PM5-', ta.name, '_', ac.acronym, 
                    IF(ac.authorizer_group > '', CONCAT('_', TRIM(ac.authorizer_group)), ''),
                    IF(ap.authorizer_group > '', CONCAT('_', TRIM(ap.authorizer_group)), '')) AS authorizerGroup
            FROM accounts ac
            JOIN applications ap ON ap.account_id = ac.id
            CROSS JOIN type_authorizations ta -- Aqui está a mágica: multiplica as apps pelos tipos disponíveis
            WHERE ac.deleted_at IS NULL 
              AND ap.deleted_at IS NULL 
              AND (:idConta IS NULL OR ac.id = :idConta)
            """, nativeQuery = true)
    List<AuthorizationResult> findAuthorizationsByAccount(
            @Param("idConta") Long idConta,
            @Param("defaultType") String defaultType
    );
}