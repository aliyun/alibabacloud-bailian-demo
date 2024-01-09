package com.aliyun.bailian.chatgpt.cache.impl;


import com.aliyun.bailian.chatgpt.cache.CacheManager;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;

import java.util.concurrent.TimeUnit;

/**
 * @author yuanci
 */
public class RedisCacheManager implements CacheManager {
    private final RedissonClient redissonClient;

    public RedisCacheManager(RedissonClient redissonClient) {
        this.redissonClient = redissonClient;
    }

    @Override
    public <V> void put(String key, V value) {
        redissonClient.getBucket(key).set(value);
    }

    @Override
    public <V> void put(String key, V value, long timeToLive, TimeUnit timeUnit) {
        RBucket<V> bucket = redissonClient.getBucket(key);
        bucket.set(value, timeToLive, timeUnit);
    }

    @Override
    public <V> V get(String key) {
        RBucket<V> bucket = redissonClient.getBucket(key);
        return bucket.get();
    }

    @Override
    public boolean delete(String key) {
        return redissonClient.getBucket(key).delete();
    }
}
