package com.shilovich.hrbet.controller.impl;

import com.shilovich.hrbet.beans.User;
import com.shilovich.hrbet.controller.Command;
import com.shilovich.hrbet.controller.exception.CommandException;
import com.shilovich.hrbet.controller.model.ServletForward;
import com.shilovich.hrbet.service.ServiceFactory;
import com.shilovich.hrbet.service.UserService;
import com.shilovich.hrbet.service.exception.ServiceException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.shilovich.hrbet.constant.CommonConstant.*;

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
                User user = userService.registration(new User(name, surname, password, email));
                if (user != null) {
                    return new ServletForward(PAGE_REDIRECT_INDEX, true);
                }
            }
            return new ServletForward(PAGE_REGISTRATION, false);
        } catch (ServiceException e) {
            throw new CommandException(e.getMessage(), e);
        }
    }
}