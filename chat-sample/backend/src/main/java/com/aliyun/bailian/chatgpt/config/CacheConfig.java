package com.aliyun.bailian.chatgpt.config;

import com.aliyun.bailian.chatgpt.cache.CacheManager;
import com.aliyun.bailian.chatgpt.cache.impl.CaffeineCacheManager;
import com.aliyun.bailian.chatgpt.cache.impl.RedisCacheManager;
import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.apache.commons.lang3.StringUtils;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.codec.JsonJacksonCodec;
import org.redisson.config.Config;
import org.redisson.config.SingleServerConfig;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * 缓存配置, 同时支持redis和caffeine, 根据配置文件配置采用不同的缓存方式
 *
 * @author yuanci
 */
@Configuration
public class CacheConfig {
    @Resource
    private RedisConfig redisConfig;

    @Resource
    private AppConfig appConfig;

    @Bean
    @ConditionalOnProperty(name = "chat.cache.type", havingValue = "redis")
    public CacheManager redisCacheManager() {
        Config config = new Config();
        config.setCodec(new JsonJacksonCodec());
        SingleServerConfig singleServerConfig = config.useSingleServer();
        int port = redisConfig.getPort() != null ? redisConfig.getPort() : 6379;
        singleServerConfig.setAddress("redis://" + redisConfig.getHost() + ":" + port);
        singleServerConfig.setConnectionPoolSize(500);
        singleServerConfig.setSubscriptionConnectionPoolSize(300);
        singleServerConfig.setSubscriptionsPerConnection(500);

        if (StringUtils.isNotBlank(redisConfig.getPassword())) {
            singleServerConfig.setPassword(redisConfig.getPassword());
        }
        RedissonClient redissonClient = Redisson.create(config);

        return new RedisCacheManager(redissonClient);
    }

    @Bean
    @ConditionalOnProperty(name = "chat.cache.type", havingValue = "caffeine")
    public CacheManager caffeineCacheManager() {
        Cache<Object, Object> cache = Caffeine.newBuilder()
                .expireAfterAccess(appConfig.getSessionTtl(), TimeUnit.HOURS)
                .maximumSize(100000)
                .build();
        return new CaffeineCacheManager(cache);
    }
}
