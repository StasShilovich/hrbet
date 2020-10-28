package com.shilovich.hrbet.controller.impl;

import com.shilovich.hrbet.beans.Bet;
import com.shilovich.hrbet.beans.User;
import com.shilovich.hrbet.controller.Command;
import com.shilovich.hrbet.controller.model.ServletForward;
import com.shilovich.hrbet.exception.CommandException;
import com.shilovich.hrbet.exception.ServiceException;
import com.shilovich.hrbet.service.BetService;
import com.shilovich.hrbet.service.ServiceFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

import static com.shilovich.hrbet.constant.CommandConstant.*;

public class ShowUserBetsCommandImpl implements Command {
    @Override
    public ServletForward execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, CommandException {
        try {
            HttpSession session = req.getSession();
            User user = (User) session.getAttribute(ATTR_USER_AUTH);
            ServiceFactory factory = ServiceFactory.getInstance();
            BetService betService = (BetService) factory.getClass(BetService.class);
            List<Bet> bets = betService.showByUser(user.getId());
            req.setAttribute(ATTR_USER_BETS, bets);
            return new ServletForward(PAGE_PROFILE_BETS);
        } catch (ServiceException e) {
            throw new CommandException(e.getMessage());
        }
    }
}
