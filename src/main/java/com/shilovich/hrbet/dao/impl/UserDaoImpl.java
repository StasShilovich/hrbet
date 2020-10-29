package com.shilovich.hrbet.dao.impl;

import com.shilovich.hrbet.bean.*;
import com.shilovich.hrbet.dao.DaoFactory;
import com.shilovich.hrbet.dao.AbstractRolePermissionsDao;
import com.shilovich.hrbet.dao.AbstractUserDao;
import com.shilovich.hrbet.dao.connection.ConnectionManager;
import com.shilovich.hrbet.exception.DaoException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.Set;

import static com.shilovich.hrbet.dao.DaoTableField.*;

public class UserDaoImpl extends AbstractUserDao {
    private static final Logger logger = LogManager.getLogger(UserDaoImpl.class);
    private final ConnectionManager manager = ConnectionManager.getInstance();

    private static final String USER_EXIST_SQL =
            "SELECT 1 FROM users WHERE email=?";
    private static final String USER_ADD_SQL =
            "INSERT INTO users (name,surname,password,email) VALUES (?,?,?,?)";
    private static final String USER_AUTHORIZED_SQL =
            "SELECT u.id,u.name, u.surname,u.password,r.id, r.name FROM users u " +
                    "INNER JOIN roles r ON u.role_id=r.id WHERE u.email=?";

    @Override
    public User authorization(User user) throws DaoException {
        User userDao = null;
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = manager.getConnection();
            statement = connection.prepareStatement(USER_AUTHORIZED_SQL);
            statement.setString(1, user.getEmail());
            ResultSet set = statement.executeQuery();
            while (set.next()) {
                Long id = set.getLong(USER_ID);
                String name = set.getString(USER_NAME);
                String surname = set.getString(USER_SURNAME);
                String password = set.getString(USER_PASSWORD);
                Long roleId = set.getLong(ROLE_ID);
                // TODO: 15.10.2020 role check
                String roleName = set.getString(ROLE_NAME);
                AbstractRolePermissionsDao rolePermissionsDao = (AbstractRolePermissionsDao) DaoFactory.getInstance()
                        .getClass(AbstractRolePermissionsDao.class);
                Set<Permission> permissions = rolePermissionsDao.findAll().getRoles().get(roleId);
                userDao = new User(id, name, surname, password, user.getEmail(),
                        new Role(roleId, roleName, permissions));
            }
            return userDao;
        } catch (SQLException e) {
            logger.error("User authorization failed!");
            throw new DaoException("User authorization failed!", e);
        } finally {
            close(statement);
            close(connection);
        }
    }

    @Override
    public Optional<User> create(User user) throws DaoException {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = manager.getConnection();
            statement = connection.prepareStatement(USER_ADD_SQL);
            statement.setString(1, user.getName());
            statement.setString(2, user.getSurname());
            statement.setString(3, user.getPassword());
            statement.setString(4, user.getEmail());
            statement.executeUpdate();
            return Optional.of(user);
        } catch (SQLException e) {
            logger.error("User registration failed!");
            throw new DaoException("User registration failed!", e);
        } finally {
            close(statement);
            close(connection);
        }
    }

    @Override
    public Optional<User> read(String email) throws DaoException {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = manager.getConnection();
            statement = connection.prepareStatement(USER_EXIST_SQL);
            statement.setString(1, email);
            ResultSet set = statement.executeQuery();
            if (set.next()) {
                return Optional.of(new User());
            } else {
                return Optional.empty();
            }
        } catch (SQLException e) {
            logger.error("User registration failed!");
            throw new DaoException("User registration failed!", e);
        } finally {
            close(statement);
            close(connection);
        }
    }
}