package com.shilovich.hrbet.controller.command;

import com.shilovich.hrbet.bean.Horse;
import com.shilovich.hrbet.bean.Race;
import com.shilovich.hrbet.controller.Command;
import com.shilovich.hrbet.controller.Router;
import com.shilovich.hrbet.exception.CommandException;
import com.shilovich.hrbet.exception.ServiceException;
import com.shilovich.hrbet.service.HorseService;
import com.shilovich.hrbet.service.RaceService;
import com.shilovich.hrbet.service.ServiceFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashSet;
import java.util.Set;

import static com.shilovich.hrbet.controller.CommandParameter.*;
import static org.apache.commons.lang3.StringUtils.*;

public class EnterResultCommand implements Command {
    @Override
    public Router execute(HttpServletRequest req, HttpServletResponse resp) throws CommandException {
        try {
            String id = req.getParameter(PARAM_RACE_ID);
            if (isNotEmpty(id)) {
                RaceService raceService = (RaceService) ServiceFactory.getInstance().getClass(RaceService.class);
                Race race = raceService.findInfo(id);
                req.setAttribute(ATTR_RACE_INFO, race);
                HorseService horseService = (HorseService) ServiceFactory.getInstance().getClass(HorseService.class);
                Set<Horse> horses = horseService.showByRace(id);
                req.setAttribute(ATTR_RACE_SET, horses);
                return new Router(PAGE_ENTER_RESULT);
            }
            Set<String> horseSet = new HashSet<>();
            for (int i = 0; i < 5; i++) {
                String horseId = req.getParameter(PARAM_HORSE + i);
                if (isNotEmpty(horseId)) {
                    horseSet.add(horseId);
                }
            }
            // TODO: 12.11.2020 pay bets
            Router router = new Router(req);
            router.redirect();
            return router;
        } catch (ServiceException e) {
            throw new CommandException(e.getMessage(), e);
        }
    }
}
