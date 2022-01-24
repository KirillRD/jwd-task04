package com.epam.library.service.impl;

import com.epam.library.dao.AuthorDAO;
import com.epam.library.dao.DAOProvider;
import com.epam.library.dao.exception.DAOException;
import com.epam.library.entity.book.Author;
import com.epam.library.service.AuthorService;
import com.epam.library.service.exception.AuthorException;
import com.epam.library.service.exception.ServiceException;
import com.epam.library.service.exception.author.*;
import com.epam.library.service.validation.Validator;

import java.util.ArrayList;
import java.util.List;

public class AuthorServiceImpl implements AuthorService {
    private static final AuthorDAO authorDAO = DAOProvider.getInstance().getAuthorDAO();
    private static final Validator validator = Validator.getInstance();

    private static final int NAME_LENGTH = 30;

    public AuthorServiceImpl() {}

    @Override
    public List<Author> getAuthorsList() throws ServiceException {
        try {
            return authorDAO.getAuthorsList();
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void addAuthor(Author author) throws ServiceException {

        List<AuthorException> exceptions = new ArrayList<>();
        try {
            if (!authorDAO.checkAuthor(author)) {
                exceptions.add(new ExistAuthorException());
            }

            if (validator.isEmpty(author.getLastName())) {
                exceptions.add(new EmptyAuthorLastNameException());
            } else if (author.getLastName().length() > NAME_LENGTH) {
                exceptions.add(new InvalidLengthAuthorLastNameException());
            }

            if (validator.isEmpty(author.getFirstName())) {
                exceptions.add(new EmptyAuthorFirstNameException());
            } else if (author.getFirstName().length() > NAME_LENGTH) {
                exceptions.add(new InvalidLengthAuthorFirstNameException());
            }

            if (author.getFatherName().length() > NAME_LENGTH) {
                exceptions.add(new InvalidLengthAuthorFatherNameException());
            }

            if (exceptions.isEmpty()) {
                authorDAO.addAuthor(author);
            } else {
                throw new AuthorException(exceptions);
            }
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void updateAuthor(Author author) throws ServiceException {

        List<AuthorException> exceptions = new ArrayList<>();
        try {
            if (!authorDAO.checkAuthor(author)) {
                exceptions.add(new ExistAuthorException());
            }

            if (validator.isEmpty(author.getLastName())) {
                exceptions.add(new EmptyAuthorLastNameException());
            } else if (author.getLastName().length() > NAME_LENGTH) {
                exceptions.add(new InvalidLengthAuthorLastNameException());
            }

            if (validator.isEmpty(author.getFirstName())) {
                exceptions.add(new EmptyAuthorFirstNameException());
            } else if (author.getFirstName().length() > NAME_LENGTH) {
                exceptions.add(new InvalidLengthAuthorFirstNameException());
            }

            if (author.getFatherName().length() > NAME_LENGTH) {
                exceptions.add(new InvalidLengthAuthorFatherNameException());
            }

            if (exceptions.isEmpty()) {
                authorDAO.updateAuthor(author);
            } else {
                throw new AuthorException(exceptions);
            }
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Author getAuthor(int authorID) throws ServiceException {
        try {
            return authorDAO.getAuthor(authorID);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public boolean deleteAuthor(int authorID) throws ServiceException {
        try {
            return authorDAO.deleteAuthor(authorID);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }
}
