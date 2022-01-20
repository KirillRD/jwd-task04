package com.epam.library.service.impl;

import com.epam.library.dao.BookCatalogDAO;
import com.epam.library.dao.DAOProvider;
import com.epam.library.dao.exception.DAOException;
import com.epam.library.entity.book.catalog.BookCatalog;
import com.epam.library.service.BookCatalogService;
import com.epam.library.service.exception.ServiceException;

import java.util.List;
import java.util.Map;

public class BookCatalogServiceImpl implements BookCatalogService {
    private static final BookCatalogDAO bookCatalogDAO = DAOProvider.getInstance().getBookCatalogDAO();

    public BookCatalogServiceImpl() {}

    @Override
    public BookCatalog getBookCatalog(int bookID) throws ServiceException {
        try {
            return bookCatalogDAO.getBookCatalog(bookID);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<BookCatalog> getNewBookCatalogList() throws ServiceException {
        try {
            return bookCatalogDAO.getNewBookCatalogList();
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<BookCatalog> getPopularBookCatalogList() throws ServiceException {
        try {
            return bookCatalogDAO.getPopularBookCatalogList();
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<BookCatalog> getBookCatalogByFilter(Map<String, Object> filters) throws ServiceException {
        try {
            return bookCatalogDAO.getBookCatalogByFilter(filters);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }
}
