package com.shilovich.hrbet.main;

import com.shilovich.hrbet.beans.UserRegistration;
import com.shilovich.hrbet.dao.DaoFactory;
import com.shilovich.hrbet.dao.UserDao;
import com.shilovich.hrbet.dao.exception.DaoException;

public class Main {
    public static void main(String[] args) {
        DaoFactory factory = DaoFactory.getInstance();
        UserDao userDao = factory.getUserDao();
        UserRegistration userRegistration = new UserRegistration("322", "1adda8", "2312321e1e2", "teset");
        try {
            userDao.registration(userRegistration);
        } catch (DaoException e) {
            System.out.println(e.getMessage());
        }
    }
}
