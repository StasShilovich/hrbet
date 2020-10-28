package com.shilovich.hrbet.dao.connection.pool.impl;

import com.shilovich.hrbet.dao.connection.pool.CustomConnectionPool;
import com.shilovich.hrbet.exception.PoolOverflowException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class CustomConnectionPoolImpl implements CustomConnectionPool {
    private static final Logger logger = LogManager.getLogger(CustomConnectionPoolImpl.class);

    private String url;
    private String user;
    private String password;
    private int initialPoolSize;
    private int maxPoolSize;
    private int maxTimeout;
    private BlockingQueue<Connection> connectionPool;
    private BlockingQueue<Connection> usedConnections = new LinkedBlockingQueue<>();

    public static CustomConnectionPoolImpl create(
            String url, String user, String password,
            int initialPoolSize, int maxPoolSize, int maxTimeout) throws SQLException {
        BlockingQueue<Connection> pool = new LinkedBlockingQueue<>(initialPoolSize);
        for (int i = 0; i < initialPoolSize; i++) {
            pool.add(createConnection(url, user, password));
        }
        return new CustomConnectionPoolImpl(url, user, password, initialPoolSize, maxPoolSize, maxTimeout, pool);
    }

    private static Connection createConnection(String url, String user, String password) throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }

    public CustomConnectionPoolImpl(String url, String user, String password, int initialPoolSize, int maxPoolSize, int maxTimeout, BlockingQueue<Connection> connectionPool) {
        this.url = url;
        this.user = user;
        this.password = password;
        this.initialPoolSize = initialPoolSize;
        this.maxPoolSize = maxPoolSize;
        this.maxTimeout = maxTimeout;
        this.connectionPool = connectionPool;
    }

    @Override
    public Connection getConnection() throws SQLException {
        if (connectionPool.isEmpty()) {
            if (usedConnections.size() < maxPoolSize) {
                connectionPool.add(createConnection(url, user, password));
            } else {
                logger.fatal("Maximum pool size reached, no available connections!");
                throw new PoolOverflowException("Maximum pool size reached, no available connections!");
            }
        }
        Connection connection = connectionPool.remove();
        if (!connection.isValid(maxTimeout)) {
            connection = createConnection(url, user, password);
        }
        usedConnections.add(connection);
        return connection;
    }

    @Override
    public boolean releaseConnection(Connection connection) {
        connectionPool.add(connection);
        return usedConnections.remove(connection);
    }

    @Override
    public void shutdown() throws SQLException {
        usedConnections.forEach(this::releaseConnection);
        for (Connection connection : connectionPool) {
            connection.close();
        }
        connectionPool.clear();
    }

    public int getSize() {
        return connectionPool.size() + usedConnections.size();
    }
}