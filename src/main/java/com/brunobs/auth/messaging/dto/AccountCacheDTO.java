package com.brunobs.auth.messaging.dto;

import java.util.List;

public record AccountCacheDTO(
        Long accountId,
        String authorizationTypeDefault,
        List<GroupDetailDTO> groups
) {}