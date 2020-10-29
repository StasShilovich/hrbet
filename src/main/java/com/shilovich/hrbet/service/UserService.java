package com.shilovich.hrbet.service;

import com.shilovich.hrbet.bean.User;
import com.shilovich.hrbet.exception.ServiceException;

import java.util.Map;

public interface UserService extends Service {
    User authorization(User user) throws ServiceException;

    Map<String, String> registration(User user) throws ServiceException;
}
