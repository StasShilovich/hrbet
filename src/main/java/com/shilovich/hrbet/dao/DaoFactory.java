package com.shilovich.hrbet.dao;

import com.shilovich.hrbet.dao.impl.*;

import java.util.HashMap;
import java.util.Map;

public class DaoFactory {
    private static final DaoFactory instance = new DaoFactory();
    private static final Map<Class<? extends DaoCRUD<?, ?>>, DaoCRUD<?, ?>> factory = new HashMap<>();

    static {
        factory.put(AbstractUserDao.class, new UserDaoImpl());
        factory.put(AbstractRaceDao.class, new RaceDaoImpl());
        factory.put(AbstractRolePermissionsDao.class, new RolePermissionsDaoImpl());
        factory.put(AbstractBetDao.class, new BetDaoImpl());
        factory.put(AbstractHorseDao.class, new HorseDaoImpl());
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