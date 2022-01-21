package com.epam.library.dao.impl;

import com.epam.library.dao.AuthorDAO;
import com.epam.library.dao.connection_pool.ConnectionPool;
import com.epam.library.dao.connection_pool.exception.ConnectionPoolException;
import com.epam.library.dao.exception.DAOException;
import com.epam.library.entity.book.Author;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MYSQLAuthorDAO implements AuthorDAO {
    private static final Logger logger = Logger.getLogger(MYSQLAuthorDAO.class.getName());

    private static final ConnectionPool connectionPool = ConnectionPool.getInstance();
    private static final String COUNT_AUTHORS = "count_authors";
    private static final String COUNT_BOOKS = "count_books";
    private static final String MAX_ID_AUTHOR = "MAX(id)";
    private static final String ID = "id";
    private static final String LAST_NAME = "last_name";
    private static final String FIRST_NAME = "first_name";
    private static final String FATHER_NAME = "father_name";

    private static final String GET_AUTHOR_BY_NAMES = "SELECT COUNT(1) AS count_authors FROM authors WHERE last_name=? AND first_name=? AND (father_name IS NULL OR father_name=?)";
    private static final String GET_MAX_ID_AUTHOR = "SELECT MAX(id) FROM authors";
    private static final String INSERT_AUTHOR = "INSERT INTO authors (id, last_name, first_name, father_name) VALUES (?,?,?,?)";
    private static final String SELECT_AUTHOR = "SELECT * FROM authors WHERE id=?";
    private static final String UPDATE_AUTHOR = "UPDATE authors SET last_name=?, first_name=?, father_name=? WHERE id=?";
    private static final String GET_BOOKS = "SELECT COUNT(1) AS count_books FROM books_authors WHERE authors_id=?";
    private static final String DELETE_AUTHOR = "DELETE FROM authors WHERE id=?";
    private static final String SELECT_AUTHORS = "SELECT * FROM authors ORDER BY last_name, first_name, father_name";

    public MYSQLAuthorDAO() {}

    @Override
    public void addAuthor(Author author) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = connectionPool.getConnection();

            preparedStatement = connection.prepareStatement(GET_AUTHOR_BY_NAMES);
            preparedStatement.setString(1, author.getLastName());
            preparedStatement.setString(2, author.getFirstName());
            preparedStatement.setString(3, author.getFatherName());
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                if (resultSet.getInt(COUNT_AUTHORS) == 0) {
                    preparedStatement = connection.prepareStatement(GET_MAX_ID_AUTHOR);
                    resultSet = preparedStatement.executeQuery();
                    if (resultSet.next()) {
                        author.setId(resultSet.getInt(MAX_ID_AUTHOR) + 1);
                    }

                    preparedStatement = connection.prepareStatement(INSERT_AUTHOR);
                    preparedStatement.setInt(1, author.getId());
                    preparedStatement.setString(2, author.getLastName());
                    preparedStatement.setString(3, author.getFirstName());
                    preparedStatement.setString(4, author.getFatherName());
                    preparedStatement.executeUpdate();
                } else {
                    //TODO logger
                }
            }
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
    public Author getAuthor(int authorID) throws DAOException {
        Author author = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = connectionPool.getConnection();

            preparedStatement = connection.prepareStatement(SELECT_AUTHOR);
            preparedStatement.setInt(1, authorID);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                author = new Author();
                author.setId(authorID);
                author.setLastName(resultSet.getString(LAST_NAME));
                author.setFirstName(resultSet.getString(FIRST_NAME));
                author.setFatherName(resultSet.getString(FATHER_NAME));
            }
            return author;
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
    public void updateAuthor(Author author) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = connectionPool.getConnection();

            preparedStatement = connection.prepareStatement(UPDATE_AUTHOR);
            preparedStatement.setString(1, author.getLastName());
            preparedStatement.setString(2, author.getFirstName());
            preparedStatement.setString(3, author.getFatherName());
            preparedStatement.setInt(4, author.getId());
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
    public void deleteAuthor(Author author) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = connectionPool.getConnection();

            preparedStatement = connection.prepareStatement(GET_BOOKS);
            preparedStatement.setInt(1, author.getId());
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                if (resultSet.getInt(COUNT_BOOKS) == 0) {
                    preparedStatement = connection.prepareStatement(DELETE_AUTHOR);
                    preparedStatement.setInt(1, author.getId());
                    preparedStatement.executeUpdate();
                } else {
                    //TODO logger
                }
            }
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
    public List<Author> getAuthorsList() throws DAOException {
        List<Author> authors = new ArrayList<>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = connectionPool.getConnection();

            preparedStatement = connection.prepareStatement(SELECT_AUTHORS);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Author author = new Author();
                author.setId(resultSet.getInt(ID));
                author.setLastName(resultSet.getString(LAST_NAME));
                author.setFirstName(resultSet.getString(FIRST_NAME));
                author.setFatherName(resultSet.getString(FATHER_NAME));
                authors.add(author);
            }
            return authors;
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
