package com.shilovich.hrbet.controller.filter;

import com.shilovich.hrbet.bean.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.shilovich.hrbet.controller.CommandParameter.ATTR_USER_AUTH;

public class SecurityFilter implements Filter {
    private static final Logger logger = LogManager.getLogger(SecurityFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        User user = (User) request.getSession().getAttribute(ATTR_USER_AUTH);
        if (user!=null){

        }
        filterChain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }
}
