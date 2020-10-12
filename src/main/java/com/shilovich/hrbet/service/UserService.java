package com.shilovich.hrbet.service;

import com.shilovich.hrbet.beans.UserAuthorized;
import com.shilovich.hrbet.beans.UserLogIn;
import com.shilovich.hrbet.beans.UserRegistration;
import com.shilovich.hrbet.service.exception.ServiceException;

public abstract class UserService implements Service {
    public abstract UserAuthorized authorization(UserLogIn logInUser) throws ServiceException;

    public abstract void registration(UserRegistration registrationUser) throws ServiceException;
}
