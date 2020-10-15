package com.shilovich.hrbet.controller;

import com.shilovich.hrbet.controller.exception.CommandException;
import com.shilovich.hrbet.controller.model.CommandEnum;
import com.shilovich.hrbet.controller.model.ServletForward;
import com.shilovich.hrbet.service.exception.ServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.shilovich.hrbet.constant.CommonConstant.PAGE_INDEX;

public class DispatcherServlet extends HttpServlet {
    private static final Logger logger = LogManager.getLogger(DispatcherServlet.class);

    private static final ControllerFactory factory = ControllerFactory.getInstance();
    private static final String COMMAND_PARAMETER = "command";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String command = req.getParameter(COMMAND_PARAMETER);
        if (command.isEmpty() || !CommandEnum.isContains(command)) {
            resp.sendRedirect(PAGE_INDEX);
        }
        Command commandAction = factory.getCommand(CommandEnum.getCommand(command));
        if (commandAction != null) {
            ServletForward forward = null;
            try {
                forward = commandAction.execute(req);
            } catch (CommandException e) {
                logger.debug(e.getMessage());
                // TODO: 13.10.2020 command exception
            }
            if (forward == null) {
                logger.debug("Null servlet forward");
                // TODO: 12.10.2020 error page
            }
            if (!forward.getRedirect()) {
                logger.debug("Forward: " + forward.getPage());
                getServletContext().getRequestDispatcher(forward.getPage()).forward(req, resp);
            } else {
                logger.debug("Redirect: " + forward.getPage());
                resp.sendRedirect(forward.getPage());
            }
        }
    }
}