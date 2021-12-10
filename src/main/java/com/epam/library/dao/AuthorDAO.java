package com.epam.library.dao;

import com.epam.library.dao.exception.DAOException;
import com.epam.library.entity.book.Author;

import java.util.List;

public interface AuthorDAO {
    void addAuthor(Author author) throws DAOException;
    Author getAuthor(int authorID) throws DAOException;
    void updateAuthor(Author author) throws DAOException;
    void deleteAuthor(Author author) throws DAOException;
    List<Author> getAuthorsList() throws DAOException;
}
