package com.brunobs.features.sharing.origin;

import com.brunobs.core.publisher.Publisher;
import com.brunobs.features.sharing.target.SharingTarget;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface SharingOriginRepository extends JpaRepository<SharingOrigin, Long> {


    List<SharingOrigin> findBySharingTargetId(Long id);

    Optional<SharingOrigin> findByIdAndSharingTarget(Long id, SharingTarget target);

    @Query(value = """
            SELECT
               so.id AS idSharingOrigin,
               st.id AS idSharingTarget,
               st.name AS sharingName,
               st.description AS sharingDescription,
               app.id AS applicationTargetId,
               app.name AS applicationTargetName,
               acc.id AS targetAccountId,
               acc.name AS targetAccountName,
               COALESCE(sst.label, 'Não Solicitado') AS shareStatusLabel,
               COALESCE(sst.name, 'NOT_REQUESTED') AS shareStatusName
            FROM sharing_target st
                INNER JOIN applications app  ON app.id = st.application_id 
                INNER JOIN accounts acc   ON app.account_id = acc.id 
                LEFT JOIN sharing_origin so   ON so.share_target_id = st.id   AND so.application_id = :applicationId  
                LEFT JOIN share_status_types sst   ON sst.id = so.share_status_type_id 
            WHERE
                app.deleted_at IS NULL
                AND (:idAccountTarget IS NULL OR app.account_id = :idAccountTarget)
                AND (:idApplicationTarget IS NULL OR st.application_id = :idApplicationTarget)
                AND (:shareStatusType IS NULL OR sst.name = :shareStatusType)
            """, nativeQuery = true)
    List<SharingOriginProjection> findAvailableSharings(
            @Param("applicationId") Long applicationId,
            @Param("idAccountTarget") Long idAccountTarget,
            @Param("idApplicationTarget") Long idApplicationTarget,
            @Param("shareStatusType") String shareStatusType
    );

    @Query(value = """
        SELECT
            so.id AS idSharingOrigin,
            st.id AS idSharing,
            st.name AS sharingName,
            st.description AS sharingDescription,
            app.id AS applicationTargetId,
            app.name AS applicationTargetName,
            acc.id AS targetAccountId,
            acc.name AS targetAccountName,
            COALESCE(sst.label, 'Não Solicitado') AS shareStatusLabel,
            COALESCE(sst.name, 'NOT_REQUESTED') AS shareStatusName
        FROM sharing_origin so
            INNER JOIN sharing_target st 
                ON st.id = so.share_target_id
            INNER JOIN applications app 
                ON app.id = st.application_id
            INNER JOIN accounts acc 
                ON acc.id = app.account_id
            INNER JOIN share_status_types sst 
                ON sst.id = so.share_status_type_id
        WHERE
            so.application_id = :applicationId
            AND app.deleted_at IS NULL
            AND (:sharingOriginId IS NULL OR so.id = :sharingOriginId)
            AND (:idAccountTarget IS NULL OR app.account_id = :idAccountTarget)
            AND (:idApplicationTarget IS NULL OR st.application_id = :idApplicationTarget)
            AND (:shareStatusType IS NULL OR sst.name = :shareStatusType)
        """, nativeQuery = true)
    List<SharingOriginProjection> findSharingOrigins(
            @Param("applicationId") Long applicationId,
            @Param("sharingOriginId") Long sharingOriginId,
            @Param("idAccountTarget") Long idAccountTarget,
            @Param("idApplicationTarget") Long idApplicationTarget,
            @Param("shareStatusType") String shareStatusType
    );






}

