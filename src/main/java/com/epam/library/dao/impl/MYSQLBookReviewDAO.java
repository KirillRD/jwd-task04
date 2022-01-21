package com.epam.library.dao.impl;

import com.epam.library.dao.BookReviewDAO;
import com.epam.library.dao.connection_pool.ConnectionPool;
import com.epam.library.dao.connection_pool.exception.ConnectionPoolException;
import com.epam.library.dao.exception.DAOException;
import com.epam.library.entity.book.catalog.BookReview;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MYSQLBookReviewDAO implements BookReviewDAO {
    private static final Logger logger = Logger.getLogger(MYSQLBookReviewDAO.class.getName());

    private static final ConnectionPool connectionPool = ConnectionPool.getInstance();
    private static final String USER_ID = "user_id";
    private static final String NICKNAME = "nickname";
    private static final String IMAGE = "image";
    private static final String ID = "id";
    private static final String RATING = "rating";
    private static final String COMMENT = "comment";
    private static final String DATE = "date";

    private static final String BOOK_REVIEWS = "SELECT users.id AS user_id, users.nickname, users.image, reviews.id, reviews.rating, reviews.comment, reviews.date FROM reviews INNER JOIN users ON reviews.reader_id=users.id WHERE reviews.books_id=?";

    public MYSQLBookReviewDAO() {}

    @Override
    public List<BookReview> getBookReviews(int bookID) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        List<BookReview> bookReviews = new ArrayList<>();
        try {
            connection = connectionPool.getConnection();

            preparedStatement = connection.prepareStatement(BOOK_REVIEWS);
            preparedStatement.setInt(1, bookID);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                BookReview bookReview = new BookReview();
                bookReview.setUserID(resultSet.getInt(USER_ID));
                bookReview.setNickname(resultSet.getString(NICKNAME));
                bookReview.setImageURL(resultSet.getString(IMAGE));
                bookReview.setId(resultSet.getInt(ID));
                bookReview.setRating(resultSet.getInt(RATING));
                bookReview.setComment(resultSet.getString(COMMENT));
                bookReview.setDate(resultSet.getDate(DATE));
                bookReviews.add(bookReview);
            }
            return bookReviews;
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
