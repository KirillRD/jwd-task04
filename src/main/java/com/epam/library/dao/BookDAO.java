package com.epam.library.dao;

import com.epam.library.dao.exception.DAOException;
import com.epam.library.entity.Book;
import com.epam.library.entity.book.BookInfo;

public interface BookDAO {
    boolean checkStandardNumber(int bookID, String isbn, String issn) throws DAOException;
    void addBook(BookInfo book) throws DAOException;
    Book getBook(int bookID) throws DAOException;
    void updateBook(BookInfo book) throws DAOException;
    boolean deleteBook(int bookID) throws DAOException;
}
