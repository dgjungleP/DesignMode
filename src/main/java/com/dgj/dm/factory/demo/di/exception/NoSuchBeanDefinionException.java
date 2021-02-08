package com.dgj.dm.factory.demo.di.exception;

/**
 * @version: v1.0
 * @date: 2021/2/8
 * @author: dgj
 */
public class NoSuchBeanDefinionException extends RuntimeException {
    public NoSuchBeanDefinionException(String message) {
        super(message);
    }
}
