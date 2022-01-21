package com.epam.library.dao.impl;

import com.epam.library.dao.ReviewDAO;
import com.epam.library.dao.connection_pool.ConnectionPool;
import com.epam.library.dao.connection_pool.exception.ConnectionPoolException;
import com.epam.library.dao.exception.DAOException;
import com.epam.library.entity.Review;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MYSQLReviewDAO implements ReviewDAO {
    private static final Logger logger = Logger.getLogger(MYSQLReviewDAO.class.getName());

    private static final ConnectionPool connectionPool = ConnectionPool.getInstance();
    private static final String MAX_ID_REVIEW = "MAX(id)";
    private static final String BOOKS_ID = "books_id";
    private static final String READER_ID = "reader_id";
    private static final String RATING = "rating";
    private static final String COMMENT = "comment";
    private static final String DATE = "date";

    private static final String GET_REVIEW_BY_BOOK_READER = "SELECT * FROM reviews WHERE books_id=? AND reader_id=?";
    private static final String GET_MAX_ID_REVIEW = "SELECT MAX(id) FROM reviews";
    private static final String INSERT_REVIEW = "INSERT INTO reviews (id, books_id, reader_id, rating, comment, date) VALUES (?,?,?,?,?,CURDATE())";
    private static final String SELECT_REVIEW = "SELECT * FROM reviews WHERE id=?";
    private static final String UPDATE_REVIEW = "UPDATE reviews SET books_id=?, reader_id=?, rating=?, comment=?, date=? WHERE id=?";
    private static final String DELETE_REVIEW = "DELETE FROM reviews WHERE id=?";

    public MYSQLReviewDAO() {}

    @Override
    public boolean checkReview(int bookID, int readerID) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = connectionPool.getConnection();

            preparedStatement = connection.prepareStatement(GET_REVIEW_BY_BOOK_READER);
            preparedStatement.setInt(1, bookID);
            preparedStatement.setInt(2, readerID);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return false;
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
    public void addReview(Review review) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = connectionPool.getConnection();

            preparedStatement = connection.prepareStatement(GET_MAX_ID_REVIEW);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                review.setId(resultSet.getInt(MAX_ID_REVIEW) + 1);
            }

            preparedStatement = connection.prepareStatement(INSERT_REVIEW);
            preparedStatement.setInt(1, review.getId());
            preparedStatement.setInt(2, review.getBookID());
            preparedStatement.setInt(3, review.getReaderID());
            preparedStatement.setInt(4, review.getRating());
            preparedStatement.setString(5, review.getComment());
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
    public Review getReview(int reviewID) throws DAOException {
        Review review = new Review();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = connectionPool.getConnection();

            preparedStatement = connection.prepareStatement(SELECT_REVIEW);
            preparedStatement.setInt(1, reviewID);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                review.setId(reviewID);
                review.setBookID(resultSet.getInt(BOOKS_ID));
                review.setReaderID(resultSet.getInt(READER_ID));
                review.setRating(resultSet.getInt(RATING));
                review.setComment(resultSet.getString(COMMENT));
                review.setDate(resultSet.getDate(DATE));
            }
            return review;
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
    public void updateReview(Review review) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = connectionPool.getConnection();

            preparedStatement = connection.prepareStatement(UPDATE_REVIEW);
            preparedStatement.setInt(1, review.getBookID());
            preparedStatement.setInt(2, review.getReaderID());
            preparedStatement.setInt(3, review.getRating());
            preparedStatement.setString(4, review.getComment());
            preparedStatement.setDate(5, review.getDate());
            preparedStatement.setInt(6, review.getId());
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
    public void deleteReview(Review review) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = connectionPool.getConnection();

            preparedStatement = connection.prepareStatement(DELETE_REVIEW);
            preparedStatement.setInt(1, review.getId());
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
}
