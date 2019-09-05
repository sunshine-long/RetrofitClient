package com.marlon.retrofitclent.exception;

/**
 * @author Marlon
 * @desc
 * @date 2019/5/14
 */
public class ServerException extends RuntimeException{
    public String message;
    public int code;

    public ServerException(String message, int code) {
        this.message = message;
        this.code = code;
    }
}
