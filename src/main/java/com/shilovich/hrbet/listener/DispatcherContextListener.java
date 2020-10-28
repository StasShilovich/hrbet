package com.shilovich.hrbet.listener;

import com.shilovich.hrbet.dao.connection.pool.MySqlConnectionPool;
import com.shilovich.hrbet.dao.connection.pool.impl.MySqlConnectionPoolImpl;
import com.shilovich.hrbet.exception.DaoException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class DispatcherContextListener implements ServletContextListener {
    private static final Logger logger = LogManager.getLogger(DispatcherContextListener.class);
    private MySqlConnectionPool pool;

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        pool = MySqlConnectionPoolImpl.getInstance();
        logger.info("Connection Pool started!");
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        try {
            if (pool != null) {
                pool.shutdown();
                logger.info("Connection Pool shutdown!");
            }
        } catch (DaoException e) {
            logger.error("Failed while pool shutdown");
            logger.error(e.getMessage());
        }
    }
}
