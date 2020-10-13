package com.shilovich.hrbet.listener;

import com.shilovich.hrbet.dao.connection.pool.MySqlConnectionPool;
import com.shilovich.hrbet.dao.connection.pool.impl.MySqlConnectionPoolImpl;
import com.shilovich.hrbet.dao.exception.DaoException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class DispatcherContextListener implements ServletContextListener {
    private static final Logger logger = LogManager.getLogger(DispatcherContextListener.class);
    private MySqlConnectionPool pool;

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        logger.debug("Connection Pool started!");
        pool = MySqlConnectionPoolImpl.getInstance();
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        try {
            if (pool != null) {
                pool.shutdown();
                logger.debug("Connection Pool shutdown!");
            }
        } catch (DaoException e) {
            logger.debug("Failed while pool shutdown");
            logger.debug(e.getMessage());
        }
    }
}
