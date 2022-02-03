package com.epam.library.dao.impl;

import com.epam.library.dao.IssuanceDAO;
import com.epam.library.dao.connection_pool.ConnectionPool;
import com.epam.library.dao.connection_pool.exception.ConnectionPoolException;
import com.epam.library.dao.exception.DAOException;
import com.epam.library.entity.Issuance;
import com.epam.library.constant.IssuanceOperation;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class MYSQLIssuanceDAO implements IssuanceDAO {
    private static final Logger logger = Logger.getLogger(MYSQLIssuanceDAO.class.getName());

    private static final ConnectionPool connectionPool = ConnectionPool.getInstance();
    private static final String MAX_ID_ISSUANCE = "MAX(id)";

    private static final String GET_MAX_ID_ISSUANCE = "SELECT MAX(id) FROM issuance";
    private static final String GET_FREE_INSTANCE =
            "SELECT id " +
            "FROM instances " +
            "WHERE instances.id=? AND date_write_off IS NULL AND " +
            "NOT EXISTS(SELECT * FROM issuance WHERE instances_id=instances.id AND (date_return IS NULL OR lost=1)) AND " +
            "NOT EXISTS(SELECT * FROM reservation WHERE instances_id=instances.id AND (status='RESERVED' OR status='READY'))";
    private static final String GET_NAMES_NOT_ISSUED_BOOKS =
            "SELECT books.name " +
            "FROM instances INNER JOIN books ON instances.books_id = books.id " +
            "WHERE instances.id IN ";
    private static final String NAME = "name";
    private static final String COMMA = ", ";
    private static final String BRACKET_LEFT = " (";
    private static final String BRACKET_RIGHT = ") ";
    private static final String INSERT_ISSUANCE =
            "INSERT INTO issuance (id, instances_id, reader_id, date_issue, date_return_planned, lost) " +
            "VALUES (?,?,?,CURDATE(),DATE_ADD(CURDATE(), INTERVAL CASE WHEN (SELECT halls_id FROM instances WHERE id=?)=1 THEN (SELECT CAST(param_value AS UNSIGNED) FROM config WHERE param_name = 'days_of_issue') ELSE 0 END DAY),0)";

    private static final String UPDATE_RETURN_ISSUANCE =
            "UPDATE issuance SET date_return=CURDATE(), " +
            "price = CASE WHEN lost=1 THEN (SELECT price FROM books INNER JOIN instances ON books.id=instances.books_id WHERE instances.id=issuance.instances_id) ELSE 0 END, " +
            "rental_price = CASE WHEN ((SELECT halls_id FROM instances WHERE id=issuance.instances_id) = 2 AND date_return_planned<date_return) " +
            "THEN IFNULL(DATEDIFF(date_return,date_return_planned),0) * (SELECT CAST(param_value AS decimal(4,2)) FROM config WHERE param_name = 'rental_price') ELSE 0 END " +
            "WHERE id=?";
    private static final String UPDATE_EXTEND_ISSUANCE =
            "UPDATE issuance SET date_return_planned=DATE_ADD(CURDATE(), INTERVAL 30 DAY) WHERE id=? AND (SELECT halls_id FROM instances WHERE id=issuance.instances_id)=1";
    private static final String UPDATE_LOST_ISSUANCE = "UPDATE issuance SET lost= CASE WHEN IFNULL(lost,0)=0 THEN 1 ELSE 0 END WHERE id=?";
    private static final Map<String, String> operations = Map.of(
            IssuanceOperation.RETURN.getOperation(), UPDATE_RETURN_ISSUANCE,
            IssuanceOperation.EXTEND.getOperation(), UPDATE_EXTEND_ISSUANCE,
            IssuanceOperation.LOST.getOperation(), UPDATE_LOST_ISSUANCE
    );

    public MYSQLIssuanceDAO() {}

    @Override
    public String addIssuance(List<Issuance> issues) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = connectionPool.getConnection();

            StringBuilder messageNotIssuedBooks = new StringBuilder();
            StringBuilder idNotIssuedBooks = new StringBuilder();


            for (Issuance issuance : issues) {
                preparedStatement = connection.prepareStatement(GET_FREE_INSTANCE);
                preparedStatement.setInt(1, issuance.getInstanceID());
                resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {

                    preparedStatement = connection.prepareStatement(GET_MAX_ID_ISSUANCE);
                    ResultSet resultSetIssuance = preparedStatement.executeQuery();
                    if (resultSetIssuance.next()) {
                        issuance.setId(resultSetIssuance.getInt(MAX_ID_ISSUANCE) + 1);
                    }
                    resultSetIssuance.close();

                    preparedStatement = connection.prepareStatement(INSERT_ISSUANCE);
                    preparedStatement.setInt(1, issuance.getId());
                    preparedStatement.setInt(2, issuance.getInstanceID());
                    preparedStatement.setInt(3, issuance.getReaderID());
                    preparedStatement.setInt(4, issuance.getInstanceID());
                    preparedStatement.executeUpdate();
                } else {
                    if (idNotIssuedBooks.toString().length() != 0) {
                        idNotIssuedBooks.append(COMMA);
                    }
                    idNotIssuedBooks.append(issuance.getInstanceID());
                }
            }

            if (idNotIssuedBooks.toString().length() != 0) {
                preparedStatement = connection.prepareStatement(GET_NAMES_NOT_ISSUED_BOOKS + BRACKET_LEFT + idNotIssuedBooks.toString() + BRACKET_RIGHT);
                resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    if (messageNotIssuedBooks.toString().length() != 0) {
                        messageNotIssuedBooks.append(COMMA);
                    }
                    messageNotIssuedBooks.append(resultSet.getString(NAME));
                }
            }
            return messageNotIssuedBooks.toString();
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
    public void updateConditionIssuance(List<String> issues, String operation) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = connectionPool.getConnection();

            connection.setAutoCommit(false);

            preparedStatement = connection.prepareStatement(operations.get(operation));
            for (String issuanceID : issues) {
                preparedStatement.setInt(1, Integer.parseInt(issuanceID));
                preparedStatement.executeUpdate();
            }

            connection.commit();
        } catch (SQLException | ConnectionPoolException e) {
            try {
                if (connection != null) {
                    connection.rollback();
                }
            } catch (SQLException exception) {
                logger.error("Error when rollback transaction", exception);
            }
            throw new DAOException(e);
        } finally {
            try {
                if (connection != null) {
                    connection.setAutoCommit(true);
                }
                connectionPool.closeConnection(preparedStatement, connection);
            } catch (ConnectionPoolException | SQLException e) {
                logger.error("Error closing resources", e);
            }
        }
    }
}
