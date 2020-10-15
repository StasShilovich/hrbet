package com.shilovich.hrbet.controller.impl;

import com.shilovich.hrbet.controller.Command;
import com.shilovich.hrbet.controller.exception.CommandException;
import com.shilovich.hrbet.controller.model.ServletForward;
import com.shilovich.hrbet.service.exception.ServiceException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

import static com.shilovich.hrbet.constant.CommonConstant.PAGE_REGISTRATION;
import static com.shilovich.hrbet.constant.CommonConstant.PARAM_EMAIL;
import static com.shilovich.hrbet.constant.CommonConstant.PARAM_NAME;
import static com.shilovich.hrbet.constant.CommonConstant.PARAM_PASSWORD;
import static com.shilovich.hrbet.constant.CommonConstant.PARAM_SURNAME;

public class RegistrationCommandImpl implements Command {
    @Override
    public ServletForward execute(HttpServletRequest req) throws ServletException, IOException, CommandException {
        String name = req.getParameter(PARAM_NAME);
        String surname = req.getParameter(PARAM_SURNAME);
        String password = req.getParameter(PARAM_PASSWORD);
        String email = req.getParameter(PARAM_EMAIL);
        System.out.println(name + " " + surname + " " + password + " " + email);
//        if (name.isEmpty() || surname.isEmpty() || password.isEmpty() || email.isEmpty()) {
//            System.out.println("|||||||||||||||||||");
//            ServiceFactory factory = ServiceFactory.getInstance();
//            UserService userService = (UserService) factory.getClass(UserService.class);
////        userService.registration(new UserRegistration(name, surname, password, email));
//        }
        return new ServletForward(PAGE_REGISTRATION, false);
    }
}
