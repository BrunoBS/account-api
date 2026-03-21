package com.brunobs.features.sharing.origin;

import com.brunobs.core.application.Application;
import com.brunobs.core.catalog.type.sharestatus.ShareStatusType;
import com.brunobs.features.sharing.target.SharingTarget;
import jakarta.persistence.*;

@Entity
@Table(name = "sharing_origin")
public class SharingOrigin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "share_target_id", nullable = false)
    private SharingTarget sharingTarget;

    @Column(nullable = false)
    private String name;

    @Column(length = 500, nullable = false)
    private String reason;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "application_id", nullable = false)
    private Application application;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "share_status_type_id", nullable = false)
    private ShareStatusType shareStatusType;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public SharingTarget getSharingTarget() {
        return sharingTarget;
    }

    public void setSharingTarget(SharingTarget sharingTarget) {
        this.sharingTarget = sharingTarget;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Application getApplication() {
        return application;
    }

    public void setApplication(Application application) {
        this.application = application;
    }

    public ShareStatusType getShareStatusType() {
        return shareStatusType;
    }

    public void setShareStatusType(ShareStatusType shareStatusType) {
        this.shareStatusType = shareStatusType;
    }
}
