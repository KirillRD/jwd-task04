package com.epam.library.service;

import com.epam.library.entity.Book;
import com.epam.library.entity.book.BookInfo;
import com.epam.library.service.exception.ServiceException;

public interface BookService {
    void addBook (BookInfo book) throws ServiceException;
    Book getBook (int bookID) throws ServiceException;
    void updateBook (BookInfo book) throws ServiceException;
    void updateBookImage(int bookID, String imageURL) throws ServiceException;
    boolean deleteBook (int bookID) throws ServiceException;
}
