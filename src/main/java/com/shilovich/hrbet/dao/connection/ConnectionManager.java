package com.shilovich.hrbet.dao.connection;

import com.shilovich.hrbet.exception.DaoException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.SQLException;

import static com.shilovich.hrbet.dao.connection.PropertyManager.*;

public class ConnectionManager {
    private static final Logger logger = LogManager.getLogger(ConnectionManager.class);
    private static final ConnectionManager instance = new ConnectionManager();
    private static ConnectionPool connectionPool;
    private static final PropertyManager manager = new PropertyManager();

    static {
        try {
            Class.forName(manager.getProperty(DRIVER_CLASS_NAME));
            String url = manager.getProperty(URL);
            String user = manager.getProperty(USERNAME);
            String password = manager.getProperty(PASSWORD);
            int initialPoolSize = Integer.parseInt(manager.getProperty(INITIAL_POOL_SIZE));
            int maxPoolSize = Integer.parseInt(manager.getProperty(MAX_OPEN_STATEMENTS));
            int maxTimeout = Integer.parseInt(manager.getProperty(MAX_TIMEOUT));
            connectionPool = ConnectionPool
                    .create(url, user, password, initialPoolSize, maxPoolSize, maxTimeout);
        } catch (Exception e) {
            // TODO: 28.09.2020 logger maybe runtime when pool will be
            logger.fatal("Exception due sql connection");
        }
    }

    public ProxyConnection getConnection() throws DaoException {
        try {
            return connectionPool.getConnection();
        } catch (SQLException e) {
            logger.error("Connection Exception");
            throw new DaoException("Connection Exception", e);
        }
    }

    public void shutdown() throws DaoException {
        try {
            connectionPool.shutdown();
        } catch (SQLException e) {
            logger.fatal("Connection pool shutdown fail!");
            throw new DaoException("Connection pool shutdown fail!", e);
        }
    }

    public void close(ProxyConnection connection) {
        if (!connectionPool.releaseConnection(connection)) {
            logger.fatal("Connection close fail!");
        }
    }


    public static ConnectionManager getInstance() {
        return instance;
    }
}