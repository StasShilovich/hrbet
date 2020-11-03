package com.shilovich.hrbet.controller.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.shilovich.hrbet.controller.CommandParameter.PAGE_START;
import static com.shilovich.hrbet.controller.CommandParameter.PAGE_USER_FRIENDLY;

public class UrlTransformationFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        String requestURI = request.getRequestURL().toString().trim();
//        if (requestURI.contains("localhost")) {
//            String newUri = requestURI.replaceAll(PAGE_START, PAGE_USER_FRIENDLY);
//            servletRequest.getRequestDispatcher(newUri).forward(servletRequest, servletResponse);
//        } else {
//    }
            filterChain.doFilter(servletRequest, servletResponse);

    }

    @Override
    public void destroy() {

    }
}
