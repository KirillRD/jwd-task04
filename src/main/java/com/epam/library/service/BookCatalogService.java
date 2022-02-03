package com.epam.library.service;

import com.epam.library.entity.book.catalog.BookCatalog;
import com.epam.library.service.exception.ServiceException;

import java.util.List;
import java.util.Map;

public interface BookCatalogService {
    BookCatalog getBookCatalog(int bookID) throws ServiceException;
    List<BookCatalog> getNewBookCatalogList() throws ServiceException;
    List<BookCatalog> getPopularBookCatalogList() throws ServiceException;
    int getPagesCount(Map<String, Object> filters) throws ServiceException;
    List<BookCatalog> getBookCatalogByFilter(Map<String, Object> filters, int page) throws ServiceException;
}
