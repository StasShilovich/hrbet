package com.shilovich.hrbet.dao.connection.pool;

import com.shilovich.hrbet.dao.exception.DaoException;

import java.sql.Connection;

public interface TestConnection {
    Connection getConnection() throws DaoException;
}
