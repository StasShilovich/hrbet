package com.shilovich.hrbet.controller.command.impl;

import com.shilovich.hrbet.beans.Race;
import com.shilovich.hrbet.controller.Command;
import com.shilovich.hrbet.controller.command.ServletForward;
import com.shilovich.hrbet.service.RaceService;
import com.shilovich.hrbet.service.ServiceFactory;
import com.shilovich.hrbet.service.exception.ServiceException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class RacesPageCommandImpl implements Command {
    private static final String RACES_PAGE = "/WEB-INF/pages/races.jsp";
    private static final String RACES_LIST_PARAMETER = "races";

    @Override
    public ServletForward execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, ServiceException {
        ServiceFactory factory = ServiceFactory.getInstance();
        RaceService raceService = (RaceService) factory.getClass(RaceService.class);
        List<Race> races = raceService.showAll();
        req.setAttribute(RACES_LIST_PARAMETER, races);
        return new ServletForward(RACES_PAGE, false);
    }
}
