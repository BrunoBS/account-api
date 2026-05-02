package com.brunobs.feature.sharing.participant;

import com.brunobs.core.application.Application;
import com.brunobs.core.catalog.type.sharestatus.ShareStatusType;
import com.brunobs.feature.sharing.contract.Sharing;
import jakarta.persistence.*;

@Entity

@Table(
        name = "sharing_origins",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_sharing_origin_application",
                        columnNames = {"sharingTarget", "applicationOrigen"}
                )
        }
)
public class SharingParticipant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sharing_targets_id", nullable = false)
    private Sharing sharing;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "application_id", nullable = false)
    private Application applicationOrigen;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "type_sharing_statuses_id", nullable = false)
    private ShareStatusType shareStatusType;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Sharing getSharingTarget() {
        return sharing;
    }

    public void setSharingTarget(Sharing sharing) {
        this.sharing = sharing;
    }


    public Application getApplication() {
        return applicationOrigen;
    }

    public void setApplication(Application application) {
        this.applicationOrigen = application;
    }

    public ShareStatusType getShareStatusType() {
        return shareStatusType;
    }

    public void setShareStatusType(ShareStatusType shareStatusType) {
        this.shareStatusType = shareStatusType;
    }
}
