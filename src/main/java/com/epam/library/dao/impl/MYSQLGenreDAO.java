package com.epam.library.dao.impl;

import com.epam.library.dao.GenreDAO;
import com.epam.library.dao.connection_pool.ConnectionPool;
import com.epam.library.dao.connection_pool.exception.ConnectionPoolException;
import com.epam.library.dao.exception.DAOException;
import com.epam.library.entity.book.Genre;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MYSQLGenreDAO implements GenreDAO {

    private static final ConnectionPool connectionPool = ConnectionPool.getInstance();
    private static final String COUNT_GENRES = "count_genres";
    private static final String COUNT_BOOKS = "count_books";
    private static final String MAX_ID_GENRE = "MAX(id)";
    private static final String ID = "id";
    private static final String NAME = "name";

    private static final String GET_GENRE_BY_NAME = "SELECT COUNT(1) AS count_genres FROM genres WHERE name=?";
    private static final String GET_MAX_ID_GENRE = "SELECT MAX(id) FROM genres";
    private static final String INSERT_GENRE = "INSERT INTO genres (id, name) VALUES (?,?)";
    private static final String SELECT_GENRE = "SELECT * FROM genres WHERE id=?";
    private static final String UPDATE_GENRE = "UPDATE genres SET name=? WHERE id=?";
    private static final String GET_BOOKS = "SELECT COUNT(1) AS count_books FROM books_genres WHERE genres_id=?";
    private static final String DELETE_GENRE = "DELETE FROM genres WHERE id=?";
    private static final String SELECT_GENRES = "SELECT * FROM genres ORDER BY name";

    public MYSQLGenreDAO() {}

    @Override
    public void addGenre(Genre genre) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = connectionPool.getConnection();

            preparedStatement = connection.prepareStatement(GET_GENRE_BY_NAME);
            preparedStatement.setString(1, genre.getName());
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                if (resultSet.getInt(COUNT_GENRES) == 0) {
                    preparedStatement = connection.prepareStatement(GET_MAX_ID_GENRE);
                    resultSet = preparedStatement.executeQuery();
                    if (resultSet.next()) {
                        genre.setId(resultSet.getInt(MAX_ID_GENRE) + 1);
                    }

                    preparedStatement = connection.prepareStatement(INSERT_GENRE);
                    preparedStatement.setInt(1, genre.getId());
                    preparedStatement.setString(2, genre.getName());
                    preparedStatement.executeUpdate();
                } else {
                    //TODO logger
                }
            }
        } catch (SQLException | ConnectionPoolException e) {
            throw new DAOException(e);
        } finally {
            try {
                connectionPool.closeConnection(resultSet, preparedStatement);
            } catch (ConnectionPoolException e) {

            }
            connectionPool.releaseConnection(connection);
        }
    }

    @Override
    public Genre getGenre(int genreID) throws DAOException {
        Genre genre = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = connectionPool.getConnection();

            preparedStatement = connection.prepareStatement(SELECT_GENRE);
            preparedStatement.setInt(1, genreID);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                genre = new Genre();
                genre.setId(genreID);
                genre.setName(resultSet.getString(NAME));
            }
            return genre;
        } catch (SQLException | ConnectionPoolException e) {
            throw new DAOException(e);
        } finally {
            try {
                connectionPool.closeConnection(resultSet, preparedStatement);
            } catch (ConnectionPoolException e) {

            }
            connectionPool.releaseConnection(connection);
        }
    }

    @Override
    public void updateGenre(Genre genre) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = connectionPool.getConnection();

            preparedStatement = connection.prepareStatement(UPDATE_GENRE);
            preparedStatement.setString(1, genre.getName());
            preparedStatement.setInt(2, genre.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException | ConnectionPoolException e) {
            throw new DAOException(e);
        } finally {
            try {
                connectionPool.closeConnection(preparedStatement);
            } catch (ConnectionPoolException e) {

            }
            connectionPool.releaseConnection(connection);
        }
    }

    @Override
    public void deleteGenre(Genre genre) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = connectionPool.getConnection();

            preparedStatement = connection.prepareStatement(GET_BOOKS);
            preparedStatement.setInt(1, genre.getId());
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                if (resultSet.getInt(COUNT_BOOKS) == 0) {
                    preparedStatement = connection.prepareStatement(DELETE_GENRE);
                    preparedStatement.setInt(1, genre.getId());
                    preparedStatement.executeUpdate();
                } else {
                    //TODO logger
                }
            }
        } catch (SQLException | ConnectionPoolException e) {
            throw new DAOException(e);
        } finally {
            try {
                connectionPool.closeConnection(resultSet, preparedStatement);
            } catch (ConnectionPoolException e) {

            }
            connectionPool.releaseConnection(connection);
        }
    }

    @Override
    public List<Genre> getGenresList() throws DAOException {
        List<Genre> genres = new ArrayList<>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = connectionPool.getConnection();

            preparedStatement = connection.prepareStatement(SELECT_GENRES);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Genre genre = new Genre();
                genre.setId(resultSet.getInt(ID));
                genre.setName(resultSet.getString(NAME));
                genres.add(genre);
            }
            return genres;
        } catch (SQLException | ConnectionPoolException e) {
            throw new DAOException(e);
        } finally {
            try {
                connectionPool.closeConnection(resultSet, preparedStatement);
            } catch (ConnectionPoolException e) {

            }
            connectionPool.releaseConnection(connection);
        }
    }
}
