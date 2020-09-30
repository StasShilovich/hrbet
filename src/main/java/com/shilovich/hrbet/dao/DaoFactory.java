package com.shilovich.hrbet.dao;

import com.shilovich.hrbet.dao.connection.pool.MySqlConnectionPool;
import com.shilovich.hrbet.dao.connection.pool.impl.MySqlConnectionPoolImpl;
import com.shilovich.hrbet.dao.impl.RaceDaoImpl;
import com.shilovich.hrbet.dao.impl.UserDaoImpl;

public class DaoFactory {
    private static final DaoFactory instance = new DaoFactory();
    private final MySqlConnectionPool connectionPool = new MySqlConnectionPoolImpl();
    private final UserDao userDao = new UserDaoImpl();
    private final RaceDao raceDao = new RaceDaoImpl();

    private DaoFactory() {
    }

    public RaceDao getRaceDao() {
        return raceDao;
    }

    public UserDao getUserDao() {
        return userDao;
    }

    public MySqlConnectionPool getConnectionPool() {
        return connectionPool;
    }

    public static DaoFactory getInstance() {
        return instance;
    }
}