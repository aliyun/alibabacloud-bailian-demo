package com.aliyun.chat.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

/**
 * 应用对话完成情况的输出参数
 * @author yunchang
 */
@Data
@SuperBuilder
@NoArgsConstructor
public class CompletionsOutMsg implements Serializable {
    //AI回答内容
    private String text;
    //流式输出正在生成时为null，生成结束时为stop
    private String finishReason;
    //会话ID
    private String sessionId;
}
