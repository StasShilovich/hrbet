package com.shilovich.hrbet.controller.command;

import com.shilovich.hrbet.bean.Bet;
import com.shilovich.hrbet.bean.PermissionEnum;
import com.shilovich.hrbet.bean.User;
import com.shilovich.hrbet.controller.Command;
import com.shilovich.hrbet.controller.Router;
import com.shilovich.hrbet.exception.CommandException;
import com.shilovich.hrbet.exception.ServiceException;
import com.shilovich.hrbet.service.BetService;
import com.shilovich.hrbet.service.ServiceFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Set;

import static com.shilovich.hrbet.bean.PermissionEnum.*;
import static com.shilovich.hrbet.controller.CommandParameter.*;

public class ShowUserBetsCommand implements Command {
    @Override
    public Router execute(HttpServletRequest req, HttpServletResponse resp) throws CommandException {
        try {
            HttpSession session = req.getSession();
            User user = (User) session.getAttribute(ATTR_USER_AUTH);
            ServiceFactory factory = ServiceFactory.getInstance();
            BetService betService = (BetService) factory.getClass(BetService.class);
            List<Bet> bets = betService.showByUser(user.getId());
            req.setAttribute(ATTR_USER_BETS, bets);
            return new Router(PAGE_PROFILE_BETS);
        } catch (ServiceException e) {
            throw new CommandException(e.getMessage());
        }
    }

    @Override
    public boolean isAllowed(Set<PermissionEnum> permissions) {
        return permissions.contains(USER_BASIC);
    }
}