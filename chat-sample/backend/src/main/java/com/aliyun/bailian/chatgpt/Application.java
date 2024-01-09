package com.aliyun.bailian.chatgpt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

/**
 * Application entry
 *
 * @author yuanci
 */
@SpringBootApplication(scanBasePackages = "com.aliyun.bailian.chatgpt")
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args).registerShutdownHook();
    }
}
