package com.shilovich.hrbet.dao.impl;

import at.favre.lib.crypto.bcrypt.BCrypt;
import com.shilovich.hrbet.beans.UserAuthorized;
import com.shilovich.hrbet.beans.UserLogIn;
import com.shilovich.hrbet.beans.UserRegistration;
import com.shilovich.hrbet.dao.DaoFactory;
import com.shilovich.hrbet.dao.UserDao;
import com.shilovich.hrbet.dao.exception.DaoException;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UserDaoImpl implements UserDao {
    private static final String ADD_USER_SQL = "INSERT INTO `hrbet`.`users` " +
            "(`name`, `surname`, `password`, `email`, `role_id`, `deleted`) VALUES (?, ?, ?, ?,'2','0')";

    @Override
    public UserAuthorized authorization(UserLogIn logInUser) throws DaoException {
        return null;
    }

    @Override
    public void registration(UserRegistration registrationUser) throws DaoException {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            DaoFactory factory = DaoFactory.getInstance();
            connection = factory.getConnectionPool().getConnection();
            statement = connection.prepareStatement(ADD_USER_SQL);
            statement.setString(1, registrationUser.getName());
            statement.setString(2, registrationUser.getSurname());
            String password = registrationUser.getPassword();
            String hashPassword = BCrypt.withDefaults().hashToString(12, password.toCharArray());
            statement.setString(3, hashPassword);
            statement.setString(4, registrationUser.getEmail());
            int rows = statement.executeUpdate();
            if (rows == 0) {
                throw new SQLException("Register user. No rows affected");
            }
        } catch (SQLException e) {
            throw new DaoException("User registration failed!", e);
        } finally {
            close(statement);
            close(connection);
        }
    }
}
