package com.shilovich.hrbet.service;

import com.shilovich.hrbet.dao.UserDao;
import com.shilovich.hrbet.dao.impl.UserDaoImpl;
import com.shilovich.hrbet.service.impl.RaceServiceImpl;
import com.shilovich.hrbet.service.impl.UserServiceImpl;

public class ServiceFactory {
    private static final ServiceFactory instance = new ServiceFactory();
    private final RaceService raceService = new RaceServiceImpl();
    private final UserService userService = new UserServiceImpl();

    private ServiceFactory() {
    }

    public UserService getUserService() {
        return userService;
    }

    public RaceService getRaceService() {
        return raceService;
    }

    public static ServiceFactory getInstance() {
        return instance;
    }
}