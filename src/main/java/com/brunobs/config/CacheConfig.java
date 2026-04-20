package com.brunobs.config;

import com.brunobs.auth.context.UserSession;
import com.brunobs.cache.factory.CacheType;
import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.Expiry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Configuration
public class CacheConfig {

    // =========================
    // JWT CACHE
    // =========================
    @Bean
    public Cache<String, UserSession> jwtCache() {
        return Caffeine.newBuilder()
                .recordStats()
                .expireAfter(new Expiry<String, UserSession>() {
                    @Override
                    public long expireAfterCreate(String key, UserSession value, long currentTime) {
                        long durationMillis = value.getExpirationTime() - System.currentTimeMillis();
                        return TimeUnit.MILLISECONDS.toNanos(Math.max(0, durationMillis));
                    }

                    @Override
                    public long expireAfterUpdate(String key, UserSession value, long currentTime, long currentDuration) {
                        return currentDuration;
                    }

                    @Override
                    public long expireAfterRead(String key, UserSession value, long currentTime, long currentDuration) {
                        return currentDuration;
                    }
                })
                .maximumSize(10_000)
                .build();
    }

    // =========================
    // AUTHORIZATION CACHE (VALUES)
    // =========================
    @Bean
    public Cache<String, String> authorizationCache() {
        return Caffeine.newBuilder()
                .maximumSize(100_000)
                .expireAfterWrite(Duration.ofMinutes(5))
                .recordStats()
                .build();
    }

    // =========================
    // REFRESH GUARD CACHE
    // =========================
    @Bean
    public Cache<String, Boolean> authorizationRefreshGuardCache() {
        return Caffeine.newBuilder()
                .maximumSize(100_000)
                .expireAfterWrite(Duration.ofMinutes(1))
                .recordStats()
                .build();
    }

    @Bean
    public Map<CacheType, Cache<?, ?>> caches(
            Cache<String, UserSession> jwtCache,
            Cache<String, String> authorizationCache,
            Cache<String, String> authorizationRefreshGuardCache
    ) {
        return Map.of(
                CacheType.JWT, jwtCache,
                CacheType.AUTHZ, authorizationCache,
                CacheType.REFRESH_AUTHZ, authorizationRefreshGuardCache
        );
    }
}