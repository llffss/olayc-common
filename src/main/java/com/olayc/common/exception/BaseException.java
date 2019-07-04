package com.olayc.common.exception;

import com.olayc.common.constant.BaseEnum;
import com.olayc.common.constant.CommonResultEnum;

/**
 * 描述：
 *
 * @author jianbiao11
 * Date  2018-05-22
 **/
public class BaseException extends RuntimeException {

    private int status = CommonResultEnum.FAIL_RESULT_CODE.getCode();

    private String customMsg;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public BaseException() {
    }

    public BaseException(int status) {
        super(String.format("this is %s exception!", status));
        this.status = status;
    }

    public BaseException(int status, String message) {
        super(message);
        this.status = status;
        this.customMsg = message;
    }

    public BaseException(BaseEnum reason) {
        this.status = reason.getCode();
        this.customMsg = reason.getMessage();
    }

    public BaseException(String message) {
        super(message);
        this.customMsg = message;
    }

    public BaseException(String message, Throwable cause) {
        super(message, cause);
        this.customMsg = message;
    }

    public BaseException(Throwable cause) {
        super(cause);
    }

    public BaseException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.customMsg = message;
    }

    public String getCustomMsg() {
        return customMsg;
    }
}
