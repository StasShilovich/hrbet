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

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static com.shilovich.hrbet.constant.CommonConstant.*;

public class UserServiceImpl implements UserService {
    private static final Logger logger = LogManager.getLogger(UserServiceImpl.class);

    @Override
    public User authorization(User user) throws ServiceException {
        try {
            if (!ValidationService.isValidUserEmail(user.getEmail()) && !ValidationService.isValidUserPassword(user.getPassword())) {
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
    public Map<String, String> registration(User userUI) throws ServiceException {
        try {
            Map<String, String> userMAP = new HashMap<>();
            boolean invalidUser = false;
            if (!ValidationService.isValidUserName(userUI.getName())) {
                invalidUser = true;
                userMAP.put(PARAM_NAME, PARAM_ERROR_NAME);
            }
            if (!ValidationService.isValidUserSurname(userUI.getSurname())) {
                invalidUser = true;
                userMAP.put(PARAM_SURNAME, PARAM_ERROR_SURNAME);
            }
            if (!ValidationService.isValidUserPassword(userUI.getPassword())) {
                invalidUser = true;
                userMAP.put(PARAM_PASSWORD, PARAM_ERROR_PASSWORD);
            }
            AbstractUserDao userDao = (AbstractUserDao) DaoFactory.getInstance().getClass(AbstractUserDao.class);
            Optional<User> user = userDao.read(userUI.getEmail());
            if (user.isPresent()) {
                userMAP.put(PARAM_EMAIL, PARAM_ERROR_EMAIL);
            } else if (ValidationService.isValidUserEmail(userUI.getEmail()) && !invalidUser) {
                String password = userUI.getPassword();
                String hashPassword = BCrypt.withDefaults().hashToString(12, password.toCharArray());
                userUI.setPassword(hashPassword);
                userDao.create(userUI);
                return null;
            }
            return userMAP;
        } catch (DaoException e) {
            logger.error("Registration exception!");
            throw new ServiceException("Registration exception!", e);
        }
    }
}