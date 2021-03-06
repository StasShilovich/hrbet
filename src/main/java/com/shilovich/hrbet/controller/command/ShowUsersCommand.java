package com.shilovich.hrbet.controller.command;

import com.shilovich.hrbet.bean.PermissionEnum;
import com.shilovich.hrbet.bean.User;
import com.shilovich.hrbet.controller.Command;
import com.shilovich.hrbet.controller.Router;
import com.shilovich.hrbet.exception.CommandException;
import com.shilovich.hrbet.exception.ServiceException;
import com.shilovich.hrbet.service.ServiceFactory;
import com.shilovich.hrbet.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Set;

import static com.shilovich.hrbet.bean.PermissionEnum.ADMIN_BASIC;
import static com.shilovich.hrbet.controller.CommandParameter.ATTR_PAGE_NUMBER;
import static com.shilovich.hrbet.controller.CommandParameter.ATTR_USERS_LIST;
import static com.shilovich.hrbet.controller.CommandParameter.PAGE_USERS;
import static com.shilovich.hrbet.controller.CommandParameter.PARAM_PAGE;

public class ShowUsersCommand implements Command {
    @Override
    public Router execute(HttpServletRequest req, HttpServletResponse resp) throws CommandException {
        try {
            UserService userService = (UserService) ServiceFactory.getInstance().getClass(UserService.class);
            int pagesCount = userService.getUsersPagesCount();
            req.setAttribute(ATTR_PAGE_NUMBER, pagesCount);
            String page = req.getParameter(PARAM_PAGE);
            List<User> users = userService.findAll(page);
            req.setAttribute(ATTR_USERS_LIST, users);
            return new Router(PAGE_USERS);
        } catch (ServiceException e) {
            throw new CommandException(e.getMessage(), e);
        }
    }

    @Override
    public boolean isAllowed(Set<PermissionEnum> permissions) {
        return permissions.contains(ADMIN_BASIC);
    }
}
