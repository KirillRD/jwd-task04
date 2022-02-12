package com.epam.library.dao.impl;

import com.epam.library.dao.InstanceDAO;
import com.epam.library.dao.connection_pool.ConnectionPool;
import com.epam.library.dao.connection_pool.exception.ConnectionPoolException;
import com.epam.library.dao.exception.DAOException;
import com.epam.library.entity.instance.BookInstance;
import com.epam.library.entity.Instance;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MYSQLInstanceDAO implements InstanceDAO {
    private static final Logger logger = Logger.getLogger(MYSQLInstanceDAO.class.getName());

    private static final ConnectionPool connectionPool = ConnectionPool.getInstance();
    private static final String COUNT_ISSUANCE = "count_issuance";
    private static final String COUNT_RESERVATIONS = "count_reservations";
    private static final String MAX_ID_INSTANCE = "MAX(id)";
    private static final String ID = "id";
    private static final String NUMBER = "number";
    private static final String HALLS_ID = "halls_id";
    private static final String DATE_RECEIVED = "date_received";
    private static final String DATE_WRITE_OFF = "date_write_off";
    private static final String HALL_NAME = "hall_name";
    private static final String STATUS = "status";

    private static final String GET_INSTANCE_BY_NUMBER = "SELECT * FROM instances WHERE number=?";
    private static final String GET_MAX_ID_INSTANCE = "SELECT MAX(id) FROM instances";
    private static final String INSERT_INSTANCE = "INSERT INTO instances (id, books_id, number, halls_id, date_received, date_write_off) VALUES (?,?,?,?,?,?)";
    private static final String UPDATE_INSTANCE = "UPDATE instances SET books_id=?, number=?, halls_id=?, date_received=?, date_write_off=? WHERE id=?";
    private static final String GET_ISSUANCE = "SELECT COUNT(1) AS count_issuance FROM issuance WHERE instances_id=?";
    private static final String GET_RESERVATIONS = "SELECT COUNT(1) AS count_reservations FROM reservation WHERE instances_id=?";
    private static final String DELETE_INSTANCE = "DELETE FROM instances WHERE id=?";
    private static final String BOOK_INSTANCES =
            "SELECT instances.id, instances.number, instances.halls_id, halls.name AS hall_name, instances.date_received, instances.date_write_off, " +
            "  CASE WHEN date_write_off IS NOT NULL THEN 'WRITE OFF' " +
            "       WHEN EXISTS(SELECT * FROM issuance WHERE instances_id=instances.id AND lost=1) THEN 'LOST' " +
            "       WHEN EXISTS(SELECT * FROM issuance WHERE instances_id=instances.id AND date_return IS NULL AND lost=0) THEN 'ISSUED' " +
            "       WHEN EXISTS(SELECT * FROM reservation WHERE instances_id=instances.id AND (status='RESERVED' OR status='READY')) THEN 'RESERVED' " +
            "       ELSE 'FREE' " +
            "  END AS status " +
            "FROM instances " +
            "INNER JOIN halls ON instances.halls_id=halls.id " +
            "WHERE books_id=?";
    private static final String GET_BOOK_INSTANCE =
            "SELECT instances.id, instances.number, instances.halls_id, halls.name AS hall_name, instances.date_received, instances.date_write_off " +
            "FROM instances " +
            "INNER JOIN halls ON instances.halls_id=halls.id " +
            "WHERE instances.id=?";

    public MYSQLInstanceDAO() {}

    @Override
    public boolean instanceNumberExists(int instanceID, String number) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = connectionPool.getConnection();

            preparedStatement = connection.prepareStatement(GET_INSTANCE_BY_NUMBER);
            preparedStatement.setString(1, number);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                if (instanceID == 0 || instanceID != resultSet.getInt(ID)) {
                    return true;
                }
            }
            return false;
        } catch (SQLException | ConnectionPoolException e) {
            throw new DAOException(e);
        } finally {
            try {
                connectionPool.closeConnection(resultSet, preparedStatement, connection);
            } catch (ConnectionPoolException e) {
                logger.error("Error closing resources", e);
            }
        }
    }

    @Override
    public void addInstance(Instance instance) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = connectionPool.getConnection();

            preparedStatement = connection.prepareStatement(GET_MAX_ID_INSTANCE);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                instance.setId(resultSet.getInt(MAX_ID_INSTANCE) + 1);
            }

            preparedStatement = connection.prepareStatement(INSERT_INSTANCE);
            preparedStatement.setInt(1, instance.getId());
            preparedStatement.setInt(2, instance.getBookID());
            preparedStatement.setString(3, instance.getNumber());
            preparedStatement.setInt(4, Integer.parseInt(instance.getHallID()));
            preparedStatement.setDate(5, Date.valueOf(instance.getReceivedDate()));
            preparedStatement.setDate(6, instance.getWriteOffDate().isEmpty() ? null : Date.valueOf(instance.getWriteOffDate()));
            preparedStatement.executeUpdate();
        } catch (SQLException | ConnectionPoolException e) {
            throw new DAOException(e);
        } finally {
            try {
                connectionPool.closeConnection(resultSet, preparedStatement, connection);
            } catch (ConnectionPoolException e) {
                logger.error("Error closing resources", e);
            }
        }
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
            preparedStatement.setInt(3, Integer.parseInt(instance.getHallID()));
            preparedStatement.setDate(4, Date.valueOf(instance.getReceivedDate()));
            preparedStatement.setDate(5, instance.getWriteOffDate().isEmpty() ? null : Date.valueOf(instance.getWriteOffDate()));
            preparedStatement.setInt(6, instance.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException | ConnectionPoolException e) {
            throw new DAOException(e);
        } finally {
            try {
                connectionPool.closeConnection(preparedStatement, connection);
            } catch (ConnectionPoolException e) {
                logger.error("Error closing resources", e);
            }
        }
    }

    @Override
    public boolean deleteInstance(int instanceID) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = connectionPool.getConnection();

            preparedStatement = connection.prepareStatement(GET_ISSUANCE);
            preparedStatement.setInt(1, instanceID);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next() && resultSet.getInt(COUNT_ISSUANCE) != 0) {
                return false;
            }

            preparedStatement = connection.prepareStatement(GET_RESERVATIONS);
            preparedStatement.setInt(1, instanceID);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next() && resultSet.getInt(COUNT_RESERVATIONS) != 0) {
                return false;
            }

            preparedStatement = connection.prepareStatement(DELETE_INSTANCE);
            preparedStatement.setInt(1, instanceID);
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException | ConnectionPoolException e) {
            throw new DAOException(e);
        } finally {
            try {
                connectionPool.closeConnection(resultSet, preparedStatement, connection);
            } catch (ConnectionPoolException e) {
                logger.error("Error closing resources", e);
            }
        }
    }

    @Override
    public BookInstance getBookInstance(int instanceID) throws DAOException {
        BookInstance bookInstance = new BookInstance();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = connectionPool.getConnection();

            preparedStatement = connection.prepareStatement(GET_BOOK_INSTANCE);
            preparedStatement.setInt(1, instanceID);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                bookInstance.setId(resultSet.getInt(ID));
                bookInstance.setNumber(resultSet.getString(NUMBER));
                bookInstance.setHallID(resultSet.getInt(HALLS_ID));
                bookInstance.setHallName(resultSet.getString(HALL_NAME));
                bookInstance.setReceivedDate(resultSet.getDate(DATE_RECEIVED));
                bookInstance.setWriteOffDate(resultSet.getDate(DATE_WRITE_OFF));
            }
            return bookInstance;
        } catch (SQLException | ConnectionPoolException e) {
            throw new DAOException(e);
        } finally {
            try {
                connectionPool.closeConnection(resultSet, preparedStatement, connection);
            } catch (ConnectionPoolException e) {
                logger.error("Error closing resources", e);
            }
        }
    }

    @Override
    public List<BookInstance> getBookInstances(int bookID) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        List<BookInstance> bookInstances = new ArrayList<>();
        try {
            connection = connectionPool.getConnection();

            preparedStatement = connection.prepareStatement(BOOK_INSTANCES);
            preparedStatement.setInt(1, bookID);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                BookInstance bookInstance = new BookInstance();
                bookInstance.setId(resultSet.getInt(ID));
                bookInstance.setNumber(resultSet.getString(NUMBER));
                bookInstance.setHallID(resultSet.getInt(HALLS_ID));
                bookInstance.setHallName(resultSet.getString(HALL_NAME));
                bookInstance.setReceivedDate(resultSet.getDate(DATE_RECEIVED));
                bookInstance.setWriteOffDate(resultSet.getDate(DATE_WRITE_OFF));
                bookInstance.setStatus(resultSet.getString(STATUS));

                preparedStatement = connection.prepareStatement(GET_ISSUANCE);
                preparedStatement.setInt(1, bookInstance.getId());
                ResultSet resultSetIssuance = preparedStatement.executeQuery();
                preparedStatement = connection.prepareStatement(GET_RESERVATIONS);
                preparedStatement.setInt(1, bookInstance.getId());
                ResultSet resultSetReservation = preparedStatement.executeQuery();
                resultSetIssuance.next();
                resultSetReservation.next();
                if (resultSetIssuance.getInt(COUNT_ISSUANCE) != 0 || resultSetReservation.getInt(COUNT_RESERVATIONS) != 0) {
                    bookInstance.setInstanceIsUsed(true);
                }
                resultSetIssuance.close();
                resultSetReservation.close();

                bookInstances.add(bookInstance);
            }
            return bookInstances;
        } catch (SQLException | ConnectionPoolException e) {
            throw new DAOException(e);
        } finally {
            try {
                connectionPool.closeConnection(resultSet, preparedStatement, connection);
            } catch (ConnectionPoolException e) {
                logger.error("Error closing resources", e);
            }
        }
    }
}
