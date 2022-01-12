package com.epam.library.service.impl;

import com.epam.library.dao.AuthorDAO;
import com.epam.library.dao.DAOProvider;
import com.epam.library.dao.exception.DAOException;
import com.epam.library.entity.book.Author;
import com.epam.library.service.AuthorService;
import com.epam.library.service.exception.ServiceException;

import java.util.List;

public class AuthorServiceImpl implements AuthorService {

    private final AuthorDAO authorDAO;

    public AuthorServiceImpl() {
        authorDAO = DAOProvider.getInstance().getAuthorDAO();
    }

    @Override
    public List<Author> getAuthorsList() throws ServiceException {
        try {
            return authorDAO.getAuthorsList();
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }
}
