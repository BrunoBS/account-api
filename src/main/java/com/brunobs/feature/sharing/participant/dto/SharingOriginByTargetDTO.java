package com.brunobs.feature.sharing.participant.dto;

import com.brunobs.shared.base.StatusDTO;

public record SharingOriginByTargetDTO(
    Long id,
    Long applicationId,
    String applicationName,
    Long accountId,
    String accountName,
    StatusDTO status
) {}