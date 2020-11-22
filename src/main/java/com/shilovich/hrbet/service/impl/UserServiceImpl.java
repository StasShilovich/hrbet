package com.shilovich.hrbet.service.impl;

import com.shilovich.hrbet.bean.Role;
import com.shilovich.hrbet.bean.User;
import com.shilovich.hrbet.cache.Cache;
import com.shilovich.hrbet.cache.CacheFactory;
import com.shilovich.hrbet.cache.CacheType;
import com.shilovich.hrbet.dao.RolePermissionsDao;
import com.shilovich.hrbet.dao.DaoFactory;
import com.shilovich.hrbet.dao.UserDao;
import com.shilovich.hrbet.exception.DaoException;
import com.shilovich.hrbet.service.UserService;
import com.shilovich.hrbet.exception.ServiceException;
import com.shilovich.hrbet.util.BCryptService;
import com.shilovich.hrbet.validation.CommonValidator;
import com.shilovich.hrbet.validation.UserValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

import static com.shilovich.hrbet.cache.CacheVariables.COUNT_ACTIVE;
import static com.shilovich.hrbet.cache.CacheVariables.USER_ROLES;
import static com.shilovich.hrbet.controller.CommandParameter.*;
import static com.shilovich.hrbet.service.ServiceParameter.USERS_ON_PAGE;

public class UserServiceImpl implements UserService {
    private static final Logger logger = LogManager.getLogger(UserServiceImpl.class);
    public static final String BLANK = "";

    private static UserService instance;

    private UserServiceImpl() {
    }

    public static UserService getInstance() {
        if (instance == null) {
            instance = new UserServiceImpl();
        }
        return instance;
    }

    @Override
    public User authorization(User user) throws ServiceException {
        try {
            if (!UserValidator.isValidEmail(user.getEmail()) && !UserValidator.isValidPassword(user.getPassword())) {
                return null;
            }
            UserDao userDao = (UserDao) DaoFactory.getInstance().getClass(UserDao.class);
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
            logger.error("Authorization exception!", e);
            throw new ServiceException("Authorization exception!", e);
        }
    }


    @Override
    public Map<String, String> registration(User userUI) throws ServiceException {
        try {
            Map<String, String> userMAP = new HashMap<>();
            boolean invalidUser = false;
            if (UserValidator.isValidName(userUI.getName())) {
                userMAP.put(PARAM_NAME, userUI.getName());
            } else {
                invalidUser = true;
                userMAP.put(PARAM_NAME, BLANK);
            }
            if (UserValidator.isValidSurname(userUI.getSurname())) {
                userMAP.put(PARAM_SURNAME, userUI.getSurname());
            } else {
                invalidUser = true;
                userMAP.put(PARAM_SURNAME, BLANK);
            }
            if (UserValidator.isValidPassword(userUI.getPassword())) {
                userMAP.put(PARAM_PASSWORD, userUI.getPassword());
            } else {
                invalidUser = true;
                userMAP.put(PARAM_PASSWORD, BLANK);
            }
            UserDao userDao = (UserDao) DaoFactory.getInstance().getClass(UserDao.class);
            Optional<User> user = userDao.read(userUI.getEmail());
            if (UserValidator.isValidEmail(userUI.getEmail()) && user.isEmpty()) {
                userMAP.put(PARAM_EMAIL, userUI.getEmail());
            } else {
                invalidUser = true;
                userMAP.put(PARAM_EMAIL, BLANK);
            }
            if (!invalidUser) {
                String password = userUI.getPassword();
                userUI.setPassword(BCryptService.hashPassword(password));
                userDao.create(userUI);
                Cache cache = (Cache) CacheFactory.getInstance().getCache(CacheType.USER_COUNT);
                if (!cache.isEmpty()) {
                    AtomicLong aLong = (AtomicLong) cache.getCache(COUNT_ACTIVE);
                    long newLong = aLong.incrementAndGet();
                    AtomicLong newALong = new AtomicLong(newLong);
                    cache.setCacheValue(COUNT_ACTIVE, aLong, newALong);
                }
                return null;
            }
            return userMAP;
        } catch (DaoException e) {
            logger.error("Registration exception!", e);
            throw new ServiceException("Registration exception!", e);
        }
    }

    @Override
    public List<User> findAll(String page) throws ServiceException {
        try {
            int offset = 0;
            if (page != null && !page.isEmpty()) {
                offset = (Integer.parseInt(page) - 1) * USERS_ON_PAGE;
            }
            UserDao userDao = (UserDao) DaoFactory.getInstance().getClass(UserDao.class);
            List<User> users = userDao.findAll(USERS_ON_PAGE, offset);
            for (User user : users) {
                setCashRole(user);
            }
            return users;
        } catch (DaoException e) {
            logger.error("Show all users exception!", e);
            throw new ServiceException("Show all users service exception!", e);
        }
    }

    @Override
    public int getUsersPagesCount() throws ServiceException {
        try {
            long count;
            Cache cache = (Cache) CacheFactory.getInstance().getCache(CacheType.USER_COUNT);
            if (cache.isEmpty()) {
                UserDao userDao = (UserDao) DaoFactory.getInstance().getClass(UserDao.class);
                count = userDao.count();
                cache.addCache(COUNT_ACTIVE, new AtomicLong(count));
            } else {
                AtomicLong aLong = (AtomicLong) cache.getCache(COUNT_ACTIVE);
                count = aLong.get();
            }
            return (int) Math.ceil((double) count / USERS_ON_PAGE);
        } catch (DaoException e) {
            logger.error("Users count exception!", e);
            throw new ServiceException("Users count exception!", e);
        }
    }

    @Override
    public boolean updateCash(BigDecimal cash, Long userId) throws ServiceException {
        try {
            UserDao userDao = (UserDao) DaoFactory.getInstance().getClass(UserDao.class);
            return userDao.updateCash(cash, userId);
        } catch (DaoException e) {
            logger.error("Add cash exception!", e);
            throw new ServiceException("Add cash exception!", e);
        }
    }

    @Override
    public boolean ban(String userId) throws ServiceException {
        try {
            if (!CommonValidator.isIdValid(userId)) {
                return false;
            }
            UserDao userDao = (UserDao) DaoFactory.getInstance().getClass(UserDao.class);
            return userDao.delete(userId);
        } catch (DaoException e) {
            logger.error("User ban exception!", e);
            throw new ServiceException("User ban exception!", e);
        }
    }

    private void setCashRole(User user) throws DaoException {
        Cache cache = (Cache) CacheFactory.getInstance().getCache(CacheType.ROLES);
        List<Role> roles = null;
        if (cache.isEmpty()) {
            RolePermissionsDao rolePermissionsDao = (RolePermissionsDao) DaoFactory.getInstance()
                    .getClass(RolePermissionsDao.class);
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