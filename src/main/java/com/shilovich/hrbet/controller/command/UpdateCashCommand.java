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
import java.math.BigDecimal;
import java.util.Set;

import static com.shilovich.hrbet.bean.PermissionEnum.USER_BASIC;
import static com.shilovich.hrbet.controller.CommandParameter.ATTR_USER_AUTH;
import static com.shilovich.hrbet.controller.CommandParameter.PAGE_PROFILE;
import static com.shilovich.hrbet.controller.CommandParameter.PARAM_ADD_CASH;
import static org.apache.commons.lang3.StringUtils.isNotEmpty;

public class UpdateCashCommand implements Command {
    @Override
    public Router execute(HttpServletRequest req, HttpServletResponse resp) throws CommandException {
        try {
            String addCash = req.getParameter(PARAM_ADD_CASH);
            if (isNotEmpty(addCash)) {
                User user = (User) req.getSession().getAttribute(ATTR_USER_AUTH);
                BigDecimal newCash = new BigDecimal(addCash).add(user.getCash());
                user.setCash(newCash);
                UserService userService = (UserService) ServiceFactory.getInstance().getClass(UserService.class);
                boolean result = userService.updateCash(newCash, user.getId());
                if (result) {
                    req.getSession().setAttribute(ATTR_USER_AUTH, user);
                }
            }
            return new Router(PAGE_PROFILE);
        } catch (ServiceException e) {
            throw new CommandException(e.getMessage(), e);
        }
    }

    @Override
    public boolean isAllowed(Set<PermissionEnum> permissions) {
        return permissions.contains(USER_BASIC);
    }
}
