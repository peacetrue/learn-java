package com.github.peacetrue.learn.servlet;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

/**
 * ${cursor}
 *
 * @author : xiayx
 * @since : 2020-10-24 08:19
 **/
@Slf4j
@WebFilter(filterName = "HelloFilter", urlPatterns = "/*", asyncSupported = true)
public class HelloFilter implements Filter {

    public void init(FilterConfig config) throws ServletException {
        log.info("init:{}", config);
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        log.info("doFilter:{},{},{}", req, resp, chain);
        chain.doFilter(req, resp);
    }

    public void destroy() {
        log.info("destroy");
    }

}
