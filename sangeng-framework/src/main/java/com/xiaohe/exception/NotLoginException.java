package com.xiaohe.exception;

/**
 * @author : 小何
 * @Description :
 * @date : 2023-01-08 17:15
 */
public class NotLoginException extends RuntimeException {

    public NotLoginException(String message) {
        super(message);
    }
}
