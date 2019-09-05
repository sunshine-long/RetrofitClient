package com.marlon.retrofitclent.exception;

/**
 * @author Marlon
 */
public class ResponeException extends Exception {
    public String message;
    public int code;

    public ResponeException(Throwable throwable, int resultCode) {
        super(throwable);
        this.code = resultCode;
        this.message = throwable.getMessage();
    }

    public ResponeException(int code,String detailMessage) {
        super(detailMessage);
        this.code = code;
        this.message = detailMessage;
    }
}
