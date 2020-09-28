package com.shilovich.hrbet.dao.connection.property.impl;

import com.shilovich.hrbet.dao.connection.property.PropertyManager;
import com.shilovich.hrbet.dao.exception.DaoException;

import java.util.MissingResourceException;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

public class PropertyManagerImpl implements PropertyManager {
    private static final String BUNDLE_NAME = "project";
    public static final String URL = "database.url";
    public static final String USERNAME = "database.username";
    public static final String PASSWORD = "database.password";
    public static final String MIN_IDLE = "database.minIdle";
    public static final String MAX_IDLE = "database.maxIdle";
    public static final String MAX_OPEN_PREPARED_STATEMENTS = "database.maxOpenPreparedStatements";

    @Override
    public String getProperty(String key) throws DaoException {
        try {
            ResourceBundle resourceBundle = PropertyResourceBundle.getBundle(BUNDLE_NAME);
            return resourceBundle.getString(key);
        } catch (MissingResourceException | IllegalArgumentException e) {
            throw new DaoException(e);
        }
    }
}
