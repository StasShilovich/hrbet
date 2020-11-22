package com.shilovich.hrbet.service;

import com.shilovich.hrbet.bean.User;
import com.shilovich.hrbet.exception.ServiceException;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface UserService extends Service {
    User authorization(User user) throws ServiceException;

    Map<String, String> registration(User user) throws ServiceException;

    List<User> findAll(String page) throws ServiceException;

    int getUsersPagesCount() throws ServiceException;

    boolean updateCash(BigDecimal cash, Long userId) throws ServiceException;

    boolean ban(String userId) throws ServiceException;
}
