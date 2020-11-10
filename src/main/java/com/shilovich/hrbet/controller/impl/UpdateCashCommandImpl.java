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
import java.math.BigDecimal;

import static com.shilovich.hrbet.controller.CommandParameter.*;

public class UpdateCashCommandImpl implements Command {
    @Override
    public ServletForward execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, CommandException {
        try {
            String addCash = req.getParameter(PARAM_ADD_CASH);
            if (addCash != null) {
                User user = (User) req.getSession().getAttribute(ATTR_USER_AUTH);
                BigDecimal newCash = new BigDecimal(addCash).add(user.getCash());
                user.setCash(newCash);
                UserService userService = (UserService) ServiceFactory.getInstance().getClass(UserService.class);
                boolean result = userService.updateCash(newCash, user.getId());
                if (result) {
                    req.getSession().setAttribute(ATTR_USER_AUTH, user);
                }
            }
            return new ServletForward(PAGE_PROFILE);
        } catch (ServiceException e) {
            throw new CommandException(e.getMessage(), e);
        }
    }
}
