package com.shilovich.hrbet.main;

import com.shilovich.hrbet.dao.AbstractBetDao;
import com.shilovich.hrbet.dao.impl.BetDaoImpl;
import com.shilovich.hrbet.dao.impl.RaceDaoImpl;
import com.shilovich.hrbet.dao.impl.UserDaoImpl;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

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
        BlockingQueue<String> test=new LinkedBlockingQueue<>();
        for (int i = 0; i < 3; i++) {
            test.add("Value "+(i+1));
        }
        String remove = test.remove();
        System.out.println(remove);
    }
}
