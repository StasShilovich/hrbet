package com.shilovich.hrbet.service;

import com.shilovich.hrbet.service.impl.*;

import java.util.Map;

/**
 * The type Service factory.
 */
public class ServiceFactory {
    private static final ServiceFactory instance = new ServiceFactory();
    private static final Map<Class<? extends Service>, Service> factory;

    static {
        factory = Map.of(
                UserService.class, UserServiceImpl.getInstance(),
                RaceService.class, RaceServiceImpl.getInstance(),
                BetService.class, BetServiceImpl.getInstance(),
                RatioService.class, RatioServiceImpl.getInstance(),
                HorseService.class, HorseServiceImpl.getInstance());
    }

    private ServiceFactory() {
    }

    /**
     * Gets implementation of interface class.
     *
     * @param clazz the clazz
     * @return the class
     */
    public Service getClass(Class<?> clazz) {
        return factory.get(clazz);
    }

    /**
     * Gets instance.
     *
     * @return the instance
     */
    public static ServiceFactory getInstance() {
        return instance;
    }
}