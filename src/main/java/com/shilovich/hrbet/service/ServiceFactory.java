package com.shilovich.hrbet.service;

import com.shilovich.hrbet.service.impl.BetServiceImpl;
import com.shilovich.hrbet.service.impl.RaceServiceImpl;
import com.shilovich.hrbet.service.impl.UserServiceImpl;

import java.util.Map;

public class ServiceFactory {
    private static final ServiceFactory instance = new ServiceFactory();
    private static final Map<Class<? extends Service>, Service> factory;

    static {
        factory = Map.of(
                UserService.class, new UserServiceImpl(),
                RaceService.class, new RaceServiceImpl(),
                BetService.class, new BetServiceImpl());
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