package com.epam.library.service;

import com.epam.library.entity.Book;
import com.epam.library.service.exception.ServiceException;

public interface BookService {
    void addBook (Book book) throws ServiceException;
    Book getBook (int bookID) throws ServiceException;
    void updateBook (Book book) throws ServiceException;
    void deleteBook (int bookID) throws ServiceException;
}
