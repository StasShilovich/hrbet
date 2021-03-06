package com.shilovich.hrbet.dao.pool;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.SQLException;

import static com.shilovich.hrbet.dao.pool.PropertyManager.*;

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
            logger.fatal("Exception due sql connection",e);
        }
    }

    public ProxyConnection getConnection() throws SQLException {
        try {
            return connectionPool.getConnection();
        } catch (SQLException e) {
            logger.error("Connection Exception",e);
            throw new SQLException("Connection Exception", e);
        }
    }

    public void shutdown() throws SQLException {
        try {
            connectionPool.shutdown();
        } catch (SQLException e) {
            logger.fatal("Connection pool shutdown fail!",e);
            throw new SQLException("Connection pool shutdown fail!", e);
        }
    }

    public void close(ProxyConnection connection) {
        connectionPool.releaseConnection(connection);
    }

    public static ConnectionManager getInstance() {
        return instance;
    }
}