package com.brunobs.cache.factory;

import com.github.benmanes.caffeine.cache.Cache;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class CacheFactory {

    private final Map<CacheType, Cache<?, ?>> caches;

    public CacheFactory(Map<CacheType, Cache<?, ?>> caches) {
        this.caches = caches;
    }

    @SuppressWarnings("unchecked")
    public <K, V> Cache<K, V> get(CacheType type) {
        return (Cache<K, V>) caches.get(type);
    }
}