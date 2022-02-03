package com.epam.library.dao;

import com.epam.library.dao.exception.DAOException;
import com.epam.library.entity.book.catalog.BookCatalog;

import java.util.List;
import java.util.Map;

public interface BookCatalogDAO {
    BookCatalog getBookCatalog(int bookID) throws DAOException;
    List<BookCatalog> getNewBookCatalogList() throws DAOException;
    List<BookCatalog> getPopularBookCatalogList() throws DAOException;
    int getPagesCount(Map<String, Object> filters) throws DAOException;
    List<BookCatalog> getBookCatalogByFilter(Map<String, Object> filters, int page) throws DAOException;
}
