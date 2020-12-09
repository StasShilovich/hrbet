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

/**
 * The interface Dao.
 *
 * @param <T> the type parameter
 * @param <K> the type parameter
 */
public interface Dao<T, K extends Serializable> {
    /**
     * The constant logger.
     */
    Logger logger = LogManager.getLogger(Dao.class);

    /**
     * The constant connection manager.
     */
    ConnectionManager manager = ConnectionManager.getInstance();

    /**
     * Create optional.
     *
     * @param a the a
     * @return the optional
     * @throws DaoException the dao exception
     */
    Optional<T> create(T a) throws DaoException;

    /**
     * Read optional.
     *
     * @param id the id
     * @return the optional
     * @throws DaoException the dao exception
     */
    Optional<T> read(K id) throws DaoException;

    /**
     * Update optional.
     *
     * @param a the a
     * @return the optional
     * @throws DaoException the dao exception
     */
    Optional<T> update(T a) throws DaoException;

    /**
     * Delete boolean.
     *
     * @param id the id
     * @return the boolean
     * @throws DaoException the dao exception
     */
    boolean delete(K id) throws DaoException;

    /**
     * Close connection.
     *
     * @param connection the connection
     */
    default void close(ProxyConnection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                logger.error("Connection close fail!");
            }
        }
    }

    /**
     * Close statement.
     *
     * @param statement the statement
     */
    default void close(Statement statement) {
        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException e) {
                logger.error("Statement close fail!");
            }
        }
    }

    /**
     * Close set.
     *
     * @param set the set
     */
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