package com.shilovich.hrbet.service.impl;

import at.favre.lib.crypto.bcrypt.BCrypt;
import com.shilovich.hrbet.beans.User;
import com.shilovich.hrbet.dao.DaoFactory;
import com.shilovich.hrbet.dao.AbstractUserDao;
import com.shilovich.hrbet.dao.exception.DaoException;
import com.shilovich.hrbet.service.UserService;
import com.shilovich.hrbet.service.exception.ServiceException;
import com.shilovich.hrbet.service.validation.ValidationService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class UserServiceImpl implements UserService {
    private static final Logger logger = LogManager.getLogger(UserServiceImpl.class);

    @Override
    public User authorization(User user) throws ServiceException {
        try {
            if (!ValidationService.isValidUser(user)) {
                return null;
            }
            AbstractUserDao userDao = (AbstractUserDao) DaoFactory.getInstance().getClass(AbstractUserDao.class);
            User userAuthorized = userDao.authorization(user);
            if (userAuthorized == null) {
                return null;
            }
            BCrypt.Result verify = BCrypt.verifyer().verify(user.getPassword().toCharArray(), userAuthorized.getPassword());
            if (!verify.verified) {
                return null;
            }
            return userAuthorized;
        } catch (DaoException e) {
            logger.error("Authorization exception!");
            throw new ServiceException("Authorization exception!", e);
        }
    }

    @Override
    public User registration(User user) throws ServiceException {
        try {
            if (!ValidationService.isValidUser(user)) {
                return null;
            }
            String password = user.getPassword();
            String hashPassword = BCrypt.withDefaults().hashToString(12, password.toCharArray());
            user.setPassword(hashPassword);
            AbstractUserDao userDao = (AbstractUserDao) DaoFactory.getInstance().getClass(AbstractUserDao.class);
            User userReg = userDao.create(user);
            return userReg;
        } catch (DaoException e) {
            logger.error("Registration exception!");
            throw new ServiceException("Registration exception!", e);
        }
    }
}