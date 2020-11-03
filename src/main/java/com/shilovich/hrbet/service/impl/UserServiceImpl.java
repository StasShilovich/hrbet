package com.shilovich.hrbet.service.impl;

import com.shilovich.hrbet.bean.Role;
import com.shilovich.hrbet.bean.User;
import com.shilovich.hrbet.cache.Cache;
import com.shilovich.hrbet.cache.CacheFactory;
import com.shilovich.hrbet.cache.CacheType;
import com.shilovich.hrbet.dao.AbstractRaceDao;
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

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.shilovich.hrbet.cache.CacheVariables.USER_ROLES;
import static com.shilovich.hrbet.controller.CommandParameter.*;

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
            if (BCryptService.verifyPassword(userAuthorized.getPassword(), user.getPassword())) {
                return null;
            }
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
            Role role = findRole(roles, userAuthorized.getRole().getId());
            userAuthorized.setRole(role);
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

    private Role findRole(List<Role> roles, Long roleId) {
        Role result = new Role();
        for (Role role : roles) {
            if (role.getId().equals(roleId)) {
                result = role;
            }
        }
        return result;
    }
}