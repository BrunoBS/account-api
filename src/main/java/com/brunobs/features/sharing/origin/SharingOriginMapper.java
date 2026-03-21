package com.brunobs.features.sharing.origin;

import com.brunobs.core.application.Application;
import com.brunobs.core.catalog.type.sharestatus.ShareStatusType;
import com.brunobs.features.sharing.target.SharingTarget;
import org.springframework.stereotype.Component;


@Component
public class SharingOriginMapper {


    public SharingOrigin toEntity(SharingOriginDTO dto,
                                  SharingTarget sharingTarget,
                                  ShareStatusType shareStatusType,
                                  Application application) {
        if (dto == null) return null;

        SharingOrigin entity = new SharingOrigin();
        updateEntity(entity, dto, sharingTarget, shareStatusType, application);
        entity.setId(dto.id());
        return entity;
    }

    public SharingOriginDTO toDTO(SharingOrigin entity) {
        if (entity == null) return null;

        return new SharingOriginDTO(
                entity.getId(),
                entity.getName(),
                entity.getSharingTarget().getId(),
                entity.getApplication().getId(),
                entity.getReason(),
                entity.getShareStatusType().getName()
        );
    }

    public void updateEntity(SharingOrigin entity,
                             SharingOriginDTO dto,
                             SharingTarget sharingTarget,
                             ShareStatusType shareStatusType,
                             Application application) {
        if (entity == null || dto == null) return;

        entity.setName(dto.name());
        entity.setApplication(application);
        entity.setShareStatusType(shareStatusType);
        entity.setReason(dto.reason());
        entity.setSharingTarget(sharingTarget);
    }
}
