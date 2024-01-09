package com.aliyun.bailian.chatgpt.security;

import com.aliyun.bailian.chatgpt.config.SecurityConfig;
import com.aliyun.bailian.chatgpt.utils.LogUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;

/**
 * @author yuanci
 */
@Configuration
public class CustomizedCorsConfig {
    @Resource
    private SecurityConfig securityConfig;

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();

        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOriginPatterns(securityConfig.getRefererWhitelist());
        config.setAllowedMethods(Arrays.asList("POST", "OPTIONS", "GET"));
        config.setMaxAge(86400L);
        config.setAllowedHeaders(Arrays.asList("Accept", "Content-Type", "Authorization", "x-xsrf-token", "Accept"));
        config.setAllowCredentials(true);

        source.registerCorsConfiguration("/**", config);

        return new CorsFilter(source) {
            @Override
            protected void doFilterInternal(@NotNull HttpServletRequest request, @NotNull HttpServletResponse response, @NotNull FilterChain filterChain) throws ServletException, IOException {
                long startTime = System.currentTimeMillis();
                super.doFilterInternal(request, response, filterChain);

                if (response.getStatus() == HttpServletResponse.SC_FORBIDDEN) {
                    String url = request.getRequestURL().toString();
                    String origin = request.getHeader("Origin");
                    String referer = request.getHeader("Referer");
                    String userAgent = request.getHeader("User-Agent");

                    LogUtils.monitor(null, "CorsFilter", "doFilterInternal", "error", startTime, url, origin, referer, userAgent);
                }
            }
        };
    }
}
