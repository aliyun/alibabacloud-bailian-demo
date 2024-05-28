package com.aliyun.chat.config;

import com.aliyun.chat.model.ChatHistory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;

/**
 * Redis配置
 * @author yunchang
 */
@Configuration
public class RedisConfig {
    @Bean
    public RedisTemplate<String, ChatHistory> redisTemplate(RedisConnectionFactory connectionFactory) {
        RedisTemplate<String, ChatHistory> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);

        // 使用Jackson2JsonRedisSerializer来序列化和反序列化redis的value值（默认使用JDK的序列化方式）
        Jackson2JsonRedisSerializer<ChatHistory> serializer = new Jackson2JsonRedisSerializer<>(ChatHistory.class);

        template.setValueSerializer(serializer);
        template.setKeySerializer(template.getStringSerializer());

        return template;
    }
}
