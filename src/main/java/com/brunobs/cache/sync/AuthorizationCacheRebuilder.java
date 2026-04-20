package com.brunobs.cache.sync;

import com.brunobs.auth.messaging.dto.AccountCacheDTO;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class AuthorizationCacheRebuilder {

    private final AuthorizationCacheRefreshService service;

    public AuthorizationCacheRebuilder(AuthorizationCacheRefreshService service) {
        this.service = service;
    }

    @KafkaListener(
            topics = "auth-refresh-request",
            groupId = "${spring.application.name}-auth-group",
            properties = {
                    "spring.json.value.default.type=com.brunobs.auth.messaging.dto.AccountCacheDTO"
            }
    )
    public void handleRefresh(AccountCacheDTO request) {
        service.refresh(request);
    }
}