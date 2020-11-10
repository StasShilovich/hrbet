package com.shilovich.hrbet.controller.impl;

import com.shilovich.hrbet.bean.User;
import com.shilovich.hrbet.controller.Command;
import com.shilovich.hrbet.controller.model.ServletForward;
import com.shilovich.hrbet.exception.CommandException;
import com.shilovich.hrbet.exception.ServiceException;
import com.shilovich.hrbet.service.ServiceFactory;
import com.shilovich.hrbet.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import static com.shilovich.hrbet.controller.CommandParameter.*;
import static com.shilovich.hrbet.service.ServiceParameter.USERS_ON_PAGE;

public class ShowUsersCommandImpl implements Command {
    @Override
    public ServletForward execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, CommandException {
        try {
            UserService userService = (UserService) ServiceFactory.getInstance().getClass(UserService.class);
            int pagesCount = userService.getUsersPagesCount();
            req.setAttribute(ATTR_PAGE_NUMBER, pagesCount);
            int page = PAGE_OFFSET;
            String parameter = req.getParameter(PARAM_PAGE);
            if (parameter != null) {
                page = (Integer.parseInt(parameter) - 1) * USERS_ON_PAGE;
            }
            List<User> users = userService.findAll(USERS_ON_PAGE, page).getList();
            req.setAttribute(ATTR_USERS_LIST, users);
            return new ServletForward(PAGE_USERS);
        } catch (ServiceException e) {
            throw new CommandException(e.getMessage(), e);
        }
    }
}
