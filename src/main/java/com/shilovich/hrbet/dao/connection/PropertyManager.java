package com.shilovich.hrbet.dao.connection;

import com.shilovich.hrbet.exception.DaoException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

import static java.util.ResourceBundle.getBundle;

class PropertyManager {
    private static final Logger logger = LogManager.getLogger(PropertyManager.class);

    private static final String BUNDLE_NAME = "project";
    public static final String URL = "database.url";
    public static final String USERNAME = "database.username";
    public static final String PASSWORD = "database.password";
    public static final String INITIAL_POOL_SIZE = "database.initialPoolSize";
    public static final String MAX_TIMEOUT = "database.maxTimeout";
    public static final String MAX_OPEN_STATEMENTS = "database.maxOpenStatements";
    public static final String DRIVER_CLASS_NAME = "database.driverClassName";

    public String getProperty(String key) throws DaoException {
        try {
            ResourceBundle resourceBundle = getBundle(BUNDLE_NAME);
            return resourceBundle.getString(key);
        } catch (MissingResourceException | IllegalArgumentException e) {
            logger.error("Property with name" + key + " does not find!");
            throw new DaoException("Not find dat property", e);
        }
    }
}