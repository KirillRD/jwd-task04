package com.epam.library.dao;

import com.epam.library.dao.impl.*;

/**
 * Provider of the DAO objects
 */
public class DAOProvider {
    private static final DAOProvider instance = new DAOProvider();

    private final UserDAO userDAO = new MYSQLUserDAO();
    private final BookDAO bookDAO = new MYSQLBookDAO();
    private final PublisherDAO publisherDAO = new MYSQLPublisherDAO();
    private final TypeDAO typeDAO = new MYSQLTypeDAO();
    private final AuthorDAO authorDAO = new MYSQLAuthorDAO();
    private final GenreDAO genreDAO = new MYSQLGenreDAO();
    private final BookCatalogDAO bookCatalogDAO = new MYSQLBookCatalogDAO();
    private final ReaderDAO readerDAO = new MYSQLReaderDAO();
    private final InstanceDAO instanceDAO = new MYSQLInstanceDAO();
    private final HallDAO hallDAO = new MYSQLHallDAO();
    private final IssuanceDAO issuanceDAO = new MYSQLIssuanceDAO();
    private final ReservationDAO reservationDAO = new MYSQLReservationDAO();
    private final ReviewDAO reviewDAO = new MYSQLReviewDAO();

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

    public PublisherDAO getPublisherDAO() {
        return publisherDAO;
    }

    public TypeDAO getTypeDAO() {
        return typeDAO;
    }

    public AuthorDAO getAuthorDAO() {
        return authorDAO;
    }

    public GenreDAO getGenreDAO() {
        return genreDAO;
    }

    public BookCatalogDAO getBookCatalogDAO() {
        return bookCatalogDAO;
    }

    public ReaderDAO getReaderDAO() {
        return readerDAO;
    }

    public InstanceDAO getInstanceDAO() {
        return instanceDAO;
    }

    public HallDAO getHallDAO() {
        return hallDAO;
    }

    public IssuanceDAO getIssuanceDAO() {
        return issuanceDAO;
    }

    public ReservationDAO getReservationDAO() {
        return reservationDAO;
    }

    public ReviewDAO getReviewDAO() {
        return reviewDAO;
    }
}
