package com.shilovich.hrbet.dao;

import com.shilovich.hrbet.dao.connection.pool.MySqlConnectionPool;
import com.shilovich.hrbet.dao.connection.pool.impl.MySqlConnectionPoolImpl;
import com.shilovich.hrbet.dao.impl.RaceDaoImpl;
import com.shilovich.hrbet.dao.impl.RolePermissionsDaoImpl;
import com.shilovich.hrbet.dao.impl.UserDaoImpl;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class DaoFactory {
    private static Map<Class<?>, DaoCRUD> factory = new HashMap<>();

    static {
        factory.put(UserDao.class, new UserDaoImpl());
        factory.put(RaceDao.class, new RaceDaoImpl());
        factory.put(RolePermissionsDao.class, new RolePermissionsDaoImpl());
    }

    private DaoFactory() {
    }

    public static DaoCRUD getClass(Class clazz) {
        return factory.get(clazz);
    }
}