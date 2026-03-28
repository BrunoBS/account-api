package com.brunobs.feature.sharing.origin;

public record SharingOriginDTO(
        Long id,
        Long sharingTargetId,
        Long applicationId,
        String shareStatus
) {

    public SharingOriginDTO withId(Long id) {
        return new SharingOriginDTO(
                id,

                this.sharingTargetId,
                this.applicationId,
                this.shareStatus
        );
    }


}