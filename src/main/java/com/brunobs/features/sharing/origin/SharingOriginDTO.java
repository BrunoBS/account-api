package com.brunobs.features.sharing.origin;

import com.brunobs.shared.BaseDTO;

public record SharingOriginDTO(
        Long id,
        String name,
        Long sharingTargetId,
        Long applicationId,
        String reason,
        String shareStatus
) implements BaseDTO<String, Long> {
    @Override
    public Long registrationIdentifier() {
        return id;
    }

    @Override
    public String registrationName() {
        return name;
    }

    public SharingOriginDTO withId(Long id) {
        return new SharingOriginDTO(
                id,
                this.name,
                this.sharingTargetId,
                this.applicationId,
                this.reason,
                this.shareStatus
        );
    }


}