package com.aliyun.bailian.chatgpt.model;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * 会话记录
 *
 * @author yuanci
 */
@Data
@ToString
public class ChatMessage implements Serializable {
    private static final long serialVersionUID = -3470843417163463759L;

    private String user;

    private String assistant;
}
