package com.shilovich.hrbet.controller.command;

import com.shilovich.hrbet.controller.Command;
import com.shilovich.hrbet.controller.Router;
import com.shilovich.hrbet.exception.CommandException;
import com.shilovich.hrbet.exception.ServiceException;
import com.shilovich.hrbet.service.RaceService;
import com.shilovich.hrbet.service.ServiceFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.shilovich.hrbet.controller.CommandParameter.*;
import static org.apache.commons.lang3.StringUtils.*;

public class DeleteRaceCommand implements Command {
    @Override
    public Router execute(HttpServletRequest req, HttpServletResponse resp) throws CommandException {
        try {
            String raceId = req.getParameter(PARAM_RACE_ID);
            if (isNoneEmpty(raceId)) {
                RaceService raceService = (RaceService) ServiceFactory.getInstance().getClass(RaceService.class);
                raceService.delete(raceId);
            }
            // TODO: 21.11.2020 If race have bets u cant delete race or pay bets back
            Router router = new Router(PAGE_REDIRECT_INDEX);
            router.redirect();
            return router;
        } catch (ServiceException e) {
            throw new CommandException(e.getMessage(), e);
        }
    }
}
