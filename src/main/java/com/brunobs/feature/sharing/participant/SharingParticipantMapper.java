package com.brunobs.feature.sharing.participant;

import com.brunobs.core.application.Application;
import com.brunobs.core.catalog.type.sharestatus.ShareStatusType;
import com.brunobs.feature.sharing.contract.Sharing;
import com.brunobs.feature.sharing.participant.dto.SharingApplicationDTO;
import com.brunobs.shared.base.StatusDTO;
import org.springframework.stereotype.Component;


@Component
public class SharingParticipantMapper {


    public SharingParticipant toEntity(
            Sharing sharing,
            ShareStatusType shareStatusType,
            Application application) {


        SharingParticipant entity = new SharingParticipant();
        updateEntity(entity, sharing, shareStatusType, application);
        return entity;
    }

    public SharingApplicationDTO toDTO(SharingParticipant entity) {
        if (entity == null) return null;

        return new SharingApplicationDTO(
                entity.getId(),
                entity.getSharingTarget().getId(),
                entity.getSharingTarget().getApplication().getAccount().getId(),
                entity.getSharingTarget().getApplication().getId(),
                new StatusDTO(
                        entity.getShareStatusType().getName(),
                        entity.getShareStatusType().getLabel()
                )
        );
    }

    public void updateEntity(SharingParticipant entity,
                             Sharing sharing,
                             ShareStatusType shareStatusType,
                             Application application) {

        entity.setApplication(application);
        entity.setShareStatusType(shareStatusType);
        entity.setSharingTarget(sharing);
    }
}
