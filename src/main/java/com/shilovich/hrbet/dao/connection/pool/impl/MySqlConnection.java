package com.shilovich.hrbet.dao.connection.pool.impl;

import com.shilovich.hrbet.dao.DaoFactory;
import com.shilovich.hrbet.dao.connection.pool.TestConnection;
import com.shilovich.hrbet.dao.connection.property.PropertyManager;
import com.shilovich.hrbet.dao.connection.property.impl.PropertyManagerImpl;
import com.shilovich.hrbet.dao.exception.DaoException;
import org.apache.commons.dbcp.BasicDataSource;

import java.sql.Connection;
import java.sql.SQLException;

import static com.shilovich.hrbet.dao.connection.property.impl.PropertyManagerImpl.*;

public class MySqlConnection implements TestConnection {
    private static BasicDataSource source = new BasicDataSource();
    private static PropertyManager manager = new PropertyManagerImpl();

    static {
        try {
            source.setUrl(manager.getProperty(URL));
            source.setUsername(manager.getProperty(USERNAME));
            source.setPassword(manager.getProperty(PASSWORD));
            source.setMinIdle(Integer.parseInt(manager.getProperty(MIN_IDLE)));
            source.setMaxIdle(Integer.parseInt(manager.getProperty(MAX_IDLE)));
            source.setMaxOpenPreparedStatements(Integer.parseInt(manager.getProperty(MAX_OPEN_PREPARED_STATEMENTS)));
        } catch (DaoException e) {
            // TODO: 28.09.2020 logger maybe runtime when pool will be
            System.out.println("Exception due sql connection");
        }
    }

    @Override
    public Connection getConnection() throws DaoException {
        try {
            return source.getConnection();
        } catch (SQLException e) {
            throw new DaoException("Connection Exception", e);
        }
    }
}