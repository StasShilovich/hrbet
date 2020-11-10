package com.shilovich.hrbet.dao;

import com.shilovich.hrbet.dao.impl.*;

import java.util.Map;

public class DaoFactory {
    private static final DaoFactory instance = new DaoFactory();
    private static final Map<Class<? extends Dao<?, ?>>, Dao<?, ?>> factory;

    static {
        factory = Map.of(
                AbstractUserDao.class, new UserDaoImpl(),
                AbstractRaceDao.class, new RaceDaoImpl(),
                AbstractRolePermissionsDao.class, new RolePermissionsDaoImpl(),
                AbstractBetDao.class, new BetDaoImpl(),
                AbstractHorseDao.class, new HorseDaoImpl(),
                AbstractRatioDao.class, new RatioDaoImpl());
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