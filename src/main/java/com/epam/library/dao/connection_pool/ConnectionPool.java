package com.epam.library.dao.connection_pool;

import com.epam.library.dao.connection_pool.exception.ConnectionPoolException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public final class ConnectionPool {
    private static final ConnectionPool instance = new ConnectionPool();

    private final String driverName;
    private final String url;
    private final String user;
    private final String password;
    private final List<Connection> connectionPool;
    private final List<Connection> usedConnections;
    private int POOL_SIZE;
//    private static final int TIMEOUT = 5;

    public static ConnectionPool getInstance() {
        return instance;
    }

    private ConnectionPool() {
        DBResourceManager dbResourceManager = DBResourceManager.getInstance();
        driverName = dbResourceManager.getValue(DBParameter.DB_DRIVER);
        url = dbResourceManager.getValue(DBParameter.DB_URL);
        user = dbResourceManager.getValue(DBParameter.DB_USER);
        password = dbResourceManager.getValue(DBParameter.DB_PASSWORD);

        try {
            POOL_SIZE = Integer.parseInt(dbResourceManager.getValue(DBParameter.DB_POOL_SIZE));
        } catch (NumberFormatException e) {
            POOL_SIZE = 10;
        } finally {
            connectionPool = new ArrayList<>(POOL_SIZE);
            usedConnections = new ArrayList<>(POOL_SIZE);
        }
    }

    public void create() throws ConnectionPoolException {
        try {
            Class.forName(driverName);
            for (int i = 0; i < POOL_SIZE; i++) {
                connectionPool.add(DriverManager.getConnection(url, user, password));
            }
        } catch (SQLException e) {
            throw new ConnectionPoolException("SQLException in ConnectionPool in create().", e);
        } catch (ClassNotFoundException e) {
            throw new ConnectionPoolException("Can't find database driver class in create().", e);
        }
    }

    public synchronized Connection getConnection() {
        Connection connection = connectionPool.remove(connectionPool.size() - 1);
        usedConnections.add(connection);
        return connection;
    }

    public synchronized void releaseConnection(Connection connection) {
        connectionPool.add(connection);
        usedConnections.remove(connection);
    }

    public void closeConnection(ResultSet resultSet, PreparedStatement preparedStatement) {
        if (resultSet != null) {
            try {
                resultSet.close();
            } catch (SQLException e) {
                //TODO logger
            }
        }
        if (preparedStatement != null) {
            try {
                preparedStatement.close();
            } catch (SQLException e) {
                //TODO logger
            }
        }
    }

    public void closeConnection(PreparedStatement preparedStatement) {
        if (preparedStatement != null) {
            try {
                preparedStatement.close();
            } catch (SQLException e) {
                //TODO logger
            }
        }
    }

    public void shutdown() throws ConnectionPoolException {
        try {
            for (Connection connection : usedConnections) {
                releaseConnection(connection);
            }
            for (Connection connection : connectionPool) {
                connection.close();
            }
            connectionPool.clear();
        } catch (SQLException e) {
            throw new ConnectionPoolException("SQLException in ConnectionPool in shutdown().", e);
        }
    }
}
