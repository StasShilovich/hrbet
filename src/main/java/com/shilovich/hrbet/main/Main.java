package com.shilovich.hrbet.main;

import com.shilovich.hrbet.beans.Race;
import com.shilovich.hrbet.beans.UserRegistration;
import com.shilovich.hrbet.dao.DaoFactory;
import com.shilovich.hrbet.dao.RaceDao;
import com.shilovich.hrbet.dao.UserDao;
import com.shilovich.hrbet.dao.exception.DaoException;
import com.shilovich.hrbet.service.RaceService;
import com.shilovich.hrbet.service.ServiceFactory;
import com.shilovich.hrbet.service.exception.ServiceException;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        ServiceFactory serviceFactory = ServiceFactory.getInstance();
        RaceService raceService = serviceFactory.getRaceService();

        try {
            List<Race> races = raceService.showAll();
            for (Race race : races) {
                System.out.println(race);

            }
        } catch (ServiceException e) {
            System.out.println(e.getMessage());
        }
    }
}
