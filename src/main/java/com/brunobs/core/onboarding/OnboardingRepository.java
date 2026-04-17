package com.brunobs.core.onboarding;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OnboardingRepository extends JpaRepository<AccountOnboardingCompletion, Long> {

    Optional<AccountOnboardingCompletion> findFirstByAccountIdAndOnboardingPhaseId(Long accountId, Long onboardingPhase);

    AccountOnboardingCompletion findByAccountId(Long accountId);


    @Query(value = """
            
            SELECT
            t.sort_order as orderPhase,
            t.name,
                 t.label,
                 t.description,
                 t.orientation,
            IFNULL(DATE_FORMAT(o.completion_date, '%d/%m/%Y %H:%i:%s'), '-') AS completionDate,
                 IFNULL(o.user_identifier, '-') userIdentifier,
                 CASE
                     WHEN o.completion_date IS NOT NULL THEN 'COMPLETED'
                     WHEN t.sort_order = MIN(CASE WHEN o.completion_date IS NULL THEN t.sort_order END) OVER ()
                         THEN 'IN_PROGRESS'
                     ELSE 'PENDING'
                 END AS status
             FROM type_onboardings t
             LEFT JOIN accounts_onboarding_completions o
                    ON o.type_onboardings_id = t.id
                   AND o.account_id = :accountId
             INNER JOIN accounts a
                    ON a.id = :accountId
                   AND a.deleted_at IS NULL
             ORDER BY t.sort_order;
            """, nativeQuery = true)
    List<OnboardingProgressProjection> findProgressByAccountId(@Param("accountId") Long accountId);
}
