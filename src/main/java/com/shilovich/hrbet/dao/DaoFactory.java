package com.shilovich.hrbet.dao;

import com.shilovich.hrbet.dao.impl.RaceDaoImpl;
import com.shilovich.hrbet.dao.impl.RolePermissionsDaoImpl;
import com.shilovich.hrbet.dao.impl.UserDaoImpl;

import java.util.HashMap;
import java.util.Map;

public class DaoFactory {
    private static final DaoFactory instance = new DaoFactory();
    private static final Map<Class<? extends DaoCRUD<?, ?>>, DaoCRUD<?, ?>> factory = new HashMap<>();

    static {
        factory.put(UserDao.class, new UserDaoImpl());
        factory.put(RaceDao.class, new RaceDaoImpl());
        factory.put(RolePermissionsDao.class, new RolePermissionsDaoImpl());
    }

    private DaoFactory() {
    }

    public static DaoFactory getInstance() {
        return instance;
    }

    public DaoCRUD<?, ?> getClass(Class<?> clazz) {
        return factory.get(clazz);
    }
}