package com.shilovich.hrbet.dao;

import com.shilovich.hrbet.dao.connection.ProxyConnection;
import com.shilovich.hrbet.exception.DaoException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;

public interface Dao<A, T extends Serializable> {
    Logger logger = LogManager.getLogger(Dao.class);
    Optional<A> create(A a) throws DaoException;

    Optional<A> read(T id) throws DaoException;

    Optional<A> update(A a) throws DaoException;

    boolean delete(A a) throws DaoException;

    default void close(ProxyConnection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                logger.error("Connection close fail!");
            }
        }
    }

    default void close(Statement statement) {
        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException e) {
                logger.error("Statement close fail!");
            }
        }
    }
    default void close(ResultSet set) {
        if (set != null) {
            try {
                set.close();
            } catch (SQLException e) {
                logger.error("Set close fail!");
            }
        }
    }
}
