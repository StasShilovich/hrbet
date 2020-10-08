package com.shilovich.hrbet.listener;

import com.shilovich.hrbet.dao.connection.pool.CustomConnectionPool;
import com.shilovich.hrbet.dao.connection.pool.MySqlConnectionPool;
import com.shilovich.hrbet.dao.connection.pool.impl.CustomConnectionPoolImpl;
import com.shilovich.hrbet.dao.connection.property.PropertyManager;
import com.shilovich.hrbet.dao.connection.property.impl.PropertyManagerImpl;
import com.shilovich.hrbet.dao.exception.DaoException;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import java.sql.SQLException;

import static com.shilovich.hrbet.dao.connection.property.impl.PropertyManagerImpl.*;

public class DispatcherContextListener implements ServletContextListener {
    private static PropertyManager manager = new PropertyManagerImpl();
    private CustomConnectionPool connectionPool;

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        try {
            Class.forName(manager.getProperty(DRIVER_CLASS_NAME));
            String url = manager.getProperty(URL);
            String user = manager.getProperty(USERNAME);
            String password = manager.getProperty(PASSWORD);
            int initialPoolSize = Integer.parseInt(manager.getProperty(INITIAL_POOL_SIZE));
            int maxPoolSize = Integer.parseInt(manager.getProperty(MAX_OPEN_STATEMENTS));
            int maxTimeout = Integer.parseInt(manager.getProperty(MAX_TIMEOUT));
            connectionPool = CustomConnectionPoolImpl
                    .create(url, user, password, initialPoolSize, maxPoolSize, maxTimeout);
        } catch (Exception e) {
            // TODO: 28.09.2020 logger maybe runtime when pool will be
            System.out.println("Exception due sql connection");
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        try {
            if ((connectionPool != null)) {
                connectionPool.shutdown();
            }
        } catch (SQLException e) {
            // TODO: 08.10.2020 logger
        }
    }
}
