package com.shilovich.hrbet.service.impl;

import com.shilovich.hrbet.beans.UserAuthorized;
import com.shilovich.hrbet.beans.UserLogIn;
import com.shilovich.hrbet.beans.UserRegistration;
import com.shilovich.hrbet.dao.DaoFactory;
import com.shilovich.hrbet.dao.UserDao;
import com.shilovich.hrbet.dao.exception.DaoException;
import com.shilovich.hrbet.service.UserService;
import com.shilovich.hrbet.service.exception.ServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class UserServiceImpl extends UserService {
    private static final Logger logger = LogManager.getLogger(UserServiceImpl.class);

    @Override
    public UserAuthorized authorization(UserLogIn logInUser) throws ServiceException {
        try {
            UserDao userDao = (UserDao) DaoFactory.getInstance().getClass(UserDao.class);
            UserAuthorized authorization = userDao.authorization(logInUser);
            return authorization;
        } catch (DaoException e) {
            logger.debug("Authorization exception!");
            throw new ServiceException("Authorization exception!", e);
        }
    }

    @Override
    public void registration(UserRegistration registrationUser) throws ServiceException {
        try {
            UserDao userDao = (UserDao) DaoFactory.getInstance().getClass(UserDao.class);
            userDao.registration(registrationUser);
        } catch (DaoException e) {
            logger.debug("Registration exception!");
            throw new ServiceException("Registration exception!", e);
        }
    }
}