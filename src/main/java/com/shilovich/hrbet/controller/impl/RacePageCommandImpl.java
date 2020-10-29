package com.shilovich.hrbet.controller.impl;

import com.shilovich.hrbet.bean.Horse;
import com.shilovich.hrbet.controller.Command;
import com.shilovich.hrbet.exception.CommandException;
import com.shilovich.hrbet.controller.model.ServletForward;
import com.shilovich.hrbet.service.RaceService;
import com.shilovich.hrbet.service.ServiceFactory;
import com.shilovich.hrbet.exception.ServiceException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Set;

import static com.shilovich.hrbet.controller.CommandParameter.*;

public class RacePageCommandImpl implements Command {
    @Override
    public ServletForward execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, CommandException {
        try {
            String id = req.getParameter(PARAM_RACE_ID);
            Long raceId = Long.parseLong(id);
            RaceService raceService = (RaceService) ServiceFactory.getInstance().getClass(RaceService.class);
            Set<Horse> horses = raceService.showByRace(raceId);
            req.setAttribute(ATTR_RACE_SET, horses);
            return new ServletForward(PAGE_RACE);
        } catch (ServiceException e) {
            throw new CommandException(e.getMessage(), e);
        }
    }
}
