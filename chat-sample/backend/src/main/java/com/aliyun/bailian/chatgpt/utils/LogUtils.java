package com.aliyun.bailian.chatgpt.utils;

import com.alibaba.fastjson.JSON;
import com.aliyun.bailian.chatgpt.config.AppConfig;
import com.aliyun.bailian.chatgpt.exceptions.BizException;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;

/**
 * log utils for app, monitor and trace
 *
 * @author yuanci
 */
public class LogUtils {

    private final static Logger logger = LoggerFactory.getLogger("application");

    private final static Logger monitorLogger = LoggerFactory.getLogger("monitor-log");

    private final static Logger traceLogger = LoggerFactory.getLogger("trace-log");

    public static final String SIMPLE_LOG_SPLIT = "@@@";

    public enum ResultStatus {
        /**
         * 调用成功
         */
        SUCCESS,

        /**
         * 调用失败
         */
        FAIL
    }

    public static void debug(Object... objects) {
        LogContent build = build(objects);
        logger.debug(build.logData);
    }

    public static void info(Object... objects) {
        LogContent build = build(objects);
        logger.info(build.logData);
    }

    public static void warn(Object... objects) {
        LogContent build = build(objects);
        logger.warn(build.logData);
    }

    public static void error(Object... objects) {
        LogContent build = build(objects);
        logger.error(build.logData, build.getThrowable());
    }

    public static void monitor(String requestId, String serviceName, String methodName,
                               String errorCode, Long startTime, Object... objects) {
        ResultStatus status = StringUtils.isNotBlank(errorCode) ? ResultStatus.FAIL : ResultStatus.SUCCESS;
        StringBuilder builder = new StringBuilder();
        builder.append(requestId);
        builder.append(SIMPLE_LOG_SPLIT).append(serviceName);
        builder.append(SIMPLE_LOG_SPLIT).append(methodName);
        builder.append(SIMPLE_LOG_SPLIT).append(status.name());
        builder.append(SIMPLE_LOG_SPLIT).append(errorCode);

        Long costTime = 0L;
        if (startTime != null) {
            costTime = System.currentTimeMillis() - startTime;
        }
        builder.append(SIMPLE_LOG_SPLIT).append(costTime);

        LogContent content = build(objects);
        builder.append(content.getLogData());
        Throwable throwable = content.getThrowable();

        if (throwable != null) {
            if (throwable instanceof BizException) {
                builder.append(SIMPLE_LOG_SPLIT).append(throwable);
            } else {
                builder.append(SIMPLE_LOG_SPLIT).append(throwable.getMessage());
            }
        }

        String logStr = builder.toString();
        monitorLogger.error(logStr);
        if (throwable != null) {
            logger.error(logStr, throwable);
        }
    }

    public static void trace(String requestId, String action, String resultCode, Long startTime,
                             Object input, Object output, Object... objects) {
        if (!AppConfig.openTrace) {
            return;
        }

        StringBuilder tracer = new StringBuilder();
        tracer.append("Trace").append(SIMPLE_LOG_SPLIT)
                .append(requestId).append(SIMPLE_LOG_SPLIT);

        tracer.append(action).append(SIMPLE_LOG_SPLIT);
        tracer.append(resultCode).append(SIMPLE_LOG_SPLIT);

        Long costTime = 0L;
        if (startTime != null) {
            costTime = System.currentTimeMillis() - startTime;
        }

        tracer.append(costTime).append(SIMPLE_LOG_SPLIT);
        tracer.append(JSON.toJSONString(input)).append(SIMPLE_LOG_SPLIT);
        tracer.append(JSON.toJSONString(output)).append(SIMPLE_LOG_SPLIT);

        LogContent content = build(objects);
        tracer.append(content.getLogData());

        traceLogger.info(tracer.toString());
    }

    private static LogContent build(Object... objects) {
        LogContent logContent = new LogContent();
        if (null == objects || objects.length == 0) {
            return logContent;
        }

        StringBuilder builder = new StringBuilder();

        Throwable throwable = null;
        for (Object obj : objects) {
            if (obj instanceof Throwable) {
                throwable = (Throwable) obj;
                continue;
            }

            String value;
            if (obj instanceof String) {
                value = String.valueOf(obj);
            } else {
                value = JSON.toJSONString(obj);
            }

            builder.append(SIMPLE_LOG_SPLIT).append(value);
        }

        logContent.setLogData(builder.toString());
        logContent.setThrowable(throwable);

        return logContent;
    }

    @Data
    static class LogContent {

        String logData;

        Throwable throwable;

    }
}
