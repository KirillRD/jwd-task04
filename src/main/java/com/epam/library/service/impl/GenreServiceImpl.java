package com.epam.library.service.impl;

import com.epam.library.dao.DAOProvider;
import com.epam.library.dao.GenreDAO;
import com.epam.library.dao.exception.DAOException;
import com.epam.library.entity.book.Genre;
import com.epam.library.service.GenreService;
import com.epam.library.service.exception.ServiceException;

import java.util.List;

public class GenreServiceImpl implements GenreService {

    private final GenreDAO genreDAO;

    public GenreServiceImpl() {
        genreDAO = DAOProvider.getInstance().getGenreDAO();
    }

    @Override
    public List<Genre> getGenresList() throws ServiceException {
        try {
            return genreDAO.getGenresList();
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }
}
