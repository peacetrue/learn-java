package com.github.peacetrue.learn.servlet;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.AsyncContext;
import javax.servlet.ReadListener;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * ${cursor}
 *
 * @author : xiayx
 * @since : 2020-10-23 21:38
 **/
@Slf4j
@WebServlet(name = "SyncHelloServlet", urlPatterns = "/sync", asyncSupported = true)
public class SyncHelloServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.info("doGet:{},{}", request, response);
        AsyncContext asyncContext = request.startAsync();

        /*
        asyncContext.start(() -> {
            try {
                asyncContext.getResponse().getWriter().append("Served at:").append(request.getServletPath()).flush();
            } catch (IOException e) {
                throw new NestRuntimeException(e);
            } finally {
                asyncContext.complete();
            }
        });
        */

        asyncContext.getRequest().getInputStream().setReadListener(new ReadListener() {
            @Override
            public void onDataAvailable() throws IOException {
                log.info("onDataAvailable");
            }

            @Override
            public void onAllDataRead() throws IOException {
                log.info("onAllDataRead");
                asyncContext.start(() -> {
                    try {
                        asyncContext.getResponse().getWriter().append("Served at:").append(request.getServletPath()).flush();
                    } catch (IOException e) {
                        throw new NestRuntimeException(e);
                    } finally {
                        asyncContext.complete();
                    }
                });
            }

            @Override
            public void onError(Throwable t) {
                asyncContext.complete();
            }
        });

    }

}
