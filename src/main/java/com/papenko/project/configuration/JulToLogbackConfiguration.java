package com.papenko.project.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.bridge.SLF4JBridgeHandler;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class JulToLogbackConfiguration implements ServletContextListener {
    private static final Logger LOGGER = LoggerFactory.getLogger(JulToLogbackConfiguration.class);

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        LOGGER.debug("Bridging Java Util Logging to Logback");
        SLF4JBridgeHandler.removeHandlersForRootLogger();
        SLF4JBridgeHandler.install();
    }
}
