package com.epam.library.service;

import com.epam.library.entity.Book;
import com.epam.library.service.exception.ServiceException;

public interface BookService {
    boolean addBook (Book book) throws ServiceException;
    Book getBook (int bookID) throws ServiceException;
    boolean updateBook (Book book) throws ServiceException;
    boolean deleteBook (int bookID) throws ServiceException;
}
