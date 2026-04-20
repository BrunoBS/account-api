package com.brunobs.feature.sharing.target;

import com.brunobs.core.application.Application;
import com.brunobs.core.catalog.common.EnumTypeDTO;
import com.brunobs.core.catalog.feature.type.FeatureType;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;


@Component
public class SharingTargetMapper {


    public SharingTarget toEntity(SharingTargetDTO dto,
                                  List<FeatureType> features,
                                  Application application) {
        if (dto == null) return null;

        SharingTarget entity = new SharingTarget();
        updateEntity(entity, dto, features, application);
        entity.setId(dto.id());
        entity.setIdentifier(UUID.randomUUID().toString());
        return entity;
    }

    public SharingTargetDTO toDTO(SharingTarget entity) {
        if (entity == null) return null;

        return new SharingTargetDTO(
                entity.getId(),
                entity.getIdentifier(),
                entity.getName(),
                entity.getDescription(),
                entity.getApplication().getAccount().getId(),
                entity.getApplication().getId(),

                entity.getFeatures().stream().map(m ->
                        new EnumTypeDTO(m.getLabel(), m.getName())).toList()
        );
    }

    public void updateEntity(SharingTarget entity,
                             SharingTargetDTO dto,
                             List<FeatureType> features,
                             Application application) {
        if (entity == null || dto == null) return;
        entity.setName(dto.name());
        entity.setDescription(dto.name());
        entity.setFeatures(features);
        entity.setApplication(application);
    }
}
