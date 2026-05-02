package com.brunobs.feature.sharing.contract;

import com.brunobs.core.application.Application;
import com.brunobs.core.catalog.common.EnumTypeDTO;
import com.brunobs.core.catalog.feature.type.FeatureType;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@Component
public class SharingMapper {


    public Sharing toEntity(SharingDTO dto,
                            List<FeatureType> features,
                            Application application) {
        if (dto == null) return null;

        Sharing entity = new Sharing();
        updateEntity(entity, dto, features, application);
        entity.setId(dto.id());
        entity.setIdentifier(UUID.randomUUID().toString());
        return entity;
    }

    public SharingDTO toDTO(Sharing entity) {
        if (entity == null) return null;

        return new SharingDTO(
                entity.getId(),
                entity.getIdentifier(),
                entity.getName(),
                entity.getDescription(),
                entity.getApplication().getAccount().getId(),
                entity.getApplication().getId(),
                entity.getFeatures().stream().map(m -> new EnumTypeDTO(m.getLabel(), m.getName())).toList(),
                entity.getHashFeatures()
        );
    }

    public void updateEntity(Sharing entity,
                             SharingDTO dto,
                             List<FeatureType> features,
                             Application application) {
        if (entity == null || dto == null) return;
        entity.setName(dto.name());
        entity.setDescription(dto.name());
        entity.setFeatures(new ArrayList<>(features));
        entity.setApplication(application);
        entity.setHashFeatures(dto.hashFeatures());
    }
}
