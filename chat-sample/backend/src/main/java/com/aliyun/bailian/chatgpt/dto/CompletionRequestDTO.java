package com.aliyun.bailian.chatgpt.dto;

import com.aliyun.bailian.chatgpt.model.ChatMessage;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Deque;

/**
 * completion request dto for API
 *
 * @author yuanci
 */

@EqualsAndHashCode(callSuper = true)
@Data
public class CompletionRequestDTO extends RequestBase {
    /**
     * session id
     */
    private String sessionId;

    /**
     * user action
     */
    private String userAction;

    /**
     * enum：ChatTypeEnum
     * text_chat: text chat
     * doc_chat: doc chat
     */
    private String sessionType;

    /**
     * content of prompt
     */
    private String content;

    /**
     * data id of doc
     */
    private String dataId;

    /**
     * userId of doc
     */
    private String userId;

    private Deque<ChatMessage> chatMessages;
}
