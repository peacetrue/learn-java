package com.github.peacetrue.learn.servlet;

import lombok.extern.slf4j.Slf4j;

/**
 * @author : xiayx
 * @since : 2020-10-24 08:31
 **/
@Slf4j
public class HelloHandlerImpl implements HelloHandler {
    @Override
    public void handle() {
        log.info("handle");
    }
}
