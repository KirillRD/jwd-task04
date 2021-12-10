package com.epam.library.dao;

import com.epam.library.dao.impl.MYSQLBookDAO;
import com.epam.library.dao.impl.MYSQLUserDAO;

public class DAOProvider {
    private static final DAOProvider instance = new DAOProvider();

    private final UserDAO userDAO = new MYSQLUserDAO();
    private final BookDAO bookDAO = new MYSQLBookDAO();

    private DAOProvider() {}

    public static DAOProvider getInstance() {
        return instance;
    }

    public UserDAO getUserDAO() {
        return userDAO;
    }

    public BookDAO getBookDAO() {
        return bookDAO;
    }
}
