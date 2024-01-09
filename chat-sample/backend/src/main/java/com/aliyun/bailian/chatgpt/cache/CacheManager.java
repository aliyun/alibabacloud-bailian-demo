package com.aliyun.bailian.chatgpt.cache;

import java.util.concurrent.TimeUnit;

/**
 * @author yuanci
 */
public interface CacheManager {
    /**
     * put cache
     *
     * @param key   cache key
     * @param value value
     * @param <V>   value type
     */
    public <V> void put(String key, V value);

    /**
     * put cache with expired time
     *
     * @param key        key
     * @param value      value
     * @param timeToLive timeToLive
     * @param timeUnit   timeUnit
     * @param <V>        value type
     */
    public <V> void put(String key, V value, long timeToLive, TimeUnit timeUnit);

    /**
     * get cache
     *
     * @param key cache key
     * @param <V> result type
     * @return result
     */
    public <V> V get(String key);

    /**
     * delete cache
     *
     * @param key cache key
     * @return success or not
     */
    public boolean delete(String key);
}
