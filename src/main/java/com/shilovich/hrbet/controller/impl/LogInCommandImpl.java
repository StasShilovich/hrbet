package com.shilovich.hrbet.controller.impl;

import com.shilovich.hrbet.beans.User;
import com.shilovich.hrbet.controller.Command;
import com.shilovich.hrbet.controller.exception.CommandException;
import com.shilovich.hrbet.controller.exception.LogInException;
import com.shilovich.hrbet.controller.model.ServletForward;
import com.shilovich.hrbet.service.ServiceFactory;
import com.shilovich.hrbet.service.UserService;
import com.shilovich.hrbet.service.exception.ServiceException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static com.shilovich.hrbet.constant.CommonConstant.ATTR_EMPTY_MESSAGE;
import static com.shilovich.hrbet.constant.CommonConstant.ATTR_EMPTY_PARAM;
import static com.shilovich.hrbet.constant.CommonConstant.ATTR_USER_AUTH;
import static com.shilovich.hrbet.constant.CommonConstant.PAGE_INDEX;
import static com.shilovich.hrbet.constant.CommonConstant.PARAM_EMAIL;
import static com.shilovich.hrbet.constant.CommonConstant.PARAM_PASSWORD;

public class LogInCommandImpl implements Command {
    @Override
    public ServletForward execute(HttpServletRequest req) throws ServletException, IOException, CommandException {
        try {
            String email = req.getParameter(PARAM_EMAIL);
            String password = req.getParameter(PARAM_PASSWORD);
            System.out.println(email + " ||||| " + password);
            if (email.isEmpty() || password.isEmpty()) {
                req.setAttribute(ATTR_EMPTY_PARAM, ATTR_EMPTY_MESSAGE);
                return new ServletForward(PAGE_INDEX, false);
            }
            User user = new User();
            user.setEmail(email);
            user.setPassword(password);
            UserService userService = (UserService) ServiceFactory.getInstance().getClass(UserService.class);
            User userAuthorized = userService.authorization(user);
            if (userAuthorized != null) {
                HttpSession session = req.getSession();
                session.setAttribute(ATTR_USER_AUTH, userAuthorized);
            }
            return new ServletForward(PAGE_INDEX, false);
        } catch (ServiceException e) {
            throw new LogInException(e.getMessage(), e);
        }
    }
}