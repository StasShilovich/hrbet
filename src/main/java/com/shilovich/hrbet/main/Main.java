package com.shilovich.hrbet.main;

import at.favre.lib.crypto.bcrypt.BCrypt;
import at.favre.lib.crypto.bcrypt.BCryptFormatter;
import com.shilovich.hrbet.beans.*;
import com.shilovich.hrbet.dao.DaoFactory;
import com.shilovich.hrbet.dao.RaceDao;
import com.shilovich.hrbet.dao.UserDao;
import com.shilovich.hrbet.dao.exception.DaoException;
import com.shilovich.hrbet.service.RaceService;
import com.shilovich.hrbet.service.ServiceFactory;
import com.shilovich.hrbet.service.exception.ServiceException;

import java.sql.Connection;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class Main {
    public static void main(String[] args) {
        UserDao userDao = (UserDao) DaoFactory.getInstance().getClass(UserDao.class);
        try {
            UserLogIn admin = new UserLogIn("stas.shilovich@gmail.com", "admin");
            UserLogIn user = new UserLogIn("vasia@rambler.ru", "qwerty");
            UserLogIn user1 = new UserLogIn("laram@mail.ru", "12345");
            UserAuthorized authorization = userDao.authorization(admin);
            System.out.println(authorization);
        } catch (DaoException e) {
            e.printStackTrace();
        }

    }
}
