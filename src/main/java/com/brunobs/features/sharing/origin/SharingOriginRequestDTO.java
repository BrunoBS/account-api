package com.brunobs.features.sharing.origin;

public record SharingOriginRequestDTO(
        Long idSharingTarget,
        Long accountTargetId,
        Long applicationTargetId
) {


}