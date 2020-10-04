package com.shilovich.hrbet.service;

import com.shilovich.hrbet.beans.UserAuthorized;
import com.shilovich.hrbet.beans.UserLogIn;
import com.shilovich.hrbet.beans.UserRegistration;
import com.shilovich.hrbet.service.exception.ServiceException;

public interface UserService {
    UserAuthorized authorization(UserLogIn logInUser) throws ServiceException;

    void registration(UserRegistration registrationUser) throws ServiceException;
}
