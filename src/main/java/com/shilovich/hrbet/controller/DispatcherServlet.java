package com.shilovich.hrbet.controller;

import com.shilovich.hrbet.dao.pool.ConnectionManager;
import com.shilovich.hrbet.exception.CommandException;
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

    private static final CommandMap factory = CommandMap.getInstance();
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
        if (command.isEmpty() || !CommandType.isContains(command)) {
            resp.sendRedirect(PAGE_404);
        }
        Command commandAction = factory.getCommand(CommandType.getCommand(command));
        if (commandAction != null) {
            Router router = null;
            try {
                router = commandAction.execute(req, resp);
            } catch (CommandException e) {
                logger.error(e.getMessage(),e);
                resp.sendRedirect(PAGE_500);
            }
            if (router == null) {
                logger.error("Null servlet forward");
                resp.sendRedirect(PAGE_404);
            }
            if (router.isRedirect()) {
                logger.info("Redirect: " + router.getPage());
                resp.sendRedirect(router.getPage());
            } else {
                logger.info("Forward: " + router.getPage());
                getServletContext().getRequestDispatcher(router.getPage()).forward(req, resp);
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