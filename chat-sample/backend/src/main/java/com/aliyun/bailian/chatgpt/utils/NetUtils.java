package com.aliyun.bailian.chatgpt.utils;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

/**
 * common utils
 *
 * @author yuanci
 */
public class NetUtils {
    public static String getRealIp(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if (ip != null && !"".equals(ip)) {
            String[] ips = ip.split(",");
            ip = ips[0];
        }
        if (ip == null) {
            ip = request.getHeader("X-Real-IP");
        }
        if (ip == null) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

}
