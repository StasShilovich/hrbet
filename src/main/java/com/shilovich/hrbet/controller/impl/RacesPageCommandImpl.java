package com.shilovich.hrbet.controller.impl;

import com.shilovich.hrbet.bean.Race;
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
import java.util.List;

import static com.shilovich.hrbet.controller.CommandParameter.*;
import static com.shilovich.hrbet.service.ServiceParameter.ROW_ON_PAGE;

public class RacesPageCommandImpl implements Command {

    @Override
    public ServletForward execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, CommandException {
        try {
            ServiceFactory factory = ServiceFactory.getInstance();
            RaceService raceService = (RaceService) factory.getClass(RaceService.class);
            int pagesCount = raceService.getRacesPagesCount();
            req.setAttribute(ATTR_PAGE_NUMBER, pagesCount);
            int page = PAGE_OFFSET;
            String parameter = req.getParameter(PARAM_PAGE);
            if (parameter != null) {
                page = (Integer.parseInt(parameter) - 1) * ROW_ON_PAGE;
            }
            List<Race> races = raceService.showAll(ROW_ON_PAGE, page).getList();
            req.setAttribute(PARAM_RACES_LIST, races);
            return new ServletForward(PAGE_RACES);
        } catch (ServiceException e) {
            throw new CommandException(e.getMessage(), e);
        }
    }
}