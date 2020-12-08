package com.shilovich.hrbet.controller.command;

import com.shilovich.hrbet.bean.PermissionEnum;
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
import java.util.Set;

import static com.shilovich.hrbet.bean.PermissionEnum.*;
import static com.shilovich.hrbet.bean.PermissionEnum.CUSTOMER_BASIC;
import static com.shilovich.hrbet.controller.CommandParameter.*;
import static org.apache.commons.lang3.StringUtils.*;

public class RacesPageCommand implements Command {

    @Override
    public Router execute(HttpServletRequest req, HttpServletResponse resp) throws CommandException {
        try {
            String check = req.getParameter(PARAM_RACE_CHECK);
            if (isEmpty(check)) {
                req.setAttribute(ATTR_CHECKBOX, ATTR_CHECKBOX_OFF);
                RaceService raceService = (RaceService) ServiceFactory.getInstance().getClass(RaceService.class);
                int pagesCount = raceService.pageNumberActive();
                req.setAttribute(ATTR_PAGE_NUMBER, pagesCount);
                String page = req.getParameter(PARAM_PAGE);
                List<Race> races = raceService.showAllActive(page);
                req.setAttribute(ATTR_RACES_LIST, races);
            }
            if (isNotEmpty(check) && equalsIgnoreCase(check, CHECKBOX)) {
                req.setAttribute(ATTR_CHECKBOX, ATTR_CHECKBOX_ON);
                RaceService raceService = (RaceService) ServiceFactory.getInstance().getClass(RaceService.class);
                int pagesCount = raceService.pageNumberAll();
                req.setAttribute(ATTR_PAGE_NUMBER, pagesCount);
                String page = req.getParameter(PARAM_PAGE);
                List<Race> races = raceService.showAll(page);
                req.setAttribute(ATTR_RACES_LIST, races);
            }
            return new Router(PAGE_RACES);
        } catch (ServiceException e) {
            throw new CommandException(e.getMessage(), e);
        }
    }

    @Override
    public boolean isAllowed(Set<PermissionEnum> permissions) {
        return permissions.contains(QUEST_BASIC) || permissions.contains(USER_BASIC) ||
                permissions.contains(ADMIN_BASIC) || permissions.contains(CUSTOMER_BASIC);
    }
}