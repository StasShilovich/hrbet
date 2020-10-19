package com.shilovich.hrbet.main;

import com.shilovich.hrbet.beans.Bet;
import com.shilovich.hrbet.beans.Horse;
import com.shilovich.hrbet.dao.AbstractBetDao;
import com.shilovich.hrbet.dao.AbstractHorseDao;
import com.shilovich.hrbet.dao.DaoFactory;
import com.shilovich.hrbet.dao.exception.DaoException;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.LinkedBlockingDeque;

public class Main {
    public static void main(String[] args) throws MalformedURLException, URISyntaxException {
//        AbstractHorseDao horseDao = (AbstractHorseDao) DaoFactory.getInstance().getClass(AbstractHorseDao.class);
//        try {
////            UserLogIn admin = new UserLogIn("stas.shilovich@gmail.com", "admin");
////            UserLogIn user = new UserLogIn("vasia@rambler.ru", "qwerty");
////            UserLogIn user1 = new UserLogIn("laram@mail.ru", "12345");
//            Set<Horse> horses = horseDao.showByRace(1L);
//            for (Horse horse : horses) {
//                System.out.println(horse);
//            }
//        } catch (DaoException e) {
//            e.printStackTrace();
//        }
        String s = "http://localhost:8888/hrbets/dispatcher?command=races";
        URI uri = URI.create(s);
        String all = s.replaceAll("http://localhost:8888/hrbets", "");

    }
}
