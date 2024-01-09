package com.aliyun.bailian.chatgpt.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author yuanci
 */

@Configuration
@ConfigurationProperties(prefix = "chat.app")
@Data
public class AppConfig {
    public static boolean openTrace;

    private Integer sessionMaxMessages;

    private Integer sessionTtl;

    public void setOpenTrace(boolean openTrace) {
        AppConfig.openTrace = openTrace;
    }
}
