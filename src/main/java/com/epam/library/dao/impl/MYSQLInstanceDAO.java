package com.epam.library.dao.impl;

import com.epam.library.dao.InstanceDAO;
import com.epam.library.dao.connection_pool.ConnectionPool;
import com.epam.library.dao.exception.DAOException;
import com.epam.library.entity.Instance;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class MYSQLInstanceDAO implements InstanceDAO {

    private static final ConnectionPool connectionPool = ConnectionPool.getInstance();
    private static final String COUNT_INSTANCES = "count_instances";
    private static final String COUNT_ISSUANCE = "count_issuance";
    private static final String COUNT_RESERVATIONS = "count_reservations";
    private static final String MAX_ID_INSTANCE = "MAX(id)";
    private static final String ID = "id";
    private static final String BOOKS_ID = "books_id";
    private static final String NUMBER = "number";
    private static final String HALLS_ID = "halls_id";
    private static final String DATE_RECEIVED = "date_received";
    private static final String DATE_WRITE_OFF = "date_write_off";

    private static final String GET_INSTANCE_BY_NUMBER = "SELECT COUNT(1) AS count_instances FROM instances WHERE number=?";
    private static final String GET_MAX_ID_INSTANCE = "SELECT MAX(id) FROM instances";
    private static final String INSERT_INSTANCE = "INSERT INTO instances (id, books_id, number, halls_id, date_received, date_write_off) VALUES (?,?,?,?,?,?)";
    private static final String SELECT_INSTANCE = "SELECT * FROM instances WHERE id=?";
    private static final String UPDATE_INSTANCE = "UPDATE instances SET books_id=?, number=?, halls_id=?, date_received=?, date_write_off=? WHERE id=?";
    private static final String GET_ISSUANCE = "SELECT COUNT(1) AS count_issuance FROM issuance WHERE instances_id=?";
    private static final String GET_RESERVATIONS = "SELECT COUNT(1) AS count_reservations FROM instances_reservation WHERE instances_id=?";
    private static final String DELETE_INSTANCE = "DELETE FROM instances WHERE id=?";
    private static final String SELECT_INSTANCES = "SELECT * FROM instances";

    public MYSQLInstanceDAO() {}

    @Override
    public void addInstance(Instance instance) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = connectionPool.getConnection();

            preparedStatement = connection.prepareStatement(GET_INSTANCE_BY_NUMBER);
            preparedStatement.setString(1, instance.getNumber());
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                if (resultSet.getInt(COUNT_INSTANCES) == 0) {
                    preparedStatement = connection.prepareStatement(GET_MAX_ID_INSTANCE);
                    resultSet = preparedStatement.executeQuery();
                    if (resultSet.next()) {
                        instance.setId(resultSet.getInt(MAX_ID_INSTANCE) + 1);
                    }

                    preparedStatement = connection.prepareStatement(INSERT_INSTANCE);
                    preparedStatement.setInt(1, instance.getId());
                    preparedStatement.setInt(2, instance.getBookID());
                    preparedStatement.setString(3, instance.getNumber());
                    preparedStatement.setInt(4, instance.getHallID());
                    preparedStatement.setDate(5, instance.getReceivedDate());
                    preparedStatement.setDate(6, instance.getWriteOffDate());
                    preparedStatement.executeUpdate();
                } else {
                    //TODO logger
                }
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
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
            if (connection != null) {
                connectionPool.releaseConnection(connection);
            }
        }
    }

    @Override
    public Instance getInstance(int instanceID) throws DAOException {
        Instance instance = new Instance();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = connectionPool.getConnection();

            preparedStatement = connection.prepareStatement(SELECT_INSTANCE);
            preparedStatement.setInt(1, instanceID);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                instance.setId(instanceID);
                instance.setBookID(resultSet.getInt(BOOKS_ID));
                instance.setNumber(resultSet.getString(NUMBER));
                instance.setHallID(resultSet.getInt(HALLS_ID));
                instance.setReceivedDate(resultSet.getDate(DATE_RECEIVED));
                instance.setWriteOffDate(resultSet.getDate(DATE_WRITE_OFF));
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
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
            if (connection != null) {
                connectionPool.releaseConnection(connection);
            }
        }
        return instance;
    }

    @Override
    public void updateInstance(Instance instance) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = connectionPool.getConnection();

            preparedStatement = connection.prepareStatement(UPDATE_INSTANCE);
            preparedStatement.setInt(1, instance.getBookID());
            preparedStatement.setString(2, instance.getNumber());
            preparedStatement.setInt(3, instance.getHallID());
            preparedStatement.setDate(4, instance.getReceivedDate());
            preparedStatement.setDate(5, instance.getWriteOffDate());
            preparedStatement.setInt(6, instance.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    //TODO logger
                }
            }
            if (connection != null) {
                connectionPool.releaseConnection(connection);
            }
        }
    }

    @Override
    public void deleteInstance(Instance instance) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = connectionPool.getConnection();

            preparedStatement = connection.prepareStatement(GET_ISSUANCE);
            preparedStatement.setInt(1, instance.getId());
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                if (resultSet.getInt(COUNT_ISSUANCE) != 0) {
                    //TODO logger
                    return;
                }
            }

            preparedStatement = connection.prepareStatement(GET_RESERVATIONS);
            preparedStatement.setInt(1, instance.getId());
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                if (resultSet.getInt(COUNT_RESERVATIONS) != 0) {
                    //TODO logger
                    return;
                }
            }

            preparedStatement = connection.prepareStatement(DELETE_INSTANCE);
            preparedStatement.setInt(1, instance.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
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
            if (connection != null) {
                connectionPool.releaseConnection(connection);
            }
        }
    }

    @Override
    public List<Instance> getInstancesByCriteria() throws DAOException {
        return null;
    }
}
