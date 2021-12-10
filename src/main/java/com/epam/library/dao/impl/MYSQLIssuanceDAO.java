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
    private static final String INSERT_ISSUANCE = "INSERT INTO issuance (id, instance_id, reader_id, date_issue, date_return, date_return_planed, lost) VALUES (?,?,?,?,?,?,?)";
    private static final String SELECT_ISSUANCE = "SELECT * FROM issuance WHERE id=?";
    private static final String UPDATE_ISSUANCE = "UPDATE issuance SET instance_id=?, reader_id=?, date_issue=?, date_return=?, date_return_planed=?, lost=? WHERE id=?";
    private static final String DELETE_ISSUANCE = "DELETE FROM issuance WHERE id=?";

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
            preparedStatement.setDate(4, issuance.getIssueDate());
            preparedStatement.setDate(5, issuance.getReturnDate());
            preparedStatement.setDate(6, issuance.getReturnPlanedDate());
            preparedStatement.setBoolean(7, issuance.isLost());
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
}
