package com.epam.library.dao.connection_pool;

import com.epam.library.dao.connection_pool.exception.ConnectionPoolException;

import java.sql.*;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public final class ConnectionPool {
    private static final ConnectionPool instance = new ConnectionPool();

    private final String driverName;
    private final String url;
    private final String user;
    private final String password;
    private final BlockingQueue<Connection> connectionPool;
    private final BlockingQueue<Connection> usedConnections;
    private int POOL_SIZE;

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
            connectionPool = new ArrayBlockingQueue<>(POOL_SIZE);
            usedConnections = new ArrayBlockingQueue<>(POOL_SIZE);
        }
    }

    public void create() throws ConnectionPoolException {
        try {
            Class.forName(driverName);
            for (int i = 0; i < POOL_SIZE; i++) {
                connectionPool.add(DriverManager.getConnection(url, user, password));
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new ConnectionPoolException(e);
        }
    }

    public Connection getConnection() throws ConnectionPoolException {
        try {
            Connection connection = connectionPool.take();
            usedConnections.put(connection);
            return connection;
        } catch (InterruptedException e) {
            throw new ConnectionPoolException(e);
        }
    }

    public void releaseConnection(Connection connection) {
        if (connection != null) {
            usedConnections.remove(connection);
            connectionPool.add(connection);
        }
    }

    public void closeConnection(ResultSet resultSet, PreparedStatement preparedStatement) throws ConnectionPoolException {
        try {
            if (resultSet != null) {
                resultSet.close();
            }
        } catch (SQLException e) {
            throw new ConnectionPoolException(e);
        } finally {
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
            } catch (SQLException e) {
                throw new ConnectionPoolException(e);
            }
        }
    }

    public void closeConnection(PreparedStatement preparedStatement) throws ConnectionPoolException {
        try {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
        } catch (SQLException e) {
            throw new ConnectionPoolException(e);
        }
    }

    public void shutdown() throws ConnectionPoolException {
        try {
            Connection connection;
            while ((connection = usedConnections.poll()) != null) {
                if (!connection.getAutoCommit()) {
                    connection.commit();
                }
                connection.close();
            }
            while ((connection = connectionPool.poll()) != null) {
                if (!connection.getAutoCommit()) {
                    connection.commit();
                }
                connection.close();
            }
        } catch (SQLException e) {
            throw new ConnectionPoolException(e);
        }
    }
}
