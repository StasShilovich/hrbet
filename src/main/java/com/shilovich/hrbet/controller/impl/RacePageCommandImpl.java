package com.shilovich.hrbet.controller.impl;

import com.shilovich.hrbet.beans.Horse;
import com.shilovich.hrbet.controller.Command;
import com.shilovich.hrbet.controller.exception.CommandException;
import com.shilovich.hrbet.controller.model.ServletForward;
import com.shilovich.hrbet.service.RaceService;
import com.shilovich.hrbet.service.ServiceFactory;
import com.shilovich.hrbet.service.exception.ServiceException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Set;

import static com.shilovich.hrbet.constant.CommonConstant.*;

public class RacePageCommandImpl implements Command {
    @Override
    public ServletForward execute(HttpServletRequest req) throws ServletException, IOException, CommandException {
        try {
            String id = req.getParameter(PARAM_RACE_ID);
            Long raceId = Long.parseLong(id);
            RaceService raceService = (RaceService) ServiceFactory.getInstance().getClass(RaceService.class);
            Set<Horse> horses = raceService.showByRace(raceId);
            req.setAttribute(ATTR_RACE_SET, horses);
            return new ServletForward(PAGE_RACE, false);
        } catch (ServiceException e) {
            throw new CommandException(e.getMessage(), e);
        }
    }
}
