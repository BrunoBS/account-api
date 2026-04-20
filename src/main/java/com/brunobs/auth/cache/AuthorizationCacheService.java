package com.brunobs.auth.cache;

import com.brunobs.auth.messaging.dto.AccountCacheDTO;
import com.brunobs.auth.messaging.dto.KafkaPublishRequest;
import com.brunobs.auth.messaging.kafka.KafkaPublisherService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.benmanes.caffeine.cache.Cache;
import org.springframework.stereotype.Service;

@Service
public class AuthorizationCacheService {

    private static final String PENDING = "__PENDING__";

    private final Cache<String, String> cache;
    private final Cache<String, Boolean> refreshGuard;

    private final KafkaPublisherService publisher;
    private final ObjectMapper mapper;

    public AuthorizationCacheService(
            Cache<String, String> authorizationCache,
            Cache<String, Boolean> authorizationRefreshGuardCache,
            KafkaPublisherService publisher,
            ObjectMapper mapper) {

        this.cache = authorizationCache;
        this.refreshGuard = authorizationRefreshGuardCache;
        this.publisher = publisher;
        this.mapper = mapper;
    }

    public String getAuthorizationGroup(Long accountId,
                                        String defaultType,
                                        String cacheKey) {

        String value = cache.getIfPresent(cacheKey);


        if (value == null || PENDING.equals(value)) {
            triggerRefresh(accountId, defaultType, cacheKey);
            value = waitForCache(cacheKey);

            return (value != null && !PENDING.equals(value)) ? value : null;
        }

        return value;
    }

    private void triggerRefresh(Long accountId,
                                String defaultType,
                                String cacheKey) {

        if (refreshGuard.getIfPresent(cacheKey) != null) {
            return;
        }

        refreshGuard.put(cacheKey, true);
        cache.put(cacheKey, PENDING);

        try {
            String json = mapper.writeValueAsString(
                    new AccountCacheDTO(accountId, defaultType, null)
            );

            publisher.publish(
                    new KafkaPublishRequest(
                            "PROD",
                            "auth-refresh-request",
                            accountId,
                            json
                    )
            );

        } catch (Exception e) {
            throw new IllegalStateException("Failed to trigger refresh", e);
        }
    }

    private String waitForCache(String cacheKey) {

        int attempts = 3;
        int delayMs = 150;

        for (int i = 0; i < attempts; i++) {
            sleep(delayMs);
            String value = cache.getIfPresent(cacheKey);
            if (value != null && !PENDING.equals(value)) {
                return value;
            }
        }

        return null;
    }

    private void sleep(int ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public void updateLocalCache(AccountCacheDTO dto) {

        if (dto.groups() == null) return;
        dto.groups().forEach(g -> {
            cache.put(g.key(), g.group());
            refreshGuard.invalidate(g.key());
        });
    }
}