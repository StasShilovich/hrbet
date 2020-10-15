package com.shilovich.hrbet.service.validation;

import com.shilovich.hrbet.beans.User;
import com.shilovich.hrbet.service.Service;

public interface ValidationService extends Service {
    boolean isValidUser(User user);
}
