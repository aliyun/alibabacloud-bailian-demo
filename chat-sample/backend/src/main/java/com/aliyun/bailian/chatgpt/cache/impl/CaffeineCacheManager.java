package com.aliyun.bailian.chatgpt.cache.impl;

import com.aliyun.bailian.chatgpt.cache.CacheManager;
import com.github.benmanes.caffeine.cache.Cache;

import java.util.concurrent.TimeUnit;

/**
 * @author yuanci
 */
public class CaffeineCacheManager implements CacheManager {
    private final Cache<Object, Object> cache;

    public CaffeineCacheManager(Cache<Object, Object> cache) {
        this.cache = cache;
    }

    @Override
    public <V> void put(String key, V value) {
        cache.put(key, value);
    }

    @Override
    public <V> void put(String key, V value, long timeToLive, TimeUnit timeUnit) {
        cache.put(key, value);
    }

    @SuppressWarnings("unchecked")
    @Override
    public <V> V get(String key) {
        return (V) cache.getIfPresent(key);
    }

    @Override
    public boolean delete(String key) {
        cache.invalidate(key);
        return true;
    }
}
