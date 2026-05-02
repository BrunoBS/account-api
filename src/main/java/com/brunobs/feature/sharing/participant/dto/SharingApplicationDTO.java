package com.brunobs.feature.sharing.participant.dto;

import com.brunobs.shared.base.StatusDTO;

public record SharingApplicationDTO(
        Long id,
        Long sharingId,
        Long accountSharingId,
        Long applicationSharingId,
        StatusDTO shareStatus
) {

    public SharingApplicationDTO withId(Long id) {
        return new SharingApplicationDTO(
                id,
                this.sharingId,
                this.accountSharingId,
                this.applicationSharingId,
                this.shareStatus
        );
    }


}