package com.shilovich.hrbet.dao.impl;

import at.favre.lib.crypto.bcrypt.BCrypt;
import com.shilovich.hrbet.beans.Role;
import com.shilovich.hrbet.beans.UserAuthorized;
import com.shilovich.hrbet.beans.UserLogIn;
import com.shilovich.hrbet.beans.UserRegistration;
import com.shilovich.hrbet.dao.DaoFactory;
import com.shilovich.hrbet.dao.UserDao;
import com.shilovich.hrbet.dao.exception.DaoException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class UserDaoImpl implements UserDao {
    private static final String ADD_USER_SQL =
            "INSERT INTO users (name,surname,password,email,role_id,deleted) VALUES (?,?,?,?,2,0)";
    private static final String USER_PASSWORD_SQL = "SELECT id FROM users WHERE email=?";
    private static final String USER_AUTHORIZED_SQL =
            "SELECT u.id,u.name, u.surname,u.password, r.name FROM users u INNER JOIN roles r ON u.role_id=r.id WHERE u.id=?";

    @Override
    public UserAuthorized authorization(UserLogIn logInUser) throws DaoException {
        UserAuthorized userAuthorized = null;
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet set = null;
        try {
            DaoFactory factory = DaoFactory.getInstance();
            connection = factory.getConnectionPool().getConnection();
            Long userId = findUserId(connection, logInUser.getEmail());
            if (userId == null) {
                return null;
            }
            statement = connection.prepareStatement(USER_AUTHORIZED_SQL);
            statement.setString(1, userId.toString());
            set = statement.executeQuery();
            while (set.next()) {
                Long id = set.getLong("u.id");
                String userName = set.getString("u.name");
                String surname = set.getString("u.surname");
                String password = set.getString("u.password");
                BCrypt.Result verify = BCrypt.verifyer().verify(logInUser.getPassword().toCharArray(), password);
                if (!verify.verified) {
                    return null;
                }
                String roleName = set.getString("r.name");
                Role role = new Role();
                role.setName(roleName);
                userAuthorized = new UserAuthorized(id, userName, surname, role);
            }
            return userAuthorized;
        } catch (SQLException e) {
            throw new DaoException("User authorization failed!", e);
        } finally {
            close(set);
            close(statement);
            close(connection);
        }
    }

    @Override
    public void registration(UserRegistration registrationUser) throws DaoException {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            DaoFactory factory = DaoFactory.getInstance();
            connection = factory.getConnectionPool().getConnection();
            if (findUserId(connection, registrationUser.getEmail()) != null) {
                throw new SQLException("User with this email already exist!");
            }
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

    private Long findUserId(Connection connection, String email) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(USER_PASSWORD_SQL);
        statement.setString(1, email);
        ResultSet set = statement.executeQuery();
        while (set.next()) {
            return set.getLong("id");
        }
        return null;
    }
}