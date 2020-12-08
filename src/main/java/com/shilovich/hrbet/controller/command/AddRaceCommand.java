package com.shilovich.hrbet.controller.command;

import com.shilovich.hrbet.bean.Horse;
import com.shilovich.hrbet.bean.PermissionEnum;
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
import java.util.List;
import java.util.Set;

import static com.shilovich.hrbet.controller.CommandParameter.*;
import static org.apache.commons.lang3.StringUtils.*;

public class AddRaceCommand implements Command {
    @Override
    public Router execute(HttpServletRequest req, HttpServletResponse resp) throws CommandException {
        try {
            String location = req.getParameter(PARAM_LOCATION);
            String date = req.getParameter(PARAM_DATE);
            Set<Long> horseSet = new HashSet<>();
            for (int i = 0; i <= 5; i++) {
                String id = req.getParameter(PARAM_HORSE + i);
                if (id != null && !id.trim().isEmpty()) {
                    horseSet.add(Long.parseLong(id));
                }
            }
            if (isNoneEmpty(location) && isNotEmpty(date) && horseSet.size() >= MIN_HORSE_IN_RACE) {
                RaceService raceService = (RaceService) ServiceFactory.getInstance().getClass(RaceService.class);
                boolean result = raceService.addRace(horseSet, location, date);
                if (result) {
                    Router router = new Router(PAGE_REDIRECT_INDEX);
                    router.redirect();
                    return router;
                }
            }
            HorseService horseService = (HorseService) ServiceFactory.getInstance().getClass(HorseService.class);
            List<Horse> horses = horseService.findAll();
            req.setAttribute(ATTR_HORSE_LIST, horses);
            return new Router(PAGE_ADD_RACE);
        } catch (ServiceException e) {
            throw new CommandException(e.getMessage(), e);
        }
    }

    @Override
    public boolean isAllowed(Set<PermissionEnum> permissions) {
        return permissions.contains(PermissionEnum.CUSTOMER_BASIC);
    }
}
