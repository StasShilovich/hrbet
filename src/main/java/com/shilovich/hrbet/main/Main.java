package com.shilovich.hrbet.main;

import com.shilovich.hrbet.beans.UserAuthorized;
import com.shilovich.hrbet.beans.UserLogIn;
import com.shilovich.hrbet.dao.DaoFactory;
import com.shilovich.hrbet.dao.UserDao;
import com.shilovich.hrbet.dao.exception.DaoException;

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
