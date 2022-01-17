package com.epam.library.dao;

import com.epam.library.dao.exception.DAOException;
import com.epam.library.entity.Book;

import java.util.List;

public interface BookDAO {
    boolean addBook(Book book) throws DAOException;
    Book getBook(int bookID) throws DAOException;
    boolean updateBook(Book book) throws DAOException;
    boolean deleteBook(int bookID) throws DAOException;
    List<Book> getBooksByCriteria() throws DAOException;
}
