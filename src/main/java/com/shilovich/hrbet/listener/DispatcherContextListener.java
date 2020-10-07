package com.shilovich.hrbet.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class DispatcherContextListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        // TODO: 07.10.2020 ConnectionPool creation
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        // TODO: 07.10.2020 ConnectionPool destroy
    }
}
