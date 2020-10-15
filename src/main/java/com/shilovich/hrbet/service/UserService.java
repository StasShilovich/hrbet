package com.shilovich.hrbet.service;

import com.shilovich.hrbet.beans.User;
import com.shilovich.hrbet.service.exception.ServiceException;

public interface UserService extends Service {
    User authorization(User user) throws ServiceException;

    User registration(User user) throws ServiceException;
}
