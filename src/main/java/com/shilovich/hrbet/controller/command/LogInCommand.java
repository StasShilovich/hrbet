package com.shilovich.hrbet.controller.command;

import com.shilovich.hrbet.bean.PermissionEnum;
import com.shilovich.hrbet.bean.User;
import com.shilovich.hrbet.controller.Command;
import com.shilovich.hrbet.exception.CommandException;
import com.shilovich.hrbet.controller.Router;
import com.shilovich.hrbet.service.ServiceFactory;
import com.shilovich.hrbet.service.UserService;
import com.shilovich.hrbet.exception.ServiceException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Set;

import static com.shilovich.hrbet.bean.PermissionEnum.QUEST_BASIC;
import static com.shilovich.hrbet.controller.CommandParameter.ATTR_EMPTY_MESSAGE;
import static com.shilovich.hrbet.controller.CommandParameter.ATTR_EMPTY_PARAM;
import static com.shilovich.hrbet.controller.CommandParameter.ATTR_INCORRECT_DATA;
import static com.shilovich.hrbet.controller.CommandParameter.ATTR_INCORRECT_MESSAGE;
import static com.shilovich.hrbet.controller.CommandParameter.ATTR_USER_AUTH;
import static com.shilovich.hrbet.controller.CommandParameter.PAGE_INDEX;
import static com.shilovich.hrbet.controller.CommandParameter.PARAM_EMAIL;
import static com.shilovich.hrbet.controller.CommandParameter.PARAM_PASSWORD;
import static org.apache.commons.lang3.StringUtils.isEmpty;

public class LogInCommand implements Command {
    @Override
    public Router execute(HttpServletRequest req, HttpServletResponse resp) throws CommandException {
        try {
            String email = req.getParameter(PARAM_EMAIL);
            String password = req.getParameter(PARAM_PASSWORD);
            if (isEmpty(email) || isEmpty(password)) {
                req.setAttribute(ATTR_EMPTY_PARAM, ATTR_EMPTY_MESSAGE);
                return new Router(PAGE_INDEX);
            }
            User user = new User();
            user.setEmail(email);
            user.setPassword(password);
            UserService userService = (UserService) ServiceFactory.getInstance().getClass(UserService.class);
            User userAuthorized = userService.authorization(user);
            if (userAuthorized != null) {
                HttpSession session = req.getSession();
                session.setAttribute(ATTR_USER_AUTH, userAuthorized);
            } else {
                req.setAttribute(ATTR_INCORRECT_DATA, ATTR_INCORRECT_MESSAGE);
                return new Router(PAGE_INDEX);
            }
            return new Router(PAGE_INDEX);
        } catch (ServiceException e) {
            throw new CommandException(e.getMessage(), e);
        }
    }

    @Override
    public boolean isAllowed(Set<PermissionEnum> permissions) {
        return permissions.contains(QUEST_BASIC);
    }
}