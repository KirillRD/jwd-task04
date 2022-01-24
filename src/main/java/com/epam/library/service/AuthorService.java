package com.epam.library.service;

import com.epam.library.entity.book.Author;
import com.epam.library.service.exception.ServiceException;

import java.util.List;

public interface AuthorService {
    List<Author> getAuthorsList() throws ServiceException;
    void addAuthor(Author author) throws ServiceException;
    void updateAuthor(Author author) throws ServiceException;
    Author getAuthor(int authorID) throws ServiceException;
    boolean deleteAuthor(int authorID) throws ServiceException;
}
