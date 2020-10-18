package com.shilovich.hrbet.dao.impl;

import com.shilovich.hrbet.beans.*;
import com.shilovich.hrbet.dao.DaoFactory;
import com.shilovich.hrbet.dao.AbstractRolePermissionsDao;
import com.shilovich.hrbet.dao.AbstractUserDao;
import com.shilovich.hrbet.dao.connection.pool.MySqlConnectionPool;
import com.shilovich.hrbet.dao.connection.pool.impl.MySqlConnectionPoolImpl;
import com.shilovich.hrbet.dao.exception.DaoException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.ejb.DuplicateKeyException;
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

public class UserDaoImpl extends AbstractUserDao {
    private static final Logger logger = LogManager.getLogger(UserDaoImpl.class);

    private final MySqlConnectionPool pool = MySqlConnectionPoolImpl.getInstance();
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
        ResultSet set = null;
        try {
            connection = pool.getConnection();
            statement = connection.prepareStatement(USER_AUTHORIZED_SQL);
            statement.setString(1, user.getEmail());
            set = statement.executeQuery();
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
            close(set);
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
            statement = connection.prepareStatement(USER_ADD_SQL);
            statement.setString(1, user.getName());
            statement.setString(2, user.getSurname());
            statement.setString(3, user.getPassword());
            statement.setString(4, user.getEmail());
            return user;
        } catch (SQLException e) {
            // TODO: 18.10.2020 ???
            if (e.getErrorCode() == 1062) {
                logger.info("User is already exist!");
                return null;
            }
            logger.error("User registration failed!");
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
}