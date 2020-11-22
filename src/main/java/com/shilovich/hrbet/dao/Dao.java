package com.shilovich.hrbet.dao;

import com.shilovich.hrbet.dao.pool.ConnectionManager;
import com.shilovich.hrbet.dao.pool.ProxyConnection;
import com.shilovich.hrbet.exception.DaoException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;

public interface Dao<T, K extends Serializable> {
    Logger logger = LogManager.getLogger(Dao.class);

    ConnectionManager manager = ConnectionManager.getInstance();

    Optional<T> create(T a) throws DaoException;

    Optional<T> read(K id) throws DaoException;

    Optional<T> update(T a) throws DaoException;

    boolean delete(K id) throws DaoException;

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

    default void rollback(ProxyConnection connection){
        if (connection != null) {
            try {
                connection.rollback();
                logger.error("Connection rollback!");
            } catch (SQLException ex) {
                logger.error("Error while rollback!");
            }
            try {
                connection.setAutoCommit(true);
                logger.error("Set auto commit true!");
            } catch (SQLException ex) {
                logger.error("Error while set auto commit!");
            }
        }
    }
}
