package com.shilovich.hrbet.service;

import com.shilovich.hrbet.service.impl.RaceServiceImpl;

public class ServiceFactory {
    private static final ServiceFactory instance = new ServiceFactory();
    private final RaceService raceService = new RaceServiceImpl();

    private ServiceFactory() {
    }

    public RaceService getRaceService() {
        return raceService;
    }

    public static ServiceFactory getInstance() {
        return instance;
    }
}
