package com.shilovich.hrbet.dao.connection.pool.impl;

import com.shilovich.hrbet.dao.connection.pool.CustomConnectionPool;
import com.shilovich.hrbet.dao.connection.pool.MySqlConnectionPool;
import com.shilovich.hrbet.dao.connection.property.PropertyManager;
import com.shilovich.hrbet.dao.connection.property.impl.PropertyManagerImpl;
import com.shilovich.hrbet.dao.exception.DaoException;

import java.sql.Connection;
import java.sql.SQLException;

import static com.shilovich.hrbet.dao.connection.property.impl.PropertyManagerImpl.*;

public class MySqlConnectionPoolImpl implements MySqlConnectionPool {
    private static CustomConnectionPool connectionPool;
    private static PropertyManager manager = new PropertyManagerImpl();

    static {
        try {
            String url = manager.getProperty(URL);
            String user = manager.getProperty(USERNAME);
            String password = manager.getProperty(PASSWORD);
            int initialPoolSize = Integer.parseInt(manager.getProperty(INITIAL_POOL_SIZE));
            int maxPoolSize = Integer.parseInt(manager.getProperty(MAX_OPEN_STATEMENTS));
            int maxTimeout = Integer.parseInt(manager.getProperty(MAX_TIMEOUT));
            connectionPool = CustomConnectionPoolImpl
                    .create(url, user, password, initialPoolSize, maxPoolSize, maxTimeout);
        } catch (SQLException | DaoException e) {
            // TODO: 28.09.2020 logger maybe runtime when pool will be
            System.out.println("Exception due sql connection");
        }
    }

    @Override
    public Connection getConnection() throws DaoException {
        try {
            return connectionPool.getConnection();
        } catch (SQLException e) {
            throw new DaoException("Connection Exception", e);
        }
    }
}