package com.epam.library.dao.impl;

import com.epam.library.dao.BookDAO;
import com.epam.library.dao.connection_pool.ConnectionPool;
import com.epam.library.dao.connection_pool.exception.ConnectionPoolException;
import com.epam.library.dao.exception.DAOException;
import com.epam.library.entity.Book;
import com.epam.library.entity.book.BookInfo;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MYSQLBookDAO implements BookDAO {
    private static final Logger logger = Logger.getLogger(MYSQLBookDAO.class.getName());

    private static final ConnectionPool connectionPool = ConnectionPool.getInstance();
    private static final String ID = "id";
    private static final String MAX_ID_BOOK = "MAX(id)";
    private static final String NAME = "name";
    private static final String PUBLISHER_ID = "publishers_id";
    private static final String TYPE_ID = "types_id";
    private static final String PUBLICATION_YEAR = "publication_year";
    private static final String PAGES = "pages";
    private static final String PART = "part";
    private static final String ISBN = "isbn";
    private static final String ISSN = "issn";
    private static final String ANNOTATION = "annotation";
    private static final String PRICE = "price";
    private static final String IMAGE = "image";
    private static final String GENRE_ID = "genres_id";
    private static final String AUTHOR_ID = "authors_id";
    private static final String DEFAULT_IMAGE_BOOK = "images/books/default_image_book.jpg";

    private static final String GET_BOOK_BY_ISBN_ISSN = "SELECT * FROM books WHERE (isbn IS NULL OR isbn=?) AND (issn IS NULL OR issn=?)";
    private static final String GET_MAX_ID_BOOK = "SELECT MAX(id) FROM books";
    private static final String INSERT_BOOK = "INSERT INTO books (id, name, publishers_id, types_id, publication_year, pages, part, isbn, issn, annotation, price, image) VALUES (?,?,?,?,?,?,?,?,?,?,?,?)";
    private static final String INSERT_BOOK_GENRE = "INSERT INTO books_genres (books_id, genres_id) VALUES (?,?)";
    private static final String INSERT_BOOK_AUTHOR = "INSERT INTO books_authors (books_id, authors_id) VALUES (?,?)";
    private static final String SELECT_BOOK = "SELECT * FROM books WHERE id=?";
    private static final String SELECT_BOOK_GENRES = "SELECT * FROM books_genres WHERE books_id=?";
    private static final String SELECT_BOOK_AUTHORS = "SELECT * FROM books_authors WHERE books_id=?";
    private static final String UPDATE_BOOK = "UPDATE books SET name=?, publishers_id=?, types_id=?, publication_year=?, pages=?, part=?, isbn=?, issn=?, annotation=?, price=?, image=? WHERE id=?";
    private static final String DELETE_BOOK_GENRES = "DELETE FROM books_genres WHERE books_id=?";
    private static final String DELETE_BOOK_AUTHORS = "DELETE FROM books_authors WHERE books_id=?";
    private static final String GET_INSTANCES = "SELECT * FROM instances WHERE books_id=?";
    private static final String DELETE_BOOK_REVIEWS = "DELETE FROM reviews WHERE books_id=?";
    private static final String DELETE_BOOK = "DELETE FROM books WHERE id=?";

    public MYSQLBookDAO() {}

    @Override
    public boolean checkStandardNumber(int bookId, String isbn, String issn) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = connectionPool.getConnection();

            preparedStatement = connection.prepareStatement(GET_BOOK_BY_ISBN_ISSN);
            preparedStatement.setString(1, isbn);
            preparedStatement.setString(2, issn);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                if (bookId == 0 || bookId != resultSet.getInt(ID)) {
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
    public void addBook(BookInfo book) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = connectionPool.getConnection();

            preparedStatement = connection.prepareStatement(GET_MAX_ID_BOOK);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                book.setId(resultSet.getInt(MAX_ID_BOOK) + 1);
            }

            connection.setAutoCommit(false);

            preparedStatement = connection.prepareStatement(INSERT_BOOK);
            preparedStatement.setInt(1, book.getId());
            preparedStatement.setString(2, book.getName());
            preparedStatement.setInt(3, Integer.parseInt(book.getPublisherID()));
            preparedStatement.setInt(4, Integer.parseInt(book.getTypeID()));
            preparedStatement.setInt(5, Integer.parseInt(book.getPublicationYear()));
            preparedStatement.setInt(6, Integer.parseInt(book.getPages()));
            preparedStatement.setInt(7, Integer.parseInt(book.getPart()));
            preparedStatement.setString(8, book.getIsbn());
            preparedStatement.setString(9, book.getIssn());
            preparedStatement.setString(10, book.getAnnotation());
            preparedStatement.setDouble(11, Double.parseDouble(book.getPrice()));
            preparedStatement.setString(12, DEFAULT_IMAGE_BOOK);//TODO загрузка картинки книги
            preparedStatement.executeUpdate();

            preparedStatement = connection.prepareStatement(INSERT_BOOK_GENRE);
            for (String genreID : book.getGenresID()) {
                preparedStatement.setInt(1, book.getId());
                preparedStatement.setInt(2, Integer.parseInt(genreID));
                preparedStatement.executeUpdate();
            }

            preparedStatement = connection.prepareStatement(INSERT_BOOK_AUTHOR);
            for (String authorID : book.getAuthorsID()) {
                preparedStatement.setInt(1, book.getId());
                preparedStatement.setInt(2, Integer.parseInt(authorID));
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
                connectionPool.closeConnection(resultSet, preparedStatement, connection);
            } catch (ConnectionPoolException | SQLException e) {
                logger.error("Error closing resources", e);
            }
        }
    }

    @Override
    public Book getBook(int bookID) throws DAOException {
        Book book = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = connectionPool.getConnection();

            preparedStatement = connection.prepareStatement(SELECT_BOOK);
            preparedStatement.setInt(1, bookID);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                book = new Book();
                book.setId(bookID);
                book.setName(resultSet.getString(NAME));
                book.setPublisherID(resultSet.getInt(PUBLISHER_ID));
                book.setTypeID(resultSet.getInt(TYPE_ID));
                book.setPublicationYear(resultSet.getInt(PUBLICATION_YEAR));
                book.setPages(resultSet.getInt(PAGES));
                book.setPart(resultSet.getInt(PART));
                book.setIsbn(resultSet.getString(ISBN));
                book.setIssn(resultSet.getString(ISSN));
                book.setAnnotation(resultSet.getString(ANNOTATION));
                book.setPrice(resultSet.getDouble(PRICE));
                book.setImageURL(resultSet.getString(IMAGE));
            }

            preparedStatement = connection.prepareStatement(SELECT_BOOK_GENRES);
            preparedStatement.setInt(1, bookID);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                book.getGenresID().add(resultSet.getInt(GENRE_ID));
            }

            preparedStatement = connection.prepareStatement(SELECT_BOOK_AUTHORS);
            preparedStatement.setInt(1, bookID);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                book.getAuthorsID().add(resultSet.getInt(AUTHOR_ID));
            }
            return book;
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
    public void updateBook(BookInfo book) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = connectionPool.getConnection();

            connection.setAutoCommit(false);

            preparedStatement = connection.prepareStatement(UPDATE_BOOK);
            preparedStatement.setString(1, book.getName());
            preparedStatement.setInt(2, Integer.parseInt(book.getPublisherID()));
            preparedStatement.setInt(3, Integer.parseInt(book.getTypeID()));
            preparedStatement.setInt(4, Integer.parseInt(book.getPublicationYear()));
            preparedStatement.setInt(5, Integer.parseInt(book.getPages()));
            preparedStatement.setInt(6, Integer.parseInt(book.getPart()));
            preparedStatement.setString(7, book.getIsbn());
            preparedStatement.setString(8, book.getIssn());
            preparedStatement.setString(9, book.getAnnotation());
            preparedStatement.setDouble(10, Double.parseDouble(book.getPrice()));
            preparedStatement.setString(11, book.getImageURL());
            preparedStatement.setInt(12, book.getId());
            preparedStatement.executeUpdate();

            preparedStatement = connection.prepareStatement(DELETE_BOOK_GENRES);
            preparedStatement.setInt(1, book.getId());
            preparedStatement.executeUpdate();

            preparedStatement = connection.prepareStatement(INSERT_BOOK_GENRE);
            for (String genreID : book.getGenresID()) {
                preparedStatement.setInt(1, book.getId());
                preparedStatement.setInt(2, Integer.parseInt(genreID));
                preparedStatement.executeUpdate();
            }

            preparedStatement = connection.prepareStatement(DELETE_BOOK_AUTHORS);
            preparedStatement.setInt(1, book.getId());
            preparedStatement.executeUpdate();

            preparedStatement = connection.prepareStatement(INSERT_BOOK_AUTHOR);
            for (String authorID : book.getAuthorsID()) {
                preparedStatement.setInt(1, book.getId());
                preparedStatement.setInt(2, Integer.parseInt(authorID));
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

    @Override
    public boolean deleteBook(int bookID) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = connectionPool.getConnection();

            preparedStatement = connection.prepareStatement(GET_INSTANCES);
            preparedStatement.setInt(1, bookID);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return false;
            }

            connection.setAutoCommit(false);

            preparedStatement = connection.prepareStatement(DELETE_BOOK_GENRES);
            preparedStatement.setInt(1, bookID);
            preparedStatement.executeUpdate();

            preparedStatement = connection.prepareStatement(DELETE_BOOK_AUTHORS);
            preparedStatement.setInt(1, bookID);
            preparedStatement.executeUpdate();

            preparedStatement = connection.prepareStatement(DELETE_BOOK_REVIEWS);
            preparedStatement.setInt(1, bookID);
            preparedStatement.executeUpdate();

            preparedStatement = connection.prepareStatement(DELETE_BOOK);
            preparedStatement.setInt(1, bookID);
            preparedStatement.executeUpdate();

            connection.commit();

            return true;
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
                connectionPool.closeConnection(resultSet, preparedStatement, connection);
            } catch (ConnectionPoolException | SQLException e) {
                logger.error("Error closing resources", e);
            }
        }
    }
}
