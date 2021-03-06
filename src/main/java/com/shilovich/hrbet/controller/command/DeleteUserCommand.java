package com.shilovich.hrbet.controller.command;

import com.shilovich.hrbet.bean.PermissionEnum;
import com.shilovich.hrbet.controller.Command;
import com.shilovich.hrbet.controller.Router;
import com.shilovich.hrbet.exception.CommandException;
import com.shilovich.hrbet.exception.ServiceException;
import com.shilovich.hrbet.service.ServiceFactory;
import com.shilovich.hrbet.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Set;

import static com.shilovich.hrbet.bean.PermissionEnum.ADMIN_BASIC;
import static com.shilovich.hrbet.bean.PermissionEnum.BAN_USER;
import static com.shilovich.hrbet.controller.CommandParameter.PARAM_USER_ID;
import static org.apache.commons.lang3.StringUtils.isNotEmpty;

public class DeleteUserCommand implements Command {
    @Override
    public Router execute(HttpServletRequest req, HttpServletResponse resp) throws CommandException {
        try {
            String userId = req.getParameter(PARAM_USER_ID);
            if (isNotEmpty(userId)) {
                UserService userService = (UserService) ServiceFactory.getInstance().getClass(UserService.class);
                userService.ban(userId);
            }
            Router router = new Router(req);
            router.redirect();
            return router;
        } catch (ServiceException e) {
            throw new CommandException(e.getMessage(), e);
        }
    }

    @Override
    public boolean isAllowed(Set<PermissionEnum> permissions) {
        return permissions.contains(ADMIN_BASIC) && permissions.contains(BAN_USER);
    }
}