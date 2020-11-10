package com.shilovich.hrbet.main;

import com.shilovich.hrbet.bcrypt.BCryptService;
import com.shilovich.hrbet.bean.Horse;
import com.shilovich.hrbet.bean.Ratio;
import com.shilovich.hrbet.bean.User;
import com.shilovich.hrbet.dao.AbstractHorseDao;
import com.shilovich.hrbet.dao.AbstractRatioDao;
import com.shilovich.hrbet.dao.AbstractUserDao;
import com.shilovich.hrbet.dao.DaoFactory;
import com.shilovich.hrbet.exception.DaoException;


import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) throws MalformedURLException, URISyntaxException {

        try {
            AbstractHorseDao horseDao = (AbstractHorseDao) DaoFactory.getInstance().getClass(AbstractHorseDao.class);
            List<Horse> all = horseDao.findAll();
            for (Horse horse : all) {
                System.out.println(horse);
            }
        } catch (DaoException e) {
            System.out.println(e.getMessage());
        }

    }
}
