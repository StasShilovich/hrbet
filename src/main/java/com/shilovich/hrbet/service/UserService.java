package com.shilovich.hrbet.service;

import com.shilovich.hrbet.beans.User;
import com.shilovich.hrbet.service.exception.ServiceException;

import java.util.Map;

public interface UserService extends Service {
    User authorization(User user) throws ServiceException;

    Map<String, String> registration(User user) throws ServiceException;
}
