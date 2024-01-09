package com.aliyun.bailian.chatgpt.dto;

import com.aliyun.bailian.chatgpt.enums.ErrorCodeEnum;
import lombok.Data;

import java.io.Serializable;

/**
 * response base DTO
 *
 * @author yuanci
 */
@Data
public class Result<T> implements Serializable {
    private static final long serialVersionUID = -488730624643299315L;

    /**
     * request id of completion
     */
    private String requestId;

    /**
     * success or not
     */
    private boolean success = true;

    /**
     * error code
     */
    private String errorCode;

    /**
     * error msg
     */
    private String errorMsg;

    /**
     * 请求处理结果
     */
    private T data;

    public static <T> Result<T> success(T data) {
        Result<T> result = new Result<>();
        result.setSuccess(true);
        result.setData(data);
        return result;
    }

    public static <T> Result<T> success(String requestId, T data) {
        Result<T> result = new Result<>();
        result.setSuccess(true);
        result.setRequestId(requestId);
        result.setData(data);
        return result;
    }


    public static <T> Result<T> error(String errorCode, String errorMsg) {
        Result<T> result = new Result<>();
        result.setSuccess(false);
        result.setErrorCode(errorCode);
        result.setErrorMsg(errorMsg);
        return result;
    }

    public static <T> Result<T> error(String requestId, String errorCode, String errorMsg) {
        Result<T> result = new Result<>();
        result.setSuccess(false);
        result.setRequestId(requestId);
        result.setErrorCode(errorCode);
        result.setErrorMsg(errorMsg);
        return result;
    }


    public static <T> Result<T> error(String code) {
        Result<T> result = new Result<>();
        result.setSuccess(false);
        result.setErrorCode(code);
        return result;
    }

    public static <T> Result<T> error(ErrorCodeEnum errorCodeEnum) {
        Result<T> result = new Result<>();
        result.setSuccess(false);
        result.setErrorCode(errorCodeEnum.getErrorCode());
        result.setErrorMsg(errorCodeEnum.getErrorMessage());
        return result;
    }

    public static <T> Result<T> error(String requestId, ErrorCodeEnum errorCodeEnum) {
        Result<T> result = new Result<>();
        result.setSuccess(false);

        result.setRequestId(requestId);
        result.setErrorCode(errorCodeEnum.getErrorCode());
        result.setErrorMsg(errorCodeEnum.getErrorMessage());
        return result;
    }

    public void setErrorCodeEnum(ErrorCodeEnum errorCodeEnum) {
        this.setSuccess(false);
        this.setErrorCode(errorCodeEnum.getErrorCode());
        this.setErrorMsg(errorCodeEnum.getErrorMessage());
    }

    public boolean isFailed() {
        return !success;
    }
}
