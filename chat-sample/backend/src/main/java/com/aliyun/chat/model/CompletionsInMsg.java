package com.aliyun.chat.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

/**
 * 查询完成情况的输入参数
 * @author yunchang
 */
@Data
@SuperBuilder
@NoArgsConstructor
public class CompletionsInMsg implements Serializable {
    private String sessionId;
    private String prompt;
}
