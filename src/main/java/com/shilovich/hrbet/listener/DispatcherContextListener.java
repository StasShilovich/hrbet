package com.shilovich.hrbet.listener;

import com.shilovich.hrbet.dao.connection.ConnectionManager;
import com.shilovich.hrbet.exception.DaoException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class DispatcherContextListener implements ServletContextListener {
    private static final Logger logger = LogManager.getLogger(DispatcherContextListener.class);
    private ConnectionManager manager;

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        manager = ConnectionManager.getInstance();
        logger.info("Connection Pool started!");
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        try {
            if (manager != null) {
                manager.shutdown();
                logger.info("Connection Pool shutdown!");
            }
        } catch (DaoException e) {
            logger.error("Failed while pool shutdown");
            logger.error(e.getMessage());
        }
    }
}
