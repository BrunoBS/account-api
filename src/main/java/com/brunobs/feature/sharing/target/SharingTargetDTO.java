package com.brunobs.feature.sharing.target;

import com.brunobs.core.catalog.common.EnumTypeDTO;
import com.brunobs.shared.base.BaseDTO;

import java.util.List;

/**
 * Data Transfer Object for SharingTarget entity.
 * Implements BaseTypeDTO for standardized operations.
 */
public record SharingTargetDTO(
        Long id,
        String identifier,
        String name,
        String description,
        Long accountId,
        Long applicationId,
        List<EnumTypeDTO> features
) implements BaseDTO<String, Long> {


    public SharingTargetDTO withId(Long id, Long accountId, Long applicationId) {
        return new SharingTargetDTO(
                id,
                null,
                this.name,
                this.description,
                accountId,
                applicationId,
                this.features
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