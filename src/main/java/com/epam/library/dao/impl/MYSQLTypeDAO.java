package com.epam.library.dao.impl;

import com.epam.library.dao.TypeDAO;
import com.epam.library.dao.connection_pool.ConnectionPool;
import com.epam.library.dao.exception.DAOException;
import com.epam.library.entity.book.Type;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MYSQLTypeDAO implements TypeDAO {

    private static final ConnectionPool connectionPool = ConnectionPool.getInstance();
    private static final String COUNT_TYPES = "count_types";
    private static final String COUNT_BOOKS = "count_books";
    private static final String MAX_ID_TYPE = "MAX(id)";
    private static final String ID = "id";
    private static final String NAME = "name";

    private static final String GET_TYPE_BY_NAME = "SELECT COUNT(1) AS count_types FROM types WHERE name=?";
    private static final String GET_MAX_ID_TYPE = "SELECT MAX(id) FROM types";
    private static final String INSERT_TYPE = "INSERT INTO types (id, name) VALUES (?,?)";
    private static final String SELECT_TYPE = "SELECT * FROM types WHERE id=?";
    private static final String UPDATE_TYPE = "UPDATE types SET name=? WHERE id=?";
    private static final String GET_BOOKS = "SELECT COUNT(1) AS count_books FROM books WHERE types_id=?";
    private static final String DELETE_TYPE = "DELETE FROM types WHERE id=?";
    private static final String SELECT_TYPES = "SELECT * FROM types ORDER BY name";

    public MYSQLTypeDAO() {}

    @Override
    public void addType(Type type) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = connectionPool.getConnection();

            preparedStatement = connection.prepareStatement(GET_TYPE_BY_NAME);
            preparedStatement.setString(1, type.getName());
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                if (resultSet.getInt(COUNT_TYPES) == 0) {
                    preparedStatement = connection.prepareStatement(GET_MAX_ID_TYPE);
                    resultSet = preparedStatement.executeQuery();
                    if (resultSet.next()) {
                        type.setId(resultSet.getInt(MAX_ID_TYPE) + 1);
                    }

                    preparedStatement = connection.prepareStatement(INSERT_TYPE);
                    preparedStatement.setInt(1, type.getId());
                    preparedStatement.setString(2, type.getName());
                    preparedStatement.executeUpdate();
                } else {
                    //TODO logger
                }
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            connectionPool.releaseConnection(connection);
            connectionPool.closeConnection(resultSet, preparedStatement);
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
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            connectionPool.releaseConnection(connection);
            connectionPool.closeConnection(resultSet, preparedStatement);
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
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            connectionPool.releaseConnection(connection);
            connectionPool.closeConnection(preparedStatement);
        }
    }

    @Override
    public void deleteType(Type type) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = connectionPool.getConnection();

            preparedStatement = connection.prepareStatement(GET_BOOKS);
            preparedStatement.setInt(1, type.getId());
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                if (resultSet.getInt(COUNT_BOOKS) == 0) {
                    preparedStatement = connection.prepareStatement(DELETE_TYPE);
                    preparedStatement.setInt(1, type.getId());
                    preparedStatement.executeUpdate();
                } else {
                    //TODO logger
                }
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            connectionPool.releaseConnection(connection);
            connectionPool.closeConnection(resultSet, preparedStatement);
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
                types.add(type);
            }
            return types;
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            connectionPool.releaseConnection(connection);
            connectionPool.closeConnection(resultSet, preparedStatement);
        }
    }
}
