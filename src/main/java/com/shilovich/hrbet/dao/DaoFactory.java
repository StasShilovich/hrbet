package com.shilovich.hrbet.dao;

import com.shilovich.hrbet.dao.impl.*;

import java.util.Map;

public class DaoFactory {
    private static final DaoFactory instance = new DaoFactory();
    private static final Map<Class<? extends Dao<?, ?>>, Dao<?, ?>> factory;

    static {
        factory = Map.of(
                UserDao.class, UserDaoImpl.getInstance(),
                RaceDao.class, RaceDaoImpl.getInstance(),
                RolePermissionsDao.class, RolePermissionsDaoImpl.getInstance(),
                BetDao.class, BetDaoImpl.getInstance(),
                HorseDao.class, HorseDaoImpl.getInstance(),
                RatioDao.class, RatioDaoImpl.getInstance());
    }

    private DaoFactory() {
    }

    public static DaoFactory getInstance() {
        return instance;
    }

    public Dao<?, ?> getClass(Class<?> clazz) {
        return factory.get(clazz);
    }
}