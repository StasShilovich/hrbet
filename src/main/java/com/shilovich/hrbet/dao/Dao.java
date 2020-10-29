package com.shilovich.hrbet.dao;
import com.shilovich.hrbet.exception.DaoException;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;

public interface Dao<A, T extends Serializable> {
    Optional<A> create(A a) throws DaoException;

    Optional<A> read(T id) throws DaoException;

    Optional<A> update(A a) throws DaoException;

    boolean delete(A a) throws DaoException;

    default void close(Connection connection) throws DaoException {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                throw new DaoException("Connection close fail!", e);
            }
        }
    }

    default void close(Statement statement) throws DaoException {
        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException e) {
                throw new DaoException("Statement close fail!", e);
            }
        }
    }
}
