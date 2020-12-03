package com.shilovich.hrbet.controller.command;

import com.shilovich.hrbet.bean.Horse;
import com.shilovich.hrbet.bean.Race;
import com.shilovich.hrbet.controller.Command;
import com.shilovich.hrbet.controller.Router;
import com.shilovich.hrbet.exception.CommandException;
import com.shilovich.hrbet.exception.ServiceException;
import com.shilovich.hrbet.service.BetService;
import com.shilovich.hrbet.service.HorseService;
import com.shilovich.hrbet.service.RaceService;
import com.shilovich.hrbet.service.ServiceFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static com.shilovich.hrbet.controller.CommandParameter.*;
import static org.apache.commons.lang3.StringUtils.*;

public class EnterResultCommand implements Command {
    @Override
    public Router execute(HttpServletRequest req, HttpServletResponse resp) throws CommandException {
        try {
            Map<Integer, String> horseMap = new HashMap<>();
            for (int i = 0; i < 5; i++) {
                String horseId = req.getParameter(PARAM_HORSE + i);
                if (isNotEmpty(horseId)) {
                    horseMap.put(i++, horseId);
                }
            }
            String id = req.getParameter(PARAM_RACE_ID);
            if (isNotEmpty(id) && horseMap.size() < MIN_HORSE_IN_RACE) {
                RaceService raceService = (RaceService) ServiceFactory.getInstance().getClass(RaceService.class);
                Race race = raceService.findInfo(id);
                req.setAttribute(ATTR_RACE_INFO, race);
                HorseService horseService = (HorseService) ServiceFactory.getInstance().getClass(HorseService.class);
                Set<Horse> horses = horseService.showByRace(id);
                req.setAttribute(ATTR_RACE_SET, horses);
                return new Router(PAGE_ENTER_RESULT);
            } else {
                if (isNotEmpty(id)) {
                    BetService betService = (BetService) ServiceFactory.getInstance().getClass(BetService.class);
                    betService.enterResult(horseMap, id);
                }
                Router router = new Router(PAGE_REDIRECT_INDEX);
                router.redirect();
                return router;
            }
        } catch (ServiceException e) {
            throw new CommandException(e.getMessage(), e);
        }
    }
}
