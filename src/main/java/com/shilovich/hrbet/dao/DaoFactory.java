package com.shilovich.hrbet.dao;

import com.shilovich.hrbet.dao.connection.pool.TestConnection;
import com.shilovich.hrbet.dao.connection.pool.impl.MySqlConnection;
import com.shilovich.hrbet.dao.connection.property.PropertyManager;
import com.shilovich.hrbet.dao.connection.property.impl.PropertyManagerImpl;
import com.shilovich.hrbet.dao.impl.UserDaoImpl;

public class DaoFactory {
    private static final DaoFactory instance = new DaoFactory();
    private final UserDao userDao = new UserDaoImpl();
    private final TestConnection connection = new MySqlConnection();

    private DaoFactory() {
    }

    public UserDao getUserDao() {
        return userDao;
    }

    public TestConnection getConnection() {
        return connection;
    }

    public static DaoFactory getInstance() {
        return instance;
    }
}
