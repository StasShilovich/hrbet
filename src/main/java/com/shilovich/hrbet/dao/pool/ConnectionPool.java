package com.shilovich.hrbet.dao.pool;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

class ConnectionPool {
    private static final Logger logger = LogManager.getLogger(ConnectionPool.class);

    private final String url;
    private final String user;
    private final String password;
    private final int maxPoolSize;
    private final int maxTimeout;
    private BlockingQueue<ProxyConnection> connectionPool;
    private BlockingQueue<ProxyConnection> usedConnections = new LinkedBlockingQueue<>();

    public static ConnectionPool create(
            String url, String user, String password,
            int initialPoolSize, int maxPoolSize, int maxTimeout) throws SQLException {
        BlockingQueue<ProxyConnection> pool = new LinkedBlockingQueue<>(maxPoolSize);
        for (int i = 0; i < initialPoolSize; i++) {
            pool.add(createConnection(url, user, password));
        }
        return new ConnectionPool(url, user, password, maxPoolSize, maxTimeout, pool);
    }

    private static ProxyConnection createConnection(String url, String user, String password) throws SQLException {
        return new ProxyConnection(DriverManager.getConnection(url, user, password));
    }

    private ConnectionPool(
            String url, String user, String password, int maxPoolSize,
            int maxTimeout, BlockingQueue<ProxyConnection> connectionPool) {
        this.url = url;
        this.user = user;
        this.password = password;
        this.maxPoolSize = maxPoolSize;
        this.maxTimeout = maxTimeout;
        this.connectionPool = connectionPool;
    }

    public ProxyConnection getConnection() throws SQLException {
        if (usedConnections.size() + connectionPool.size() < maxPoolSize && connectionPool.isEmpty()) {
            connectionPool.add(createConnection(url, user, password));
        }
        ProxyConnection connection = null;
        try {
            connection = connectionPool.take();
        } catch (InterruptedException e) {
            logger.error(e.getMessage(), e);
        }
        if (!connection.isValid(maxTimeout)) {
            throw new SQLException("Connection is closed!");
        }
        usedConnections.add(connection);
        return connection;
    }

    public void releaseConnection(ProxyConnection connection) {
        if (usedConnections.remove(connection)) {
            connectionPool.add(connection);
        }
    }

    public void shutdown() throws SQLException {
        try {
            for (ProxyConnection connection : usedConnections) {
                connection.reallyClose();
            }
            for (ProxyConnection connection : connectionPool) {
                connection.reallyClose();
            }
        } catch (IllegalStateException e) {
            logger.error(e.getMessage(), e);
        }
    }
}