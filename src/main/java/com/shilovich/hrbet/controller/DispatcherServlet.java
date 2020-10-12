package com.shilovich.hrbet.controller;

import com.shilovich.hrbet.controller.command.ServletForward;
import com.shilovich.hrbet.controller.command.impl.LogInCommandImpl;
import com.shilovich.hrbet.controller.command.impl.LogOutCommandImpl;
import com.shilovich.hrbet.controller.command.impl.RacesPageCommandImpl;
import com.shilovich.hrbet.controller.command.model.CommandEnum;
import com.shilovich.hrbet.service.exception.ServiceException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class DispatcherServlet extends HttpServlet {
    private static final Map<CommandEnum, Command> commands = new HashMap<>();
    private static final String COMMAND_PARAMETER = "command";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String command = req.getParameter(COMMAND_PARAMETER);
        // TODO: 03.10.2020 check if command is null
        Command commandAction = commands.get(CommandEnum.getCommand(command));
        if (commandAction != null) {
            ServletForward forward = null;
            try {
                forward = commandAction.execute(req, resp);
            } catch (ServiceException e) {
                // TODO: 03.10.2020
            }
            if (forward == null) {
                // TODO: 12.10.2020 error page
            }
            if (!forward.getRedirect()) {
                getServletContext().getRequestDispatcher(forward.getPage()).forward(req, resp);
            } else {
                resp.sendRedirect(forward.getPage());
            }
        }
    }

    @Override
    public void destroy() {
        super.destroy();
    }

    @Override
    public void init() throws ServletException {
        commands.put(CommandEnum.RACES, new RacesPageCommandImpl());
        commands.put(CommandEnum.LOGIN, new LogInCommandImpl());
        commands.put(CommandEnum.LOGOUT, new LogOutCommandImpl());
    }
}