package com.shilovich.hrbet.controller.command;

import com.shilovich.hrbet.bean.Horse;
import com.shilovich.hrbet.bean.Ratio;
import com.shilovich.hrbet.controller.Command;
import com.shilovich.hrbet.controller.Router;
import com.shilovich.hrbet.exception.CommandException;
import com.shilovich.hrbet.exception.ServiceException;
import com.shilovich.hrbet.service.HorseService;
import com.shilovich.hrbet.service.RatioService;
import com.shilovich.hrbet.service.ServiceFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.*;

import static com.shilovich.hrbet.controller.CommandParameter.*;

public class SetRatioCommand implements Command {
    @Override
    public Router execute(HttpServletRequest req, HttpServletResponse resp) throws CommandException {
        try {
            String id = req.getParameter(PARAM_RACE_ID);
            if (id != null && !id.isEmpty()) {
                Long raceId = Long.parseLong(id);
                HorseService horseService = (HorseService) ServiceFactory.getInstance().getClass(HorseService.class);
                Set<Horse> horses = horseService.showByRace(raceId);
                req.setAttribute(ATTR_RACE_SET, horses);
                return new Router(PAGE_SET_RATIO);
            }
//            RatioService ratioService = (RatioService) ServiceFactory.getInstance().getClass(RatioService.class);
            Map parameterMap = req.getParameterMap();
            return new Router(PAGE_INDEX);
        } catch (ServiceException e) {
            throw new CommandException(e.getMessage(), e);
        }
    }
}
