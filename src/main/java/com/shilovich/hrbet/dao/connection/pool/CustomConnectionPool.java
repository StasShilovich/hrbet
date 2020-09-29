package com.shilovich.hrbet.dao.connection.pool;

import java.sql.Connection;
import java.sql.SQLException;

public interface CustomConnectionPool {
    Connection getConnection() throws SQLException;

    boolean releaseConnection(Connection connection);

    void shutdown() throws SQLException;

}
