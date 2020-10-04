package com.shilovich.hrbet.main;

import at.favre.lib.crypto.bcrypt.BCrypt;
import at.favre.lib.crypto.bcrypt.BCryptFormatter;
import com.shilovich.hrbet.beans.Race;
import com.shilovich.hrbet.beans.UserAuthorized;
import com.shilovich.hrbet.beans.UserLogIn;
import com.shilovich.hrbet.beans.UserRegistration;
import com.shilovich.hrbet.dao.DaoFactory;
import com.shilovich.hrbet.dao.RaceDao;
import com.shilovich.hrbet.dao.UserDao;
import com.shilovich.hrbet.dao.exception.DaoException;
import com.shilovich.hrbet.service.RaceService;
import com.shilovich.hrbet.service.ServiceFactory;
import com.shilovich.hrbet.service.exception.ServiceException;

import java.sql.Connection;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        DaoFactory factory = DaoFactory.getInstance();
        try {
            UserLogIn admin = new UserLogIn("stas.shilovich@gmail.com", "admin");
            UserLogIn user = new UserLogIn("vasia@rambler.ru", "qwerty");
            UserLogIn user1 = new UserLogIn("laram@mail.ru", "12345");
            UserAuthorized authorization = factory.getUserDao()
                    .authorization(user1);
            System.out.println(authorization);
        } catch (DaoException e) {
            e.printStackTrace();
        }


    }
}
