package com.epam.library.dao.impl;

import com.epam.library.dao.TypeDAO;
import com.epam.library.dao.connection_pool.ConnectionPool;
import com.epam.library.dao.connection_pool.exception.ConnectionPoolException;
import com.epam.library.dao.exception.DAOException;
import com.epam.library.entity.book.Type;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MYSQLTypeDAO implements TypeDAO {
    private static final Logger logger = Logger.getLogger(MYSQLTypeDAO.class.getName());

    private static final ConnectionPool connectionPool = ConnectionPool.getInstance();
    private static final String MAX_ID_TYPE = "MAX(id)";
    private static final String ID = "id";
    private static final String NAME = "name";
    private static final String TYPE_IS_USED = "type_is_used";

    private static final String GET_TYPE_BY_NAME = "SELECT * FROM types WHERE name=?";
    private static final String GET_MAX_ID_TYPE = "SELECT MAX(id) FROM types";
    private static final String INSERT_TYPE = "INSERT INTO types (id, name) VALUES (?,?)";
    private static final String SELECT_TYPE = "SELECT * FROM types WHERE id=?";
    private static final String UPDATE_TYPE = "UPDATE types SET name=? WHERE id=?";
    private static final String GET_BOOKS = "SELECT * FROM books WHERE types_id=?";
    private static final String DELETE_TYPE = "DELETE FROM types WHERE id=?";
    private static final String SELECT_TYPES =
            "SELECT id, name, CASE WHEN EXISTS(SELECT * FROM books WHERE types_id=types.id) THEN 1 ELSE 0 END AS type_is_used FROM types ORDER BY name";

    public MYSQLTypeDAO() {}

    @Override
    public boolean checkType(Type type) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = connectionPool.getConnection();

            preparedStatement = connection.prepareStatement(GET_TYPE_BY_NAME);
            preparedStatement.setString(1, type.getName());
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                if (type.getId() == 0 || type.getId() != resultSet.getInt(ID)) {
                    return false;
                }
            }
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
    public void addType(Type type) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = connectionPool.getConnection();

            preparedStatement = connection.prepareStatement(GET_MAX_ID_TYPE);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                type.setId(resultSet.getInt(MAX_ID_TYPE) + 1);
            }

            preparedStatement = connection.prepareStatement(INSERT_TYPE);
            preparedStatement.setInt(1, type.getId());
            preparedStatement.setString(2, type.getName());
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
    public Type getType(int typeID) throws DAOException {
        Type type = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = connectionPool.getConnection();

            preparedStatement = connection.prepareStatement(SELECT_TYPE);
            preparedStatement.setInt(1, typeID);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                type = new Type();
                type.setId(typeID);
                type.setName(resultSet.getString(NAME));
            }
            return type;
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
    public void updateType(Type type) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = connectionPool.getConnection();

            preparedStatement = connection.prepareStatement(UPDATE_TYPE);
            preparedStatement.setString(1, type.getName());
            preparedStatement.setInt(2, type.getId());
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
    public boolean deleteType(int typeID) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = connectionPool.getConnection();

            preparedStatement = connection.prepareStatement(GET_BOOKS);
            preparedStatement.setInt(1, typeID);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return false;
            }

            preparedStatement = connection.prepareStatement(DELETE_TYPE);
            preparedStatement.setInt(1, typeID);
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
    public List<Type> getTypesList() throws DAOException {
        List<Type> types = new ArrayList<>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = connectionPool.getConnection();

            preparedStatement = connection.prepareStatement(SELECT_TYPES);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Type type = new Type();
                type.setId(resultSet.getInt(ID));
                type.setName(resultSet.getString(NAME));
                type.setTypeIsUsed(resultSet.getBoolean(TYPE_IS_USED));
                types.add(type);
            }
            return types;
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
