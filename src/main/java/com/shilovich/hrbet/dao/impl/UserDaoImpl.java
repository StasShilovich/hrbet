package com.shilovich.hrbet.dao.impl;

import com.shilovich.hrbet.bean.*;
import com.shilovich.hrbet.dao.UserDao;
import com.shilovich.hrbet.dao.pool.ProxyConnection;
import com.shilovich.hrbet.exception.DaoException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.shilovich.hrbet.dao.DaoTableField.*;

public class UserDaoImpl extends UserDao {
    private static final Logger logger = LogManager.getLogger(UserDaoImpl.class);

    private static UserDao instance;
    private static final String USER_EXIST_SQL =
            "SELECT 1 FROM users WHERE email=?";
    private static final String FIND_ALL_USER_SQL =
            "SELECT u.id,u.name, u.surname,u.email,u.cash,r.id FROM users u " +
                    "INNER JOIN roles r ON u.role_id=r.id LIMIT ? OFFSET ?";
    private static final String COUNT_USERS_SQL = "SELECT count(id) FROM users";
    private static final String USER_ADD_SQL =
            "INSERT INTO users (name,surname,password,email) VALUES (?,?,?,?)";
    private static final String USER_AUTHORIZED_SQL =
            "SELECT u.id,u.name, u.surname,u.password,u.cash,r.id FROM users u " +
                    "INNER JOIN roles r ON u.role_id=r.id WHERE u.email=?";
    private static final String UPDATE_CASH_SQL = "UPDATE users u SET u.cash=? WHERE u.id=?";
    private static final String DELETE_USER_SQL = "UPDATE users u SET u.deleted=1 WHERE u.id=?";
    private static final String SHOW_USER_CASH_SQL = "SELECT u.cash FROM users u WHERE id=?";

    private UserDaoImpl() {
    }

    public static UserDao getInstance() {
        if (instance == null) {
            instance = new UserDaoImpl();
        }
        return instance;
    }

    @Override
    public User authorization(User user) throws DaoException {
        User userDao = null;
        ProxyConnection connection = null;
        PreparedStatement statement = null;
        ResultSet set = null;
        try {
            connection = manager.getConnection();
            statement = connection.prepareStatement(USER_AUTHORIZED_SQL);
            statement.setString(1, user.getEmail());
            set = statement.executeQuery();
            while (set.next()) {
                Long id = set.getLong(USER_ID);
                String name = set.getString(USER_NAME);
                String surname = set.getString(USER_SURNAME);
                String password = set.getString(USER_PASSWORD);
                BigDecimal cash = set.getBigDecimal(USER_CASH);
                Long roleId = set.getLong(ROLE_ID);
                userDao = new User(id, name, surname, password, user.getEmail(), cash, new Role(roleId));
            }
            return userDao;
        } catch (SQLException e) {
            logger.error("User authorization failed!", e);
            throw new DaoException("User authorization failed!", e);
        } finally {
            close(set);
            close(statement);
            close(connection);
        }
    }

    @Override
    public List<User> findAll(int limit, int offset) throws DaoException {
        List<User> users = new ArrayList<>();
        ProxyConnection connection = null;
        PreparedStatement statement = null;
        ResultSet set = null;
        try {
            connection = manager.getConnection();
            statement = connection.prepareStatement(FIND_ALL_USER_SQL);
            statement.setInt(1, limit);
            statement.setInt(2, offset);
            set = statement.executeQuery();
            while (set.next()) {
                User user = new User();
                user.setId(set.getLong(USER_ID));
                user.setName(set.getString(USER_NAME));
                user.setSurname(set.getString(USER_SURNAME));
                user.setEmail(set.getString(USER_EMAIL));
                user.setCash(set.getBigDecimal(USER_CASH));
                user.setRole(new Role(set.getLong(ROLE_ID)));
                users.add(user);
            }
            return users;
        } catch (SQLException e) {
            logger.error("User find all failed!", e);
            throw new DaoException("User find all failed!", e);
        } finally {
            close(set);
            close(statement);
            close(connection);
        }
    }

    @Override
    public long count() throws DaoException {
        ProxyConnection connection = null;
        Statement statement = null;
        ResultSet set = null;
        try {
            connection = manager.getConnection();
            statement = connection.createStatement();
            set = statement.executeQuery(COUNT_USERS_SQL);
            long count = 0L;
            if (set.next()) {
                count = set.getLong(ENTITY_COUNT);
            }
            return count;
        } catch (SQLException e) {
            logger.error("Count races exception!", e);
            throw new DaoException("Count races exception!", e);
        } finally {
            close(set);
            close(statement);
            close(connection);
        }
    }

    @Override
    public boolean updateCash(BigDecimal cash, Long userId) throws DaoException {
        ProxyConnection connection = null;
        PreparedStatement statement = null;
        try {
            connection = manager.getConnection();
            statement = connection.prepareStatement(UPDATE_CASH_SQL);
            statement.setBigDecimal(1, cash);
            statement.setLong(2, userId);
            int rowEffected = statement.executeUpdate();
            return rowEffected > 0;
        } catch (SQLException e) {
            logger.error("Update cash failed!", e);
            throw new DaoException("Update cash failed!", e);
        } finally {
            close(statement);
            close(connection);
        }
    }

    @Override
    public boolean updateCash(ProxyConnection connection, BigDecimal cash, Long userId) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(UPDATE_CASH_SQL);
        statement.setBigDecimal(1, cash);
        statement.setLong(2, userId);
        int rowEffected = statement.executeUpdate();
        return rowEffected > 0;
    }

    @Override
    protected BigDecimal findCash(ProxyConnection connection, Long userId) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(SHOW_USER_CASH_SQL);
        statement.setLong(1, userId);
        ResultSet set = statement.executeQuery();
        BigDecimal cash = BigDecimal.ZERO;
        while (set.next()) {
            cash = set.getBigDecimal(USER_CASH);
        }
        return cash;
    }

    @Override
    public boolean delete(String id) throws DaoException {
        long userId = Long.parseLong(id);
        ProxyConnection connection = null;
        PreparedStatement statement = null;
        try {
            connection = manager.getConnection();
            statement = connection.prepareStatement(DELETE_USER_SQL);
            statement.setLong(1, userId);
            int rowEffected = statement.executeUpdate();
            return rowEffected > 0;
        } catch (SQLException e) {
            logger.error("User delete failed!", e);
            throw new DaoException("User delete failed!", e);
        } finally {
            close(statement);
            close(connection);
        }
    }

    @Override
    public Optional<User> create(User user) throws DaoException {
        ProxyConnection connection = null;
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
            logger.error("User registration failed!", e);
            throw new DaoException("User registration failed!", e);
        } finally {
            close(statement);
            close(connection);
        }
    }

    @Override
    public Optional<User> read(String email) throws DaoException {
        ProxyConnection connection = null;
        PreparedStatement statement = null;
        ResultSet set = null;
        try {
            connection = manager.getConnection();
            statement = connection.prepareStatement(USER_EXIST_SQL);
            statement.setString(1, email);
            set = statement.executeQuery();
            if (set.next()) {
                return Optional.of(new User());
            } else {
                return Optional.empty();
            }
        } catch (SQLException e) {
            logger.error("User exist failed!", e);
            throw new DaoException("User exist failed!", e);
        } finally {
            close(set);
            close(statement);
            close(connection);
        }
    }
}