package com.shilovich.hrbet.controller.impl;

import com.shilovich.hrbet.beans.Race;
import com.shilovich.hrbet.controller.Command;
import com.shilovich.hrbet.controller.exception.CommandException;
import com.shilovich.hrbet.controller.model.ServletForward;
import com.shilovich.hrbet.service.RaceService;
import com.shilovich.hrbet.service.ServiceFactory;
import com.shilovich.hrbet.service.exception.ServiceException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import static com.shilovich.hrbet.constant.CommonConstant.PAGE_RACES;
import static com.shilovich.hrbet.constant.CommonConstant.PARAM_RACES_LIST;

public class RacesPageCommandImpl implements Command {

    @Override
    public ServletForward execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, CommandException {
        try {
            ServiceFactory factory = ServiceFactory.getInstance();
            RaceService raceService = (RaceService) factory.getClass(RaceService.class);
            List<Race> races = raceService.showAll();
            req.setAttribute(PARAM_RACES_LIST, races);
            return new ServletForward(PAGE_RACES, false);
        } catch (ServiceException e) {
            throw new CommandException(e.getMessage(), e);
        }
    }
}