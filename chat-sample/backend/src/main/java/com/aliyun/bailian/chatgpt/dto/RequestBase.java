package com.aliyun.bailian.chatgpt.dto;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author yuanci
 */
@Data
@ToString
public class RequestBase implements Serializable {
    private static final long serialVersionUID = -3372275118743867054L;

    /**
     * request id
     */
    private String requestId;
}
