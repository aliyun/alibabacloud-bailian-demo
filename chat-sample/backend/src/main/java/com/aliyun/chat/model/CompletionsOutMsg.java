package com.aliyun.chat.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

/**
 * 查询完成情况的输出参数
 * @author yunchang
 */
@Data
@SuperBuilder
@NoArgsConstructor
public class CompletionsOutMsg implements Serializable {
    private String text;
    private String finishReason;
    private String sessionId;
}
