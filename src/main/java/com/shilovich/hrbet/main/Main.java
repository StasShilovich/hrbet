package com.shilovich.hrbet.main;

import com.shilovich.hrbet.beans.Race;
import com.shilovich.hrbet.beans.UserRegistration;
import com.shilovich.hrbet.dao.DaoFactory;
import com.shilovich.hrbet.dao.RaceDao;
import com.shilovich.hrbet.dao.UserDao;
import com.shilovich.hrbet.dao.exception.DaoException;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        DaoFactory factory = DaoFactory.getInstance();
        RaceDao raceDao = factory.getRaceDao();
        try {
            List<Race> races = raceDao.showAll();
            for (Race race : races) {
                System.out.println(race);

            }
        } catch (DaoException e) {
            System.out.println(e.getMessage());
        }
    }
}
