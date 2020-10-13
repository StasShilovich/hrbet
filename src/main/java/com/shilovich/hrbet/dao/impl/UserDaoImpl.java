package com.shilovich.hrbet.dao.impl;

import at.favre.lib.crypto.bcrypt.BCrypt;
import com.shilovich.hrbet.beans.*;
import com.shilovich.hrbet.dao.DaoFactory;
import com.shilovich.hrbet.dao.RolePermissionsDao;
import com.shilovich.hrbet.dao.UserDao;
import com.shilovich.hrbet.dao.connection.pool.MySqlConnectionPool;
import com.shilovich.hrbet.dao.connection.pool.impl.MySqlConnectionPoolImpl;
import com.shilovich.hrbet.dao.exception.DaoException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Set;

import static com.shilovich.hrbet.constant.CommonConstant.USER_ID;
import static com.shilovich.hrbet.constant.CommonConstant.USER_NAME;
import static com.shilovich.hrbet.constant.CommonConstant.USER_PASSWORD;
import static com.shilovich.hrbet.constant.CommonConstant.ROLE_ID;
import static com.shilovich.hrbet.constant.CommonConstant.ROLE_NAME;
import static com.shilovich.hrbet.constant.CommonConstant.USER_SURNAME;

public class UserDaoImpl extends UserDao {
    private static final Logger logger = LogManager.getLogger(UserDaoImpl.class);

    private final MySqlConnectionPool pool = MySqlConnectionPoolImpl.getInstance();
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
                Long id = set.getLong(USER_ID);
                String userName = set.getString(USER_NAME);
                String surname = set.getString(USER_SURNAME);
                String password = set.getString(USER_PASSWORD);
                BCrypt.Result verify = BCrypt.verifyer().verify(logInUser.getPassword().toCharArray(), password);
                if (!verify.verified) {
                    return null;
                }
                Long roleId = set.getLong(ROLE_ID);
                String roleName = set.getString(ROLE_NAME);
                RolePermissionsDao rolePermissionsDao = (RolePermissionsDao) DaoFactory.getInstance().getClass(RolePermissionsDao.class);
                Set<Permission> permissions = rolePermissionsDao.findAll().getRoles().get(roleId);
                userAuthorized = new UserAuthorized(id, userName, surname,
                        new Role(roleId, roleName, permissions));
            }
            return userAuthorized;
        } catch (SQLException e) {
            logger.debug("User authorization failed!");
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
                logger.debug("User with email" + registrationUser.getEmail() + "already exist!");
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
                logger.debug("Registration method. Rows do not affected!");
                throw new SQLException("Register user. No rows affected");
            }
        } catch (SQLException e) {
            logger.debug("User registration failed!");
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
                logger.debug("Registration method. Rows do not affected!");
                throw new SQLException("Register user. No rows affected");
            }
            return user;
        } catch (SQLException e) {
            logger.debug("User registration failed!");
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
            return set.getLong(USER_ID);
        }
        return null;
    }
}