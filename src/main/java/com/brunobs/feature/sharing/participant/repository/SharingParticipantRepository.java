package com.brunobs.feature.sharing.participant.repository;

import com.brunobs.feature.sharing.participant.SharingParticipant;
import com.brunobs.feature.sharing.participant.repository.projection.*;
import com.brunobs.feature.sharing.contract.Sharing;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface SharingParticipantRepository extends JpaRepository<SharingParticipant, Long> {


    @Query(value = """
            SELECT 
                so.id AS id,
                app.id AS applicationId,
                app.name AS applicationName,
                acc.id AS accountId,
                acc.name AS accountName,
                tss.name AS statusName,
                tss.label AS statusLabel
            FROM sharing_origins so
            INNER JOIN type_sharing_statuses tss 
                ON tss.id = so.type_sharing_statuses_id
            INNER JOIN applications app 
                ON so.application_id = app.id
            INNER JOIN accounts acc 
                ON app.account_id = acc.id
            WHERE so.sharing_targets_id = :sharingTargetId
            """, nativeQuery = true)
    List<SharingOriginByTargetProjection> findBySharingId(@Param("sharingTargetId") Long sharingTargetId);

    boolean existsByIdAndSharing(Long id, Sharing target);

    List<SharingParticipant> findBySharing(Sharing target);
    Optional<SharingParticipant> findByIdAndSharing(Long id, Sharing target);


    @Query(value = """
            
            SELECT
                       acc.id AS accountId,
                       acc.name AS accountName,
                       app.id AS applicationId,
                       app.name AS applicationName
                   FROM applications app
                   INNER JOIN accounts acc ON acc.id = app.account_id
                   WHERE EXISTS (
                       SELECT 1
                       FROM sharing_targets sts
                       WHERE sts.application_id = app.id
                   )
                   ORDER BY acc.name, app.name;
            
            """, nativeQuery = true)
    List<AccountsWithAppsProjection> findAccountsWithApps();


    @Query(value = """
                 SELECT
                     st.id AS sharingId,
                     st.name AS sharingName,
                     f.features,
                     app_destination.id AS applicationDestinationId,
                     app_destination.name AS applicationDestinationName,
                     tas_destination.label AS statusDestination,
            
                     acc_destination.id AS accountDestinationId,
                     acc_destination.name AS accountDestinationName,
            
                     COALESCE(sst.label, 'Não Solicitado') AS shareStatusLabel,
                     COALESCE(sst.name, 'NOT_REQUESTED') AS shareStatusName,
            
                    COALESCE(
             		NULLIF(
                     CONCAT_WS(',',
                         IF(app_destination.id IS NULL, 'DESTINATION_NOT_FOUND', NULL),
                         IF(app_destination.deleted_at IS NOT NULL, 'DESTINATION_INACTIVE', NULL),
                         IF(acc_destination.id IS NULL, 'DESTINATION_ACCOUNT_NOT_FOUND', NULL),
                         IF(acc_destination.deleted_at IS NOT NULL, 'DESTINATION_ACCOUNT_INACTIVE', NULL),
                         IF(tas_destination.id IS NULL, 'DESTINATION_SCOPE_NOT_FOUND', NULL),
                         IF(tas_destination.name <> 'SHARED', 'DESTINATION_NOT_SHARED', NULL),
            
                         IF(so.id IS NOT NULL AND app_origin.id IS NULL, 'ORIGIN_NOT_FOUND', NULL),
                         IF(so.id IS NOT NULL AND app_origin.deleted_at IS NOT NULL, 'ORIGIN_INACTIVE', NULL),
                         IF(so.id IS NOT NULL AND acc_origin.id IS NULL, 'ORIGIN_ACCOUNT_NOT_FOUND', NULL),
                         IF(so.id IS NOT NULL AND acc_origin.deleted_at IS NOT NULL, 'ORIGIN_ACCOUNT_INACTIVE', NULL),
                         IF(so.id IS NOT NULL AND tas_origin.id IS NULL, 'ORIGIN_SCOPE_NOT_FOUND', NULL),
                         IF(so.id IS NOT NULL AND tas_origin.name <> 'BACKEND', 'ORIGIN_NOT_BACKEND', NULL)
                     ),
                     ''\s
                 ),
                 'VALID'
               ) AS validationStatus
               FROM sharing_targets st
               LEFT JOIN (
               SELECT
                     stf.sharing_targets_id,
                     GROUP_CONCAT(DISTINCT tf.label ORDER BY tf.label SEPARATOR ', ') AS features
                 FROM sharing_target_features stf
                 INNER JOIN type_features tf\s
                     ON tf.id = stf.type_features_id
                    AND tf.is_active = true
                 GROUP BY stf.sharing_targets_id
               ) f ON f.sharing_targets_id = st.id
               LEFT JOIN applications app_destination  ON app_destination.id = st.application_id 
               LEFT JOIN type_application_scopes tas_destination  ON tas_destination.id = app_destination.type_application_scopes_id
               LEFT JOIN accounts  acc_destination ON acc_destination.id = app_destination.account_id
               LEFT JOIN sharing_origins so ON so.sharing_targets_id = st.id AND so.application_id = :applicationOriginId
               LEFT JOIN applications app_origin  ON app_origin.id = so.application_id
               LEFT JOIN type_application_scopes tas_origin ON tas_origin.id = app_origin.type_application_scopes_id
               LEFT JOIN accounts acc_origin  ON acc_origin.id = app_origin.account_id
            
             LEFT JOIN type_sharing_statuses sst ON sst.id = so.type_sharing_statuses_id
            
             WHERE
                 app_destination.id <> :applicationOriginId
                 AND (:accountDestinationId IS NULL OR acc_destination.id = :accountDestinationId)
                 AND (:applicationDestinationId IS NULL OR app_destination.id = :applicationDestinationId)
                 AND  (:shareStatusName IS NULL    OR COALESCE(sst.name, 'NOT_REQUESTED') = :shareStatusName)
            
            """, nativeQuery = true)
    List<AvailableSharingProjection> findAvailableSharings(
            @Param("applicationDestinationId") Long applicationDestinationId,
            @Param("accountDestinationId") Long accountDestinationId,
            @Param("applicationOriginId") Long applicationOriginId,
            @Param("shareStatusName") String shareStatusName
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


    @Query(value = """
                SELECT
                    st.name AS targetName,
                    st.identifier AS targetIdentifier,
                    so.application_id AS applicationOriginId,
                    ac.id AS accountDestinationId, 
                    ac.name AS accountDestinationName,
                    ac.identifier AS accountDestinationIdentifier,
                    a.name AS applicationDestinationName,
                    a.id AS applicationDestinationId,
                    ae.environment_id AS environmentId,
                    CAST(CONCAT('[',\s
                        GROUP_CONCAT(
                            DISTINCT JSON_OBJECT(
                                'publisher', p.name,
                                'parameters', 
                                        CASE 
                                            WHEN JSON_VALID(pc.parameters)
                                            THEN CAST(pc.parameters AS JSON)
                                            ELSE null
                                        END
                            )
                        ),
                     ']') AS JSON) AS publishers
                FROM sharing_origins so
                INNER JOIN type_sharing_statuses tss ON tss.id = so.type_sharing_statuses_id  AND tss.name = :statusName
                INNER JOIN sharing_targets st ON st.id = so.sharing_targets_id
                INNER JOIN applications a ON a.id = st.application_id AND a.deleted_at IS NULL
                INNER JOIN accounts ac ON ac.id = a.account_id
                INNER JOIN type_application_scopes tas ON tas.id = a.type_application_scopes_id  AND tas.name = :scopeName
                INNER JOIN applications_environments ae ON a.id = ae.application_id  AND (:environmentId IS NULL OR ae.environment_id = :environmentId)
                INNER JOIN environments e ON ae.environment_id = e.id AND e.active = true
                INNER JOIN applications_environment_publishers aep ON ae.application_id = aep.application_id  AND ae.environment_id = aep.environment_id
                INNER JOIN publisher_configs pc ON pc.id = aep.publisher_id
                INNER JOIN publishers p ON p.id = pc.publisher_id
                WHERE
                    so.application_id = :applicationOriginId
                    AND EXISTS (
                        SELECT 1
                        FROM sharing_target_features stf
                        JOIN type_features tf ON tf.id = stf.type_features_id
                        JOIN type_feature_scopes tfs ON tfs.id = tf.type_feature_scopes_id
                        WHERE 
                            stf.sharing_targets_id = st.id
                            AND tf.is_active=true
                            AND (:typeFeature IS NULL OR tf.name = :typeFeature)
                            AND tfs.name = :featureScope
                    )
                GROUP BY 
                    st.id, st.name, st.identifier, so.application_id, ac.id, 
                    ac.name, ac.identifier, a.name, a.id, ae.environment_id
            """, nativeQuery = true)
    List<SharingDestinationsProjection> findSharingDestinations(
            @Param("applicationOriginId") Long applicationOriginId,
            @Param("environmentId") Long environmentId,
            @Param("typeFeature") String typeFeature,
            @Param("statusName") String statusName,       // Ex: 'APPROVED'
            @Param("scopeName") String scopeName,         // Ex: 'SHARED'
            @Param("featureScope") String featureScope     // Ex: 'BACKEND_APPLICATION'
    );
}
