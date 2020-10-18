package com.shilovich.hrbet.service;

import com.shilovich.hrbet.service.impl.RaceServiceImpl;
import com.shilovich.hrbet.service.impl.UserServiceImpl;

import java.util.HashMap;
import java.util.Map;

public class ServiceFactory {
    private static final ServiceFactory instance = new ServiceFactory();
    private static final Map<Class<? extends Service>, Service> factory = new HashMap<>();

    static {
        factory.put(UserService.class, new UserServiceImpl());
        factory.put(RaceService.class, new RaceServiceImpl());
    }

    private ServiceFactory() {
    }

    public Service getClass(Class<?> clazz) {
        return factory.get(clazz);
    }

    public static ServiceFactory getInstance() {
        return instance;
    }
}