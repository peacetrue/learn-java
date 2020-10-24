package com.github.peacetrue.learn.servlet;

import lombok.extern.slf4j.Slf4j;

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
@WebServlet(urlPatterns = "/")
public class HelloServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.info("doGet:{},{}", request, response);
        response.getWriter().append("Served at:").append(request.getServletPath()).flush();
    }
}
