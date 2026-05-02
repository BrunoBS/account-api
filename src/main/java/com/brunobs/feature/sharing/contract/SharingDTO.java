package com.brunobs.feature.sharing.contract;

import com.brunobs.core.catalog.common.EnumTypeDTO;
import com.brunobs.shared.base.BaseDTO;

import java.util.List;

/**
 * Data Transfer Object for SharingTarget entity.
 * Implements BaseTypeDTO for standardized operations.
 */
public record SharingDTO(
        Long id,
        String identifier,
        String name,
        String description,
        Long accountId,
        Long applicationId,
        List<EnumTypeDTO> features,
        String hashFeatures
) implements BaseDTO<String, Long> {


    public SharingDTO withId(Long id, Long accountId, Long applicationId) {
        return new SharingDTO(
                id,
                null,
                this.name,
                this.description,
                accountId,
                applicationId,
                this.features,
                hashFeatures
        );
    }

    public SharingDTO withHashFeature(String hashFeatures) {
        return new SharingDTO(
                this.id,
                this.identifier,
                this.name,
                this.description,
                this.accountId,
                this.applicationId,
                this.features,
                hashFeatures
        );
    }

    @Override
    public Long registrationIdentifier() {
        return id;
    }

    @Override
    public String registrationName() {
        return name;
    }
}