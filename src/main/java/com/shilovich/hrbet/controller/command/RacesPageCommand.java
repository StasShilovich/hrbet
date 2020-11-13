package com.shilovich.hrbet.controller.command;

import com.shilovich.hrbet.bean.Race;
import com.shilovich.hrbet.controller.Command;
import com.shilovich.hrbet.exception.CommandException;
import com.shilovich.hrbet.controller.Router;
import com.shilovich.hrbet.service.RaceService;
import com.shilovich.hrbet.service.ServiceFactory;
import com.shilovich.hrbet.exception.ServiceException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

import static com.shilovich.hrbet.controller.CommandParameter.*;
import static com.shilovich.hrbet.service.ServiceParameter.RACES_ON_PAGE;

public class RacesPageCommand implements Command {

    @Override
    public Router execute(HttpServletRequest req, HttpServletResponse resp) throws CommandException {
        try {
            String check = req.getParameter(PARAM_RACE_CHECK);
            if (check == null || check.isEmpty()) {
                req.setAttribute("switch","off");
                RaceService raceService = (RaceService) ServiceFactory.getInstance().getClass(RaceService.class);
                int pagesCount = raceService.pageNumberActive();
                req.setAttribute(ATTR_PAGE_NUMBER, pagesCount);
                int page = PAGE_OFFSET;
                String parameter = req.getParameter(PARAM_PAGE);
                if (parameter != null) {
                    page = (Integer.parseInt(parameter) - 1) * RACES_ON_PAGE;
                }
                List<Race> races = raceService.showAllActive(RACES_ON_PAGE, page).getList();
                req.setAttribute(ATTR_RACES_LIST, races);
            }
            if (check != null && check.equals(CHECKBOX)) {
                req.setAttribute("switch","on");
                RaceService raceService = (RaceService) ServiceFactory.getInstance().getClass(RaceService.class);
                int pagesCount = raceService.pageNumberAll();
                req.setAttribute(ATTR_PAGE_NUMBER, pagesCount);
                int page = PAGE_OFFSET;
                String parameter = req.getParameter(PARAM_PAGE);
                if (parameter != null) {
                    page = (Integer.parseInt(parameter) - 1) * RACES_ON_PAGE;
                }
                List<Race> races = raceService.showAll(RACES_ON_PAGE, page).getList();
                req.setAttribute(ATTR_RACES_LIST, races);
            }
            return new Router(PAGE_RACES);
        } catch (ServiceException e) {
            throw new CommandException(e.getMessage(), e);
        }
    }
}