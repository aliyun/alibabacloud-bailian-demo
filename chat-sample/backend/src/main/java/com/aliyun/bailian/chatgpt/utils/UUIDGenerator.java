package com.aliyun.bailian.chatgpt.utils;

import java.util.UUID;

/**
 * UUID generator
 *
 * @author yuanci
 */
public class UUIDGenerator {
    public static String generate() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString().replaceAll("-", "");
    }
}
