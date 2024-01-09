package com.aliyun.bailian.chatgpt.service.impl;

import com.aliyun.bailian.chatgpt.cache.CacheManager;
import com.aliyun.bailian.chatgpt.config.AppConfig;
import com.aliyun.bailian.chatgpt.model.ChatMessage;
import com.aliyun.bailian.chatgpt.service.ChatSessionService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.concurrent.TimeUnit;

/**
 * @author yuanci
 */
@Service
public class ChatSessionServiceImpl implements ChatSessionService {
    private static final String CACHE_PREFIX = "chat_session:";

    @Resource
    private AppConfig appConfig;

    @Resource
    private CacheManager cacheManager;

    @Override
    public Deque<ChatMessage> getChatSessions(String sessionId) {
        String key = getCacheKey(sessionId);
        return cacheManager.get(key);
    }

    @Override
    public void saveChatSessions(String sessionId, Deque<ChatMessage> chatMessages) {
        String key = getCacheKey(sessionId);
        cacheManager.put(key, chatMessages, appConfig.getSessionTtl(), TimeUnit.HOURS);
    }

    @Override
    public void saveChatMessage(String sessionId, ChatMessage message) {
        Deque<ChatMessage> chatMessages = getChatSessions(sessionId);
        if (chatMessages == null) {
            chatMessages = new ArrayDeque<>();
        } else if (chatMessages.size() > appConfig.getSessionMaxMessages()) {
            chatMessages.poll();
        }

        chatMessages.offer(message);
        saveChatSessions(sessionId, chatMessages);
    }

    private String getCacheKey(String key) {
        return CACHE_PREFIX + key;
    }
}
