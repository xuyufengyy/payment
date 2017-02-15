package com.payment.exception;

/**
 * Created by xuyufengyy on 2017/2/15.
 */
public class BaseException extends RuntimeException {

    public BaseException(String message) {
        super(message, new Throwable(message));
    }

    public BaseException(Throwable cause) {
        super(cause);
    }

    public BaseException(String message, Throwable cause) {
        super(message, cause);
    }
}
