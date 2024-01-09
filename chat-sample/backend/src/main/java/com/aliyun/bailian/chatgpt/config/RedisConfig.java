package com.aliyun.bailian.chatgpt.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author yuanci
 */
@Configuration
@ConfigurationProperties(prefix = "redis")
@Data
public class RedisConfig {
    private String host;

    private Integer port;

    private String password;
}
