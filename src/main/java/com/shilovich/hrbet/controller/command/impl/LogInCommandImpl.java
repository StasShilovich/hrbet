package com.shilovich.hrbet.controller.command.impl;

import com.shilovich.hrbet.beans.UserAuthorized;
import com.shilovich.hrbet.beans.UserLogIn;
import com.shilovich.hrbet.controller.Command;
import com.shilovich.hrbet.controller.command.ServletForward;
import com.shilovich.hrbet.service.ServiceFactory;
import com.shilovich.hrbet.service.UserService;
import com.shilovich.hrbet.service.exception.ServiceException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class LogInCommandImpl implements Command {
    @Override
    public ServletForward execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        try {
            if (email.isEmpty() || password.isEmpty()) {
                req.setAttribute("emptyParams", "There should be no empty fields!");
                return new ServletForward("/index.jsp", false);
            }
            ServiceFactory factory = ServiceFactory.getInstance();
            UserService userService = (UserService) factory.getClass(UserService.class);
            UserAuthorized userAuthorized = userService.authorization(new UserLogIn(email, password));
            if (userAuthorized != null) {
                HttpSession session = req.getSession();
                session.setAttribute("userAuth", userAuthorized);
            }
        } catch (ServiceException e) {
            // TODO: 05.10.2020 logger
            System.out.println(e.getMessage());
        }
        return new ServletForward("/index.jsp", false);
    }
}
