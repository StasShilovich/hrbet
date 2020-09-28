package com.shilovich.hrbet.dao;

import com.shilovich.hrbet.dao.exception.DaoException;
import com.shilovich.hrbet.beans.UserAuthorized;
import com.shilovich.hrbet.beans.UserLogIn;
import com.shilovich.hrbet.beans.UserRegistration;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public interface UserDao {
    UserAuthorized authorization(UserLogIn logInUser) throws DaoException;

    void registration(UserRegistration registrationUser) throws DaoException;

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

    default void close(ResultSet set) throws DaoException {
        if (set != null) {
            try {
                set.close();
            } catch (SQLException e) {
                throw new DaoException("ResultSet close fail!", e);
            }
        }
    }
}
