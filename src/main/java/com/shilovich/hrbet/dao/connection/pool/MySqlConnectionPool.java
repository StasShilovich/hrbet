package com.shilovich.hrbet.dao.connection.pool;

import com.shilovich.hrbet.exception.DaoException;

import java.sql.Connection;

public interface MySqlConnectionPool {
    Connection getConnection() throws DaoException;

    void shutdown() throws DaoException;
}
