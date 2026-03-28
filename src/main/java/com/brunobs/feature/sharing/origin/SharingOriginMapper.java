package com.brunobs.feature.sharing.origin;

import com.brunobs.core.application.Application;
import com.brunobs.core.catalog.type.sharestatus.ShareStatusType;
import com.brunobs.feature.sharing.target.SharingTarget;
import org.springframework.stereotype.Component;


@Component
public class SharingOriginMapper {


    public SharingOrigin toEntity(
                                  SharingTarget sharingTarget,
                                  ShareStatusType shareStatusType,
                                  Application application) {


        SharingOrigin entity = new SharingOrigin();
        updateEntity(entity, sharingTarget, shareStatusType, application);
        return entity;
    }

    public SharingOriginDTO toDTO(SharingOrigin entity) {
        if (entity == null) return null;

        return new SharingOriginDTO(
                entity.getId(),
                entity.getSharingTarget().getId(),
                entity.getApplication().getId(),
                entity.getShareStatusType().getName()
        );
    }

    public void updateEntity(SharingOrigin entity,
                             SharingTarget sharingTarget,
                             ShareStatusType shareStatusType,
                             Application application) {

        entity.setApplication(application);
        entity.setShareStatusType(shareStatusType);
        entity.setSharingTarget(sharingTarget);
    }
}
