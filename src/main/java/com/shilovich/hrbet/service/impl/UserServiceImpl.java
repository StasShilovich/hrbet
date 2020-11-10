package com.shilovich.hrbet.service.impl;

import com.shilovich.hrbet.bean.Page;
import com.shilovich.hrbet.bean.Role;
import com.shilovich.hrbet.bean.User;
import com.shilovich.hrbet.cache.Cache;
import com.shilovich.hrbet.cache.CacheFactory;
import com.shilovich.hrbet.cache.CacheType;
import com.shilovich.hrbet.dao.AbstractRolePermissionsDao;
import com.shilovich.hrbet.dao.DaoFactory;
import com.shilovich.hrbet.dao.AbstractUserDao;
import com.shilovich.hrbet.exception.DaoException;
import com.shilovich.hrbet.service.UserService;
import com.shilovich.hrbet.exception.ServiceException;
import com.shilovich.hrbet.bcrypt.BCryptService;
import com.shilovich.hrbet.validation.ValidationService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

import static com.shilovich.hrbet.cache.CacheVariables.COUNT;
import static com.shilovich.hrbet.cache.CacheVariables.USER_ROLES;
import static com.shilovich.hrbet.controller.CommandParameter.*;
import static com.shilovich.hrbet.service.ServiceParameter.USERS_ON_PAGE;

public class UserServiceImpl implements UserService {
    private static final Logger logger = LogManager.getLogger(UserServiceImpl.class);
    public static final String BLANK = "";

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
            if (!BCryptService.verifyPassword(user.getPassword(), userAuthorized.getPassword())) {
                return null;
            }
            setCashRole(userAuthorized);
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
            if (ValidationService.isValidUserName(userUI.getName())) {
                userMAP.put(PARAM_NAME, userUI.getName());
            } else {
                invalidUser = true;
                userMAP.put(PARAM_NAME, BLANK);
            }
            if (ValidationService.isValidUserSurname(userUI.getSurname())) {
                userMAP.put(PARAM_SURNAME, userUI.getSurname());
            } else {
                invalidUser = true;
                userMAP.put(PARAM_SURNAME, BLANK);
            }
            if (ValidationService.isValidUserPassword(userUI.getPassword())) {
                userMAP.put(PARAM_PASSWORD, userUI.getPassword());
            } else {
                invalidUser = true;
                userMAP.put(PARAM_PASSWORD, BLANK);
            }
            AbstractUserDao userDao = (AbstractUserDao) DaoFactory.getInstance().getClass(AbstractUserDao.class);
            Optional<User> user = userDao.read(userUI.getEmail());
            if (ValidationService.isValidUserEmail(userUI.getEmail()) && user.isEmpty()) {
                userMAP.put(PARAM_EMAIL, userUI.getEmail());
            } else {
                invalidUser = true;
                userMAP.put(PARAM_EMAIL, BLANK);
            }
            if (!invalidUser) {
                String password = userUI.getPassword();
                userUI.setPassword(BCryptService.hashPassword(password));
                userDao.create(userUI);
                return null;
            }
            return userMAP;
        } catch (DaoException e) {
            logger.error("Registration exception!");
            throw new ServiceException("Registration exception!", e);
        }
    }

    @Override
    public Page<User> findAll(int limit, int offset) throws ServiceException {
        try {
            AbstractUserDao userDao = (AbstractUserDao) DaoFactory.getInstance().getClass(AbstractUserDao.class);
            List<User> users = userDao.findAll(limit, offset);
            for (User user : users) {
                setCashRole(user);
            }
            int pageNumber = offset / USERS_ON_PAGE;
            return new Page<>(pageNumber, users);
        } catch (DaoException e) {
            logger.error("Show all users exception!");
            throw new ServiceException("Show all users service exception!", e);
        }
    }

    @Override
    public int getUsersPagesCount() throws ServiceException {
        try {
            long count;
            Cache cache = (Cache) CacheFactory.getInstance().getCache(CacheType.USER_COUNT);
            if (cache.isEmpty()) {
                AbstractUserDao userDao = (AbstractUserDao) DaoFactory.getInstance().getClass(AbstractUserDao.class);
                count = userDao.count();
                cache.addCache(COUNT, new AtomicLong(count));
            } else {
                AtomicLong aLong = (AtomicLong) cache.getCache(COUNT);
                count = aLong.get();
            }
            return (int) Math.ceil((double) count / USERS_ON_PAGE);
        } catch (DaoException e) {
            logger.error("Users count exception!");
            throw new ServiceException("Users count exception!", e);
        }
    }

    @Override
    public boolean updateCash(BigDecimal cash, Long userId) throws ServiceException {
        try {
            AbstractUserDao userDao = (AbstractUserDao) DaoFactory.getInstance().getClass(AbstractUserDao.class);
            return userDao.updateCash(cash, userId);
        } catch (DaoException e) {
            logger.error("Add cash exception!");
            throw new ServiceException("Add cash exception!", e);
        }
    }

    private void setCashRole(User user) throws DaoException {
        Cache cache = (Cache) CacheFactory.getInstance().getCache(CacheType.ROLES);
        List<Role> roles = null;
        if (cache.isEmpty()) {
            AbstractRolePermissionsDao rolePermissionsDao = (AbstractRolePermissionsDao) DaoFactory.getInstance()
                    .getClass(AbstractRolePermissionsDao.class);
            roles = rolePermissionsDao.findAll();
            cache.addCache(USER_ROLES, roles);
        } else {
            roles = (List<Role>) cache.getCache(USER_ROLES);
        }
        for (Role role : roles) {
            if (role.getId().equals(user.getRole().getId())) {
                user.setRole(role);
            }
        }
    }
}