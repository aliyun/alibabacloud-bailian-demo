package com.aliyun.bailian.chatgpt.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * @author yuanci
 */
@Configuration
@ConfigurationProperties(prefix = "chat.security")
@Data
public class SecurityConfig {
    private List<String> refererWhitelist;
}
