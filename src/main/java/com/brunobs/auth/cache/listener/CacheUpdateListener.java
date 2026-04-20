package com.brunobs.auth.messaging.kafka;

import com.brunobs.auth.cache.AuthorizationCacheService;
import com.brunobs.auth.messaging.dto.AccountCacheDTO;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class CacheUpdateListener {

    private final AuthorizationCacheService cacheService;

    public CacheUpdateListener(AuthorizationCacheService cacheService) {
        this.cacheService = cacheService;
    }

    @KafkaListener(
            topics = "account-permissions-cache",
            groupId = "${spring.application.name}-auth-group",
            properties = {
                    "spring.json.value.default.type=com.brunobs.auth.messaging.dto.AccountCacheDTO"
            })
    public void onCacheUpdate(AccountCacheDTO dto) {
        cacheService.updateLocalCache(dto);
    }
}