package com.shilovich.hrbet.controller.impl;

import com.shilovich.hrbet.bean.User;
import com.shilovich.hrbet.controller.Command;
import com.shilovich.hrbet.exception.CommandException;
import com.shilovich.hrbet.controller.model.ServletForward;
import com.shilovich.hrbet.service.ServiceFactory;
import com.shilovich.hrbet.service.UserService;
import com.shilovich.hrbet.exception.ServiceException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

import static com.shilovich.hrbet.controller.CommandParameter.*;

public class RegistrationCommandImpl implements Command {
    @Override
    public ServletForward execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, CommandException {
        try {
            String name = req.getParameter(PARAM_NAME);
            String surname = req.getParameter(PARAM_SURNAME);
            String password = req.getParameter(PARAM_PASSWORD);
            String email = req.getParameter(PARAM_EMAIL);
            if (!name.isEmpty() && !surname.isEmpty() && !password.isEmpty() && !email.isEmpty()) {
                ServiceFactory factory = ServiceFactory.getInstance();
                UserService userService = (UserService) factory.getClass(UserService.class);
                Map<String, String> userMap = userService.registration(new User(name, surname, password, email));
                if (userMap == null) {
                    ServletForward servletForward = new ServletForward(PAGE_REDIRECT_INDEX);
                    servletForward.redirect();
                    return servletForward;
                }
                req.setAttribute(ATTR_USER_MAP, userMap);
            }
            req.setAttribute(ATTR_EMPTY_PARAM, ATTR_EMPTY_MESSAGE);
            return new ServletForward(PAGE_REGISTRATION);
        } catch (ServiceException e) {
            throw new CommandException(e.getMessage(), e);
        }
    }
}