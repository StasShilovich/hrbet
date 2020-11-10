package com.shilovich.hrbet.controller.impl;

import com.shilovich.hrbet.bean.Horse;
import com.shilovich.hrbet.controller.Command;
import com.shilovich.hrbet.controller.model.ServletForward;
import com.shilovich.hrbet.exception.CommandException;
import com.shilovich.hrbet.exception.ServiceException;
import com.shilovich.hrbet.service.HorseService;
import com.shilovich.hrbet.service.RaceService;
import com.shilovich.hrbet.service.ServiceFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.shilovich.hrbet.controller.CommandParameter.*;

public class AddRaceCommandImpl implements Command {
    @Override
    public ServletForward execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, CommandException {
        try {
            HorseService horseService = (HorseService) ServiceFactory.getInstance().getClass(HorseService.class);
            List<Horse> horses = horseService.findAll();
            req.setAttribute(ATTR_HORSE_LIST, horses);
            req.setAttribute(ATTR_HORSE_MAP,new HashMap<Integer,Long>());
            String location = req.getParameter(PARAM_LOCATION);
            String date = req.getParameter(PARAM_DATE);
            Map<Integer,Long> horseMap = (Map<Integer,Long>)req.getAttribute(ATTR_HORSE_MAP);
            System.out.println(horseMap.size());
            System.out.println(location);
            System.out.println(date);


            RaceService raceService = (RaceService) ServiceFactory.getInstance().getClass(RaceService.class);
            raceService.getRacesPagesCount();
            return new ServletForward(PAGE_ADD_RACE);
        } catch (ServiceException e) {
            throw new CommandException(e.getMessage(), e);
        }
    }
}
