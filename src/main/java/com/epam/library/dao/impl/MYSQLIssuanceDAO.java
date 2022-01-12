package com.epam.library.dao.impl;

import com.epam.library.dao.IssuanceDAO;
import com.epam.library.dao.connection_pool.ConnectionPool;
import com.epam.library.dao.exception.DAOException;
import com.epam.library.entity.Issuance;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MYSQLIssuanceDAO implements IssuanceDAO {

    private static final ConnectionPool connectionPool = ConnectionPool.getInstance();
    private static final String MAX_ID_ISSUANCE = "MAX(id)";
    private static final String ID = "id";
    private static final String INSTANCES_ID = "instances_id";
    private static final String READER_ID = "reader_id";
    private static final String DATE_ISSUE = "date_issue";
    private static final String DATE_RETURN = "date_return";
    private static final String DATE_RETURN_PLANED = "date_return_planed";
    private static final String LOST = "lost";

    private static final String GET_MAX_ID_ISSUANCE = "SELECT MAX(id) FROM issuance";
    private static final String INSERT_ISSUANCE = "INSERT INTO issuance (id, instances_id, reader_id, date_issue, date_return_planned, lost) VALUES (?,?,?,CURDATE(),DATE_ADD(CURDATE(), INTERVAL 30 DAY),0)";
    private static final String SELECT_ISSUANCE = "SELECT * FROM issuance WHERE id=?";
    private static final String UPDATE_ISSUANCE = "UPDATE issuance SET instance_id=?, reader_id=?, date_issue=?, date_return=?, date_return_planed=?, lost=? WHERE id=?";
    private static final String DELETE_ISSUANCE = "DELETE FROM issuance WHERE id=?";

    private static final String UPDATE_RETURN_ISSUANCE = "UPDATE issuance SET date_return=CURDATE() WHERE id=?";
    private static final String UPDATE_EXTEND_ISSUANCE = "UPDATE issuance SET date_return_planned=DATE_ADD(CURDATE(), INTERVAL 30 DAY) WHERE id=?";
    private static final String UPDATE_LOST_ISSUANCE = "UPDATE issuance SET lost= CASE WHEN IFNULL(lost,0)=0 THEN 1 ELSE 0 END WHERE id=?";

    public MYSQLIssuanceDAO() {}

    @Override
    public void addIssuance(Issuance issuance) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = connectionPool.getConnection();

            preparedStatement = connection.prepareStatement(GET_MAX_ID_ISSUANCE);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                issuance.setId(resultSet.getInt(MAX_ID_ISSUANCE) + 1);
            }

            preparedStatement = connection.prepareStatement(INSERT_ISSUANCE);
            preparedStatement.setInt(1, issuance.getId());
            preparedStatement.setInt(2, issuance.getInstanceID());
            preparedStatement.setInt(3, issuance.getReaderID());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            connectionPool.releaseConnection(connection);
            connectionPool.closeConnection(resultSet, preparedStatement);
        }
    }

    @Override
    public Issuance getIssuance(int issuanceID) throws DAOException {
        Issuance issuance = new Issuance();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = connectionPool.getConnection();

            preparedStatement = connection.prepareStatement(SELECT_ISSUANCE);
            preparedStatement.setInt(1, issuanceID);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                issuance.setId(issuanceID);
                issuance.setInstanceID(resultSet.getInt(INSTANCES_ID));
                issuance.setReaderID(resultSet.getInt(READER_ID));
                issuance.setIssueDate(resultSet.getDate(DATE_ISSUE));
                issuance.setReturnDate(resultSet.getDate(DATE_RETURN));
                issuance.setReturnPlanedDate(resultSet.getDate(DATE_RETURN_PLANED));
                issuance.setLost(resultSet.getBoolean(LOST));
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            connectionPool.releaseConnection(connection);
            connectionPool.closeConnection(resultSet, preparedStatement);
        }
        return issuance;
    }

    @Override
    public void updateIssuance(Issuance issuance) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = connectionPool.getConnection();

            preparedStatement = connection.prepareStatement(UPDATE_ISSUANCE);
            preparedStatement.setInt(1, issuance.getInstanceID());
            preparedStatement.setInt(2, issuance.getReaderID());
            preparedStatement.setDate(3, issuance.getIssueDate());
            preparedStatement.setDate(4, issuance.getReturnDate());
            preparedStatement.setDate(5, issuance.getReturnPlanedDate());
            preparedStatement.setBoolean(6, issuance.isLost());
            preparedStatement.setInt(7, issuance.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            connectionPool.releaseConnection(connection);
            connectionPool.closeConnection(preparedStatement);
        }
    }

    @Override
    public void deleteIssuance(Issuance issuance) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = connectionPool.getConnection();

            preparedStatement = connection.prepareStatement(DELETE_ISSUANCE);
            preparedStatement.setInt(1, issuance.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            connectionPool.releaseConnection(connection);
            connectionPool.closeConnection(preparedStatement);
        }
    }

    @Override
    public void updateReturnIssuance(int issuanceID) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = connectionPool.getConnection();

            preparedStatement = connection.prepareStatement(UPDATE_RETURN_ISSUANCE);
            preparedStatement.setInt(1, issuanceID);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            connectionPool.releaseConnection(connection);
            connectionPool.closeConnection(preparedStatement);
        }
    }

    @Override
    public void updateExtendIssuance(int issuanceID) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = connectionPool.getConnection();

            preparedStatement = connection.prepareStatement(UPDATE_EXTEND_ISSUANCE);
            preparedStatement.setInt(1, issuanceID);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            connectionPool.releaseConnection(connection);
            connectionPool.closeConnection(preparedStatement);
        }
    }

    @Override
    public void updateLostIssuance(int issuanceID) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = connectionPool.getConnection();

            preparedStatement = connection.prepareStatement(UPDATE_LOST_ISSUANCE);
            preparedStatement.setInt(1, issuanceID);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            connectionPool.releaseConnection(connection);
            connectionPool.closeConnection(preparedStatement);
        }
    }
}
