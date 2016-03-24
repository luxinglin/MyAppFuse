package com.h3c.activiti.listener;

import org.activiti.engine.ProcessEngines;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * Created by l61989 on 2016/3/24.
 */
public class ProcessEnginesServletContextListener implements ServletContextListener {

    public void contextInitialized(ServletContextEvent servletContextEvent) {
        ProcessEngines.init();
    }

    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        ProcessEngines.destroy();
    }

}
