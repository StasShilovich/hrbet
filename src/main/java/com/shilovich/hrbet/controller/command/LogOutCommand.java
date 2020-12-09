package com.shilovich.hrbet.controller.command;

import com.shilovich.hrbet.bean.PermissionEnum;
import com.shilovich.hrbet.controller.Command;
import com.shilovich.hrbet.exception.CommandException;
import com.shilovich.hrbet.controller.Router;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Set;

import static com.shilovich.hrbet.bean.PermissionEnum.ADMIN_BASIC;
import static com.shilovich.hrbet.bean.PermissionEnum.CUSTOMER_BASIC;
import static com.shilovich.hrbet.bean.PermissionEnum.USER_BASIC;
import static com.shilovich.hrbet.controller.CommandParameter.PAGE_INDEX;

public class LogOutCommand implements Command {
    @Override
    public Router execute(HttpServletRequest req, HttpServletResponse resp) throws CommandException {
        HttpSession session = req.getSession();
        if (session != null) {
            session.invalidate();
        }
        return new Router(PAGE_INDEX);
    }

    @Override
    public boolean isAllowed(Set<PermissionEnum> permissions) {
        return permissions.contains(USER_BASIC) || permissions.contains(ADMIN_BASIC) ||
                permissions.contains(CUSTOMER_BASIC);
    }
}