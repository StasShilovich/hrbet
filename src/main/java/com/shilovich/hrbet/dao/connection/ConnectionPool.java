package com.shilovich.hrbet.dao.connection;

import com.shilovich.hrbet.exception.PoolOverflowException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

class ConnectionPool {
    private static final Logger logger = LogManager.getLogger(ConnectionPool.class);

    private final String url;
    private final String user;
    private final String password;
    private final int initialPoolSize;
    private final int maxPoolSize;
    private final int maxTimeout;
    private BlockingQueue<ProxyConnection> connectionPool;
    private BlockingQueue<ProxyConnection> usedConnections = new LinkedBlockingQueue<>();

    public static ConnectionPool create(
            String url, String user, String password,
            int initialPoolSize, int maxPoolSize, int maxTimeout) throws SQLException {
        BlockingQueue<ProxyConnection> pool = new LinkedBlockingQueue<>(initialPoolSize);
        for (int i = 0; i < initialPoolSize; i++) {
            pool.add(createConnection(url, user, password));
        }
        return new ConnectionPool(url, user, password, initialPoolSize, maxPoolSize, maxTimeout, pool);
    }

    private static ProxyConnection createConnection(String url, String user, String password) throws SQLException {
        return new ProxyConnection(DriverManager.getConnection(url, user, password));
    }

    public ConnectionPool(
            String url, String user, String password, int initialPoolSize, int maxPoolSize,
            int maxTimeout, BlockingQueue<ProxyConnection> connectionPool) {
        this.url = url;
        this.user = user;
        this.password = password;
        this.initialPoolSize = initialPoolSize;
        this.maxPoolSize = maxPoolSize;
        this.maxTimeout = maxTimeout;
        this.connectionPool = connectionPool;
    }

    public ProxyConnection getConnection() throws SQLException {
        if (connectionPool.isEmpty()) {
            if (usedConnections.size() < maxPoolSize) {
                connectionPool.add(createConnection(url, user, password));
            } else {
                logger.fatal("Maximum pool size reached, no available connections!");
                throw new PoolOverflowException("Maximum pool size reached, no available connections!");
            }
        }
        ProxyConnection connection = connectionPool.remove();
        if (!connection.isValid(maxTimeout)) {
            connection = createConnection(url, user, password);
        }
        usedConnections.add(connection);
        return connection;
    }

    public boolean releaseConnection(ProxyConnection connection) {
        connectionPool.add(connection);
        return usedConnections.remove(connection);
    }

    public void shutdown() throws SQLException {
        usedConnections.forEach(this::releaseConnection);
        for (Connection connection : connectionPool) {
            connection.close();
        }
        connectionPool.clear();
    }
}