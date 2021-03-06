package com.shilovich.hrbet.controller.command;

import com.shilovich.hrbet.bean.PermissionEnum;
import com.shilovich.hrbet.controller.Command;
import com.shilovich.hrbet.controller.Router;
import com.shilovich.hrbet.exception.CommandException;
import com.shilovich.hrbet.exception.ServiceException;
import com.shilovich.hrbet.service.RaceService;
import com.shilovich.hrbet.service.ServiceFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Set;

import static com.shilovich.hrbet.bean.PermissionEnum.CUSTOMER_BASIC;
import static com.shilovich.hrbet.controller.CommandParameter.PAGE_REDIRECT_INDEX;
import static com.shilovich.hrbet.controller.CommandParameter.PARAM_RACE_ID;
import static org.apache.commons.lang3.StringUtils.isNoneEmpty;

public class DeleteRaceCommand implements Command {
    @Override
    public Router execute(HttpServletRequest req, HttpServletResponse resp) throws CommandException {
        try {
            String raceId = req.getParameter(PARAM_RACE_ID);
            if (isNoneEmpty(raceId)) {
                RaceService raceService = (RaceService) ServiceFactory.getInstance().getClass(RaceService.class);
                raceService.delete(raceId);
            }
            Router router = new Router(PAGE_REDIRECT_INDEX);
            router.redirect();
            return router;
        } catch (ServiceException e) {
            throw new CommandException(e.getMessage(), e);
        }
    }

    @Override
    public boolean isAllowed(Set<PermissionEnum> permissions) {
        return permissions.contains(CUSTOMER_BASIC);
    }
}
