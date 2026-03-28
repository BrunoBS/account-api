package com.brunobs.feature.sharing.origin;

public record SharingOriginRequestDTO(
        Long idSharingTarget,
        Long accountTargetId,
        Long applicationTargetId
) {


}