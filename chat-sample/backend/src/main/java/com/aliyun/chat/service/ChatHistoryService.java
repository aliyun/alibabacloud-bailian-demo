package com.aliyun.chat.service;

import com.aliyun.chat.model.ChatHistory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 模型对话的聊天记录服务
 * @author yunchang
 */
@Service
public class ChatHistoryService {
    @Autowired
    private RedisTemplate<String, ChatHistory> redisTemplate;

    public void save(String sessionId, ChatHistory chatHistory) {
        redisTemplate.opsForList().rightPush(sessionId, chatHistory);
        //设置缓存过期时间为24小时
        redisTemplate.expire(sessionId, 24, TimeUnit.HOURS);
    }

    public List<ChatHistory> load(String sessionId) {
        return redisTemplate.opsForList().range(sessionId, 0, -1);
    }
}
