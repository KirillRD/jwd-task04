package com.epam.library.service.impl;

import com.epam.library.dao.BookDAO;
import com.epam.library.dao.DAOProvider;
import com.epam.library.dao.exception.DAOException;
import com.epam.library.entity.Book;
import com.epam.library.service.BookService;
import com.epam.library.service.exception.ServiceException;

public class BookServiceImpl implements BookService {

    private final BookDAO bookDAO;

    public BookServiceImpl() {
        bookDAO = DAOProvider.getInstance().getBookDAO();
    }

    @Override
    public void addBook(Book book) throws ServiceException {
        try {
            bookDAO.addBook(book);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Book getBook(int bookID) throws ServiceException {
        Book book;
        try {
            book = bookDAO.getBook(bookID);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        return book;
    }

    @Override
    public void updateBook(Book book) throws ServiceException {
        try {
            bookDAO.updateBook(book);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void deleteBook(int bookID) throws ServiceException {
        try {
            bookDAO.deleteBook(bookID);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }
}
