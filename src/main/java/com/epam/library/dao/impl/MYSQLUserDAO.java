package com.epam.library.dao.impl;

import com.epam.library.dao.UserDAO;
import com.epam.library.dao.connection_pool.ConnectionPool;
import com.epam.library.dao.exception.DAOException;
import com.epam.library.entity.User;

public class MYSQLUserDAO implements UserDAO {

    private static final ConnectionPool connectionPool = ConnectionPool.getInstance();
    private static final String COUNT_USERS = "count_books";
    private static final String MAX_ID_USER = "MAX(id)";
    private static final String ID = "id";
    private static final String LOGIN = "login";
    private static final String PASSWORD = "password";
    private static final String EMAIL = "email";
    private static final String LAST_NAME = "last_name";
    private static final String FIRST_NAME = "first_name";
    private static final String FATHER_NAME = "father_name";
    private static final String BIRTHDAY = "birthday";
    private static final String GENDER = "gender";
    private static final String PASSPORT = "passport";
    private static final String ADDRESS = "address";
    private static final String PHONE = "phone";
    private static final String REGISTRATION_DATE = "registration_date";
    private static final String IMAGE = "image";
    private static final String DEFAULT_IMAGE_BOOK = "images/users/default_image_user";

    private static final String GET_BOOK_BY_ISBN_ISSN = "SELECT COUNT(1) AS count_books FROM books WHERE (isbn IS NULL OR isbn=?) OR (issn IS NULL OR issn=?)";
    private static final String GET_MAX_ID_BOOK = "SELECT MAX(id) FROM books";
    private static final String INSERT_BOOK = "INSERT INTO books (id, name, publishers_id, types_id, publication_year, pages, part, isbn, issn, udc, lbc, copyright_mark, annotation, price, image) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
    private static final String INSERT_BOOK_GENRE = "INSERT INTO books_genres (books_id, genres_id) VALUES (?,?)";
    private static final String INSERT_BOOK_AUTHOR = "INSERT INTO books_authors (books_id, authors_id) VALUES (?,?)";
    private static final String SELECT_BOOK = "SELECT * FROM books WHERE id=?";
    private static final String SELECT_BOOK_GENRES = "SELECT * FROM books_genres WHERE books_id=?";
    private static final String SELECT_BOOK_AUTHORS = "SELECT * FROM books_authors WHERE books_id=?";
    private static final String UPDATE_BOOK = "UPDATE books SET name=?, publishers_id=?, types_id=?, publication_year=?, pages=?, part=?, isbn=?, issn=?, udc=?, lbc=?, copyright_mark=?, annotation=?, price=?, image=? WHERE id=?";
    private static final String DELETE_BOOK_GENRES = "DELETE FROM books_genres WHERE books_id=?";
    private static final String DELETE_BOOK_AUTHORS = "DELETE FROM books_authors WHERE books_id=?";
    private static final String GET_INSTANCES = "SELECT COUNT(1) AS count_instances FROM instances WHERE books_id=?";
    private static final String DELETE_BOOK_REVIEWS = "DELETE FROM reviews WHERE books_id=?";
    private static final String DELETE_BOOK = "DELETE FROM books WHERE id=?";

    public MYSQLUserDAO() {}

    @Override
    public User registrationUser(User user) throws DAOException {
        return null;
    }

    @Override
    public User authorizationUser(String email, String password) throws DAOException {
        return null;
    }

    @Override
    public User updateUser(User user) throws DAOException {
        return null;
    }

    @Override
    public User changePassword(User user, String password) throws DAOException {
        return null;
    }
}
