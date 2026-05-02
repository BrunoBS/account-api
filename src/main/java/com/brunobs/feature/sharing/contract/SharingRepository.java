package com.brunobs.feature.sharing.contract;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface SharingRepository extends JpaRepository<Sharing, Long> {



    @Query("""
                SELECT s FROM Sharing s
                WHERE s.id = :id
                    AND s.application.id = :applicationId
                    AND s.application.deletedAt is null
                    AND s.application.account.id = :accountId
                    AND s.application.account.deletedAt is null
            """)
    Optional<Sharing> findSharing(Long accountId, Long applicationId, Long id);


    @Query("""
             SELECT s FROM  Sharing s
             WHERE  s.application.id = :applicationId
                 AND s.application.deletedAt is null
                 AND s.application.account.id = :accountId
                 AND s.application.account.deletedAt  is null
            """)
    List<Sharing> findByAccountAndApplication(Long accountId, Long applicationId);

    boolean existsByNameIgnoreCaseAndApplicationIdAndIdNot(String name, Long applicationId, Long id);
    boolean existsByHashFeaturesAndApplicationIdAndIdNot(String hash, Long applicationId, Long id);

}
