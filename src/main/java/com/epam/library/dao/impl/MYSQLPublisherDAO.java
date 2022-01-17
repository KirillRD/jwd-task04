package com.epam.library.dao.impl;

import com.epam.library.dao.PublisherDAO;
import com.epam.library.dao.connection_pool.ConnectionPool;
import com.epam.library.dao.exception.DAOException;
import com.epam.library.entity.book.Publisher;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MYSQLPublisherDAO implements PublisherDAO {

    private static final ConnectionPool connectionPool = ConnectionPool.getInstance();
    private static final String COUNT_PUBLISHERS = "count_publishers";
    private static final String COUNT_BOOKS = "count_books";
    private static final String MAX_ID_PUBLISHER = "MAX(id)";
    private static final String ID = "id";
    private static final String NAME = "name";
    private static final String CITY = "city";

    private static final String GET_PUBLISHER_BY_NAME = "SELECT COUNT(1) AS count_publishers FROM publishers WHERE name=?";
    private static final String GET_MAX_ID_PUBLISHER = "SELECT MAX(id) FROM publishers";
    private static final String INSERT_PUBLISHER = "INSERT INTO publishers (id, name, city) VALUES (?,?,?)";
    private static final String SELECT_PUBLISHER = "SELECT * FROM publishers WHERE id=?";
    private static final String UPDATE_PUBLISHER = "UPDATE publishers SET name=?, city=? WHERE id=?";
    private static final String GET_BOOKS = "SELECT COUNT(1) AS count_books FROM books WHERE publishers_id=?";
    private static final String DELETE_PUBLISHER = "DELETE FROM publishers WHERE id=?";
    private static final String SELECT_PUBLISHERS = "SELECT * FROM publishers ORDER BY name";

    public MYSQLPublisherDAO() {}

    @Override
    public void addPublisher(Publisher publisher) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = connectionPool.getConnection();

            preparedStatement = connection.prepareStatement(GET_PUBLISHER_BY_NAME);
            preparedStatement.setString(1, publisher.getName());
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                if (resultSet.getInt(COUNT_PUBLISHERS) == 0) {
                    preparedStatement = connection.prepareStatement(GET_MAX_ID_PUBLISHER);
                    resultSet = preparedStatement.executeQuery();
                    if (resultSet.next()) {
                        publisher.setId(resultSet.getInt(MAX_ID_PUBLISHER) + 1);
                    }

                    preparedStatement = connection.prepareStatement(INSERT_PUBLISHER);
                    preparedStatement.setInt(1, publisher.getId());
                    preparedStatement.setString(2, publisher.getName());
                    preparedStatement.setString(3, publisher.getCity());
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
    public Publisher getPublisher(int publisherID) throws DAOException {
        Publisher publisher = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = connectionPool.getConnection();

            preparedStatement = connection.prepareStatement(SELECT_PUBLISHER);
            preparedStatement.setInt(1, publisherID);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                publisher = new Publisher();
                publisher.setId(publisherID);
                publisher.setName(resultSet.getString(NAME));
                publisher.setCity(resultSet.getString(CITY));
            }
            return publisher;
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            connectionPool.releaseConnection(connection);
            connectionPool.closeConnection(resultSet, preparedStatement);
        }
    }

    @Override
    public void updatePublisher(Publisher publisher) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = connectionPool.getConnection();

            preparedStatement = connection.prepareStatement(UPDATE_PUBLISHER);
            preparedStatement.setString(1, publisher.getName());
            preparedStatement.setString(2, publisher.getCity());
            preparedStatement.setInt(3, publisher.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            connectionPool.releaseConnection(connection);
            connectionPool.closeConnection(preparedStatement);
        }
    }

    @Override
    public void deletePublisher(Publisher publisher) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = connectionPool.getConnection();

            preparedStatement = connection.prepareStatement(GET_BOOKS);
            preparedStatement.setInt(1, publisher.getId());
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                if (resultSet.getInt(COUNT_BOOKS) == 0) {
                    preparedStatement = connection.prepareStatement(DELETE_PUBLISHER);
                    preparedStatement.setInt(1, publisher.getId());
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
    public List<Publisher> getPublishersList() throws DAOException {
        List<Publisher> publishers = new ArrayList<>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = connectionPool.getConnection();

            preparedStatement = connection.prepareStatement(SELECT_PUBLISHERS);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Publisher publisher = new Publisher();
                publisher.setId(resultSet.getInt(ID));
                publisher.setName(resultSet.getString(NAME));
                publisher.setCity(resultSet.getString(CITY));
                publishers.add(publisher);
            }
            return publishers;
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            connectionPool.releaseConnection(connection);
            connectionPool.closeConnection(resultSet, preparedStatement);
        }
    }
}
