package com.shilovich.hrbet.dao.connection.property;

import com.shilovich.hrbet.exception.DaoException;

public interface PropertyManager {
    String getProperty(String key) throws DaoException;
}