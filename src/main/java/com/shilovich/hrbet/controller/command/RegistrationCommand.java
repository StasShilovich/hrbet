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
import java.util.Map;
import java.util.Set;

import static com.shilovich.hrbet.bean.PermissionEnum.ADMIN_BASIC;
import static com.shilovich.hrbet.bean.PermissionEnum.CUSTOMER_BASIC;
import static com.shilovich.hrbet.bean.PermissionEnum.QUEST_BASIC;
import static com.shilovich.hrbet.bean.PermissionEnum.USER_BASIC;
import static com.shilovich.hrbet.controller.CommandParameter.ATTR_EMPTY_MESSAGE;
import static com.shilovich.hrbet.controller.CommandParameter.ATTR_EMPTY_PARAM;
import static com.shilovich.hrbet.controller.CommandParameter.ATTR_USER_MAP;
import static com.shilovich.hrbet.controller.CommandParameter.PAGE_REDIRECT_INDEX;
import static com.shilovich.hrbet.controller.CommandParameter.PAGE_REGISTRATION;
import static com.shilovich.hrbet.controller.CommandParameter.PARAM_EMAIL;
import static com.shilovich.hrbet.controller.CommandParameter.PARAM_NAME;
import static com.shilovich.hrbet.controller.CommandParameter.PARAM_PASSWORD;
import static com.shilovich.hrbet.controller.CommandParameter.PARAM_SURNAME;
import static org.apache.commons.lang3.StringUtils.isNotEmpty;

public class RegistrationCommand implements Command {
    @Override
    public Router execute(HttpServletRequest req, HttpServletResponse resp) throws CommandException {
        try {
            String name = req.getParameter(PARAM_NAME);
            String surname = req.getParameter(PARAM_SURNAME);
            String password = req.getParameter(PARAM_PASSWORD);
            String email = req.getParameter(PARAM_EMAIL);
            if (isNotEmpty(name) && isNotEmpty(surname) && isNotEmpty(password) && isNotEmpty(email)) {
                UserService userService = (UserService) ServiceFactory.getInstance().getClass(UserService.class);
                Map<String, String> userMap = userService.registration(new User(name, surname, password, email));
                if (userMap == null) {
                    Router router = new Router(PAGE_REDIRECT_INDEX);
                    router.redirect();
                    return router;
                }
                req.setAttribute(ATTR_USER_MAP, userMap);
            }
            req.setAttribute(ATTR_EMPTY_PARAM, ATTR_EMPTY_MESSAGE);
            return new Router(PAGE_REGISTRATION);
        } catch (ServiceException e) {
            throw new CommandException(e.getMessage(), e);
        }
    }

    @Override
    public boolean isAllowed(Set<PermissionEnum> permissions) {
        return permissions.contains(QUEST_BASIC) || permissions.contains(USER_BASIC) ||
                permissions.contains(ADMIN_BASIC) || permissions.contains(CUSTOMER_BASIC);
    }
}