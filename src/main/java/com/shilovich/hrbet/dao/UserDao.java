package com.shilovich.hrbet.dao;

import com.shilovich.hrbet.beans.User;
import com.shilovich.hrbet.beans.UserAuthorized;
import com.shilovich.hrbet.beans.UserLogIn;
import com.shilovich.hrbet.beans.UserRegistration;
import com.shilovich.hrbet.dao.exception.DaoException;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public abstract class UserDao implements DaoCRUD<User, Long> {
    public abstract UserAuthorized authorization(UserLogIn logInUser) throws DaoException;

    public abstract void registration(UserRegistration registrationUser) throws DaoException;

    @Override
    public abstract User create(User user) throws DaoException;

    @Override
    public abstract User read(Long id) throws DaoException;

    @Override
    public final User update(User user) throws UnsupportedOperationException {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean delete(User user) throws UnsupportedOperationException {
        throw new UnsupportedOperationException();
    }

    public void close(Connection connection) throws DaoException {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                throw new DaoException("Connection close fail!", e);
            }
        }
    }

    public void close(Statement statement) throws DaoException {
        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException e) {
                throw new DaoException("Statement close fail!", e);
            }
        }
    }

    public void close(ResultSet set) throws DaoException {
        if (set != null) {
            try {
                set.close();
            } catch (SQLException e) {
                throw new DaoException("ResultSet close fail!", e);
            }
        }
    }
}
