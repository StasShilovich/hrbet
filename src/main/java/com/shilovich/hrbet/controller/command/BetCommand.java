package com.shilovich.hrbet.controller.command;

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

import java.math.BigDecimal;

import static com.shilovich.hrbet.controller.CommandParameter.*;
import static org.apache.commons.lang3.StringUtils.*;

public class BetCommand implements Command {
    @Override
    public Router execute(HttpServletRequest req, HttpServletResponse resp) throws CommandException {
        try {
            String info = req.getParameter(PARAM_BET_INFO);
            String cash = req.getParameter(PARAM_BET_CASH);
            if (isNotEmpty(info) && isNotEmpty(cash)) {
                HttpSession session = req.getSession();
                User user = (User) session.getAttribute(ATTR_USER_AUTH);
                BetService betService = (BetService) ServiceFactory.getInstance().getClass(BetService.class);
                boolean result = betService.add(info, cash, user.getCash(), user.getId());
                if (result) {
                    BigDecimal userCash = user.getCash();
                    BigDecimal newUserCash = userCash.subtract(new BigDecimal(cash));
                    user.setCash(newUserCash);
                    session.setAttribute(ATTR_USER_AUTH, user);
                    // TODO: 21.11.2020 delete cash from database 
                    Router router = new Router(req);
                    router.redirect();
                    return router;
                }
            }
            return new Router(req);
        } catch (ServiceException e) {
            throw new CommandException(e.getMessage(), e);
        }
    }
}
