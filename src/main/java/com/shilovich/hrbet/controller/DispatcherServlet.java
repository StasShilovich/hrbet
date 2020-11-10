package com.shilovich.hrbet.controller;

import com.shilovich.hrbet.dao.connection.ConnectionManager;
import com.shilovich.hrbet.exception.CommandException;
import com.shilovich.hrbet.controller.model.CommandEnum;
import com.shilovich.hrbet.controller.model.ServletForward;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

import static com.shilovich.hrbet.controller.CommandParameter.*;


public class DispatcherServlet extends HttpServlet {
    private static final Logger logger = LogManager.getLogger(DispatcherServlet.class);

    private static final ControllerFactory factory = ControllerFactory.getInstance();
    private static final String COMMAND_PARAMETER = "command";
    private ConnectionManager manager;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }

    private void processRequest(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String command = req.getParameter(COMMAND_PARAMETER);
        logger.info(command);
        if (command.isEmpty() || !CommandEnum.isContains(command)) {
            resp.sendRedirect(PAGE_404);
        }
        Command commandAction = factory.getCommand(CommandEnum.getCommand(command));
        if (commandAction != null) {
            ServletForward forward = null;
            try {
                forward = commandAction.execute(req, resp);
            } catch (CommandException e) {
                logger.error(e.getMessage());
                resp.sendRedirect(PAGE_500);
            }
            if (forward == null) {
                logger.error("Null servlet forward");
                resp.sendRedirect(PAGE_404);
            }
            if (forward.isRedirect()) {
                logger.info("Redirect: " + forward.getPage());
                resp.sendRedirect(forward.getPage());
            } else {
                logger.info("Forward: " + forward.getPage());
                getServletContext().getRequestDispatcher(forward.getPage()).forward(req, resp);
            }
        }
    }

    @Override
    public void init() {
        manager = ConnectionManager.getInstance();
        logger.info("Connection Pool started!");
    }

    @Override
    public void destroy() {
        try {
            if (manager != null) {
                manager.shutdown();
                logger.info("Connection Pool shutdown!");
            }
        } catch (SQLException e) {
            logger.error("Failed while pool shutdown");
            logger.error(e.getMessage());
        }
    }
}