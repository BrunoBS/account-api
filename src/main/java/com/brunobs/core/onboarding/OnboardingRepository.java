package com.brunobs.core.onboarding;

import com.brunobs.core.onboarding.type.OnboardingProgressProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OnboardingRepository extends JpaRepository<AccountOnboardingCompletion, Long> {

    List<AccountOnboardingCompletion> findByAccountId(Long accountId);

    @Query(value = """
        SELECT t.id as id, t.name as name, t.step_order as order, 
               CASE 
                   WHEN o.id IS NOT NULL THEN 'COMPLETED'
                   ELSE NULL 
               END as status
        FROM onboarding_types t
        LEFT JOIN account_onboarding_completions o 
               ON o.onboarding_type_id = t.id AND o.account_id = :accountId
        ORDER BY t.step_order
        """, nativeQuery = true)
    List<OnboardingProgressProjection> findProgressByAccountId(@Param("accountId") Long accountId);
}
