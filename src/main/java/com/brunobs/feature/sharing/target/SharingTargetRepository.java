package com.brunobs.feature.sharing.target;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface SharingTargetRepository extends JpaRepository<SharingTarget, Long> {



    @Query("""
                SELECT s FROM SharingTarget s
                WHERE s.id = :id
                    AND s.application.id = :applicationId
                    AND s.application.deletedAt is null
                    AND s.application.account.id = :accountId
                    AND s.application.account.deletedAt is null
            """)
    Optional<SharingTarget> findSharing(Long accountId, Long applicationId, Long id);


    @Query("""
             SELECT s FROM SharingTarget s
             WHERE  s.application.id = :applicationId
                 AND s.application.deletedAt is null
                 AND s.application.account.id = :accountId
                 AND s.application.account.deletedAt  is null
            """)
    List<SharingTarget> findByAccountAndApplication(Long accountId, Long applicationId);

    boolean existsByNameAndApplicationIdAndIdNot(String name, Long applicationId, Long id);

}
