package com.shilovich.hrbet.dao.connection.pool.impl;

import com.shilovich.hrbet.dao.connection.pool.CustomConnectionPool;
import com.shilovich.hrbet.dao.connection.pool.MySqlConnectionPool;
import com.shilovich.hrbet.dao.connection.property.PropertyManager;
import com.shilovich.hrbet.dao.connection.property.impl.PropertyManagerImpl;
import com.shilovich.hrbet.exception.DaoException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;

import static com.shilovich.hrbet.dao.connection.property.impl.PropertyManagerImpl.*;

public class MySqlConnectionPoolImpl implements MySqlConnectionPool {
    private static final Logger logger = LogManager.getLogger(MySqlConnectionPoolImpl.class);
    private static final MySqlConnectionPoolImpl instance = new MySqlConnectionPoolImpl();
    private static CustomConnectionPool connectionPool;
    private static final PropertyManager manager = new PropertyManagerImpl();

    static {
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
            logger.fatal("Exception due sql connection");
        }
    }

    @Override
    public Connection getConnection() throws DaoException {
        try {
            return connectionPool.getConnection();
        } catch (SQLException e) {
            logger.error("Connection Exception");
            throw new DaoException("Connection Exception", e);
        }
    }

    @Override
    public void shutdown() throws DaoException {
        try {
            connectionPool.shutdown();
        } catch (SQLException e) {
            logger.fatal("Connection pool shutdown fail!");
            throw new DaoException("Connection pool shutdown fail!", e);
        }
    }

    public static MySqlConnectionPoolImpl getInstance() {
        return instance;
    }
}