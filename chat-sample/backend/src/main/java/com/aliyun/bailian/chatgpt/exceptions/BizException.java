package com.aliyun.bailian.chatgpt.exceptions;


import com.aliyun.bailian.chatgpt.enums.ErrorCodeEnum;

/**
 * @author yuanci
 */
public class BizException extends RuntimeException {
    private String errorCode;

    public BizException(String errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    public BizException(ErrorCodeEnum errorCode) {
        super(errorCode.getErrorMessage());
        this.errorCode = errorCode.getErrorCode();
    }

    public BizException(String errorCode, String message, Throwable cause) {
        super(message, cause);
        this.errorCode = errorCode;
    }

    public BizException(ErrorCodeEnum errorCode, Throwable cause) {
        super(errorCode.getErrorMessage(), cause);
        this.errorCode = errorCode.getErrorCode();
    }

    public BizException(String errorCode, String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.errorCode = errorCode;
    }

    public BizException(ErrorCodeEnum errorCode, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(errorCode.getErrorMessage(), cause, enableSuppression, writableStackTrace);
        this.errorCode = errorCode.getErrorCode();
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    @Override
    public String toString() {
        return "BizException{" + "errorCode=" + errorCode + ", " +
                "message=" + getMessage() +
                '}';
    }
}
