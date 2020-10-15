package com.shilovich.hrbet.main;

import com.shilovich.hrbet.beans.Bet;
import com.shilovich.hrbet.beans.Horse;
import com.shilovich.hrbet.dao.AbstractBetDao;
import com.shilovich.hrbet.dao.AbstractHorseDao;
import com.shilovich.hrbet.dao.DaoFactory;
import com.shilovich.hrbet.dao.exception.DaoException;

import java.util.List;
import java.util.Set;

public class Main {
    public static void main(String[] args) {
        AbstractHorseDao horseDao = (AbstractHorseDao) DaoFactory.getInstance().getClass(AbstractHorseDao.class);
        try {
//            UserLogIn admin = new UserLogIn("stas.shilovich@gmail.com", "admin");
//            UserLogIn user = new UserLogIn("vasia@rambler.ru", "qwerty");
//            UserLogIn user1 = new UserLogIn("laram@mail.ru", "12345");
            Set<Horse> horses = horseDao.showByRace(1L);
            for (Horse horse : horses) {
                System.out.println(horse);
            }
        } catch (DaoException e) {
            e.printStackTrace();
        }

    }
}
