package com.dgj.project.ratelimiter.exception;

/**
 * @version: v1.0
 * @date: 2021/2/26
 * @author: dgj
 */
public class InternalErrorException extends Throwable {
    public InternalErrorException(String message, InterruptedException exception) {
        super(message,exception);
    }

    public InternalErrorException(String message) {
        super(message);
    }
}
