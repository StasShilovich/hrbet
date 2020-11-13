package com.shilovich.hrbet.service;

import com.shilovich.hrbet.bean.Page;
import com.shilovich.hrbet.bean.User;
import com.shilovich.hrbet.exception.ServiceException;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface UserService extends Service {
    User authorization(User user) throws ServiceException;

    Map<String, String> registration(User user) throws ServiceException;

    Page<User> findAll(int limit, int offset) throws ServiceException;

    int getUsersPagesCount() throws ServiceException;

    boolean updateCash(BigDecimal cash, Long userId) throws ServiceException;

    boolean ban(Long id) throws ServiceException;
}
