package com.aliyun.bailian.chatgpt.enums;

/**
 * @author yuanci
 */

public enum ErrorCodeEnum {
    /**
     * System error
     */
    SYSTEM_ERROR("10000", "System error"),

    PARAMS_INVALID("10001", "Request param is missing"),

    REQUEST_LLM_TIMEOUT("10100", "Request LLM timout"),

    GUEST_NOT_EXIST("10201", "Guest user does not exist"),

    CREATE_COMPLETION_ERROR("10301", "create completion error"),
    ;

    /**
     * error code
     */
    private final String errorCode;

    /**
     * error message
     */
    private final String errorMessage;

    ErrorCodeEnum(String errorCode, String errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }


    public String getErrorCode() {
        return this.errorCode;
    }

    public String getErrorMessage() {
        return this.errorMessage;
    }

    public String getErrorMessage(Object... args) {
        return String.format(this.errorMessage, args);
    }

    public static ErrorCodeEnum getErrorCodeEnum(String errorCode) {
        for (ErrorCodeEnum codeConstants : values()) {
            if (codeConstants.errorCode.equals(errorCode)) {
                return codeConstants;
            }
        }

        return SYSTEM_ERROR;
    }
}
