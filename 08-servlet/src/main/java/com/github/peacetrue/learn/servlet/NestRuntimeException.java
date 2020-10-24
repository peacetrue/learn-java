package com.github.peacetrue.learn.servlet;

/**
 * @author : xiayx
 * @since : 2020-10-24 09:20
 **/
public class NestRuntimeException extends RuntimeException {
    public NestRuntimeException(Exception cause) {
        super(cause);
    }
}
