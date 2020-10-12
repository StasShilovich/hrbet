package com.shilovich.hrbet.dao.impl;

import at.favre.lib.crypto.bcrypt.BCrypt;
import com.shilovich.hrbet.beans.*;
import com.shilovich.hrbet.dao.DaoFactory;
import com.shilovich.hrbet.dao.RolePermissionsDao;
import com.shilovich.hrbet.dao.UserDao;
import com.shilovich.hrbet.dao.connection.pool.MySqlConnectionPool;
import com.shilovich.hrbet.dao.connection.pool.impl.MySqlConnectionPoolImpl;
import com.shilovich.hrbet.dao.exception.DaoException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Set;

import static com.shilovich.hrbet.constant.CommonConstant.ALIAS_ID;
import static com.shilovich.hrbet.constant.CommonConstant.ALIAS_NAME;
import static com.shilovich.hrbet.constant.CommonConstant.ALIAS_PASSWORD;
import static com.shilovich.hrbet.constant.CommonConstant.ALIAS_ROLE_ID;
import static com.shilovich.hrbet.constant.CommonConstant.ALIAS_ROLE_NAME;
import static com.shilovich.hrbet.constant.CommonConstant.ALIAS_SURNAME;

public class UserDaoImpl extends UserDao {
    private MySqlConnectionPool pool = new MySqlConnectionPoolImpl();

    private static final String ADD_USER_SQL =
            "INSERT INTO users u (u.name,u.surname,u.password,u.email,u.role_id,u.deleted) VALUES (?,?,?,?,2,0)";
    private static final String USER_PASSWORD_SQL =
            "SELECT u.id FROM users u WHERE u.email=?";
    private static final String USER_AUTHORIZED_SQL =
            "SELECT u.id,u.name, u.surname,u.password,r.id, r.name FROM users u " +
                    "INNER JOIN roles r ON u.role_id=r.id WHERE u.id=?";

    @Override
    public UserAuthorized authorization(UserLogIn logInUser) throws DaoException {
        UserAuthorized userAuthorized = null;
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet set = null;
        try {
            connection = pool.getConnection();
            Long userId = findUserId(connection, logInUser.getEmail());
            if (userId == null) {
                return null;
            }
            statement = connection.prepareStatement(USER_AUTHORIZED_SQL);
            statement.setString(1, userId.toString());
            set = statement.executeQuery();
            while (set.next()) {
                Long id = set.getLong(ALIAS_ID);
                String userName = set.getString(ALIAS_NAME);
                String surname = set.getString(ALIAS_SURNAME);
                String password = set.getString(ALIAS_PASSWORD);
                BCrypt.Result verify = BCrypt.verifyer().verify(logInUser.getPassword().toCharArray(), password);
                if (!verify.verified) {
                    return null;
                }
                Long roleId = set.getLong(ALIAS_ROLE_ID);
                String roleName = set.getString(ALIAS_ROLE_NAME);
                RolePermissionsDao rolePermissionsDao = (RolePermissionsDao) DaoFactory.getInstance().getClass(RolePermissionsDao.class);
                Set<Permission> permissions = rolePermissionsDao.findAll().getRoles().get(roleId);
                userAuthorized = new UserAuthorized(id, userName, surname,
                        new Role(roleId, roleName, permissions));
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
            connection = pool.getConnection();
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

    @Override
    public User create(User user) throws DaoException {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = pool.getConnection();
            if (findUserId(connection, user.getEmail()) != null) {
                return null;
            }
            statement = connection.prepareStatement(ADD_USER_SQL);
            statement.setString(1, user.getName());
            statement.setString(2, user.getSurname());
            // TODO: 12.10.2020 hash in service
//            String password = user.getPassword();
//            String hashPassword = BCrypt.withDefaults().hashToString(12, password.toCharArray());
            statement.setString(3, user.getPassword());
            statement.setString(4, user.getEmail());
            int rows = statement.executeUpdate();
            if (rows == 0) {
                throw new SQLException("Register user. No rows affected");
            }
            return user;
        } catch (SQLException e) {
            throw new DaoException("User registration failed!", e);
        } finally {
            close(statement);
            close(connection);
        }
    }

    @Override
    public User read(Long id) throws DaoException {
        return null;
    }

    private Long findUserId(Connection connection, String email) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(USER_PASSWORD_SQL);
        statement.setString(1, email);
        ResultSet set = statement.executeQuery();
        while (set.next()) {
            return set.getLong(ALIAS_ID);
        }
        return null;
    }
}