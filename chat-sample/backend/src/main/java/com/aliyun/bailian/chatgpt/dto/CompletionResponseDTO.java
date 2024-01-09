package com.aliyun.bailian.chatgpt.dto;

import lombok.Data;
import lombok.ToString;

/**
 * completion response dto for api
 *
 * @author yuanci
 */
@Data
@ToString
public class CompletionResponseDTO {
    /**
     * session id for chat history
     */
    private String sessionId;

    /**
     * content type, default is text
     */
    private String contentType;

    /**
     * completion result
     */
    private String content;
}
