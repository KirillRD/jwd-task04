package com.epam.library.controller.listener;

import com.epam.library.controller.listener.exception.ContextListenerException;
import com.epam.library.dao.connection_pool.ConnectionPool;
import com.epam.library.dao.connection_pool.exception.ConnectionPoolException;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

public final class ContextListener implements ServletContextListener {
    private static final Logger logger = Logger.getLogger(ContextListener.class.getName());
    private static final String ERROR_CREATING_CONNECTION_POOL = "Error creating connection pool";
    private static final String ERROR_CLOSING_CONNECTION_POOL = "Error closing connection pool";

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        try {
            ConnectionPool.getInstance().create();
        } catch (ConnectionPoolException e) {
            logger.log(Level.FATAL, ERROR_CREATING_CONNECTION_POOL);
            throw new ContextListenerException(ERROR_CREATING_CONNECTION_POOL, e);
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        try {
            ConnectionPool.getInstance().shutdown();
        } catch (ConnectionPoolException e) {
            logger.log(Level.FATAL, ERROR_CLOSING_CONNECTION_POOL);
            throw new ContextListenerException(ERROR_CLOSING_CONNECTION_POOL, e);
        }
    }
}
