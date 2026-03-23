package com.brunobs.core.onboarding;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OnboardingRepository extends JpaRepository<AccountOnboardingCompletion, Long> {

    List<AccountOnboardingCompletion> findByAccountId(Long accountId);

    @Query(value = """
            
            SELECT
                t.name,
                t.label,
                t.description,
                t.sort_order as orderPhase,
                CASE
                    WHEN o.completion_date IS NOT NULL THEN 'COMPLETED'
                    WHEN t.sort_order = MIN(CASE WHEN o.completion_date IS NULL THEN t.sort_order END) OVER ()
                        THEN 'IN_PROGRESS'
                    ELSE 'PENDING'
                END AS status
            FROM onboarding_types t
            LEFT JOIN account_onboarding_completions o
                   ON o.onboarding_type = t.id
                  AND o.account_id = :accountId
            INNER JOIN accounts a
                   ON a.id = :accountId
                  AND a.deleted_at IS NULL
            ORDER BY t.sort_order;
            """, nativeQuery = true)
    List<OnboardingProgressProjection> findProgressByAccountId(@Param("accountId") Long accountId);
}
