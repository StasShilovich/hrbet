package com.shilovich.hrbet.controller.impl;

import com.shilovich.hrbet.bean.Horse;
import com.shilovich.hrbet.bean.Ratio;
import com.shilovich.hrbet.controller.Command;
import com.shilovich.hrbet.exception.CommandException;
import com.shilovich.hrbet.controller.model.ServletForward;
import com.shilovich.hrbet.service.HorseService;
import com.shilovich.hrbet.service.RaceService;
import com.shilovich.hrbet.service.RatioService;
import com.shilovich.hrbet.service.ServiceFactory;
import com.shilovich.hrbet.exception.ServiceException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Set;

import static com.shilovich.hrbet.controller.CommandParameter.*;

public class RacePageCommandImpl implements Command {
    @Override
    public ServletForward execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, CommandException {
        try {
            String id = req.getParameter(PARAM_RACE_ID);
            Long raceId = Long.parseLong(id);
            HorseService horseService = (HorseService) ServiceFactory.getInstance().getClass(HorseService.class);
            Set<Horse> horses = horseService.showByRace(raceId);
            req.setAttribute(ATTR_RACE_SET, horses);
            RatioService ratioService = (RatioService) ServiceFactory.getInstance().getClass(RatioService.class);
            List<Ratio> ratios = ratioService.findRatio(raceId);
            req.setAttribute(ATTR_RATIO_LIST, ratios);
            return new ServletForward(PAGE_RACE);
        } catch (ServiceException e) {
            throw new CommandException(e.getMessage(), e);
        }
    }
}
