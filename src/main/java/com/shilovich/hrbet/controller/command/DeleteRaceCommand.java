package com.shilovich.hrbet.controller.command;

import com.shilovich.hrbet.controller.Command;
import com.shilovich.hrbet.controller.Router;
import com.shilovich.hrbet.exception.CommandException;
import com.shilovich.hrbet.exception.ServiceException;
import com.shilovich.hrbet.service.RaceService;
import com.shilovich.hrbet.service.ServiceFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.shilovich.hrbet.controller.CommandParameter.PARAM_RACE_ID;

public class DeleteRaceCommand implements Command {
    @Override
    public Router execute(HttpServletRequest req, HttpServletResponse resp) throws CommandException {
        try {
            String raceId = req.getParameter(PARAM_RACE_ID);
            if (raceId != null && !raceId.isEmpty()) {
                RaceService raceService = (RaceService) ServiceFactory.getInstance().getClass(RaceService.class);
                raceService.delete(Long.valueOf(raceId));
            }
            Router router = new Router(req);
            router.redirect();
            return router;
        } catch (ServiceException e) {
            throw new CommandException(e.getMessage(), e);
        }
    }
}
