package com.epam.library.service.impl;

import com.epam.library.dao.DAOProvider;
import com.epam.library.dao.GenreDAO;
import com.epam.library.dao.exception.DAOException;
import com.epam.library.entity.book.Genre;
import com.epam.library.service.GenreService;
import com.epam.library.service.exception.GenreException;
import com.epam.library.service.exception.ServiceException;
import com.epam.library.service.exception.genre.EmptyGenreNameException;
import com.epam.library.service.exception.genre.ExistGenreException;
import com.epam.library.service.exception.genre.InvalidLengthGenreNameException;
import com.epam.library.service.validation.Validator;

import java.util.ArrayList;
import java.util.List;

public class GenreServiceImpl implements GenreService {
    private static final GenreDAO genreDAO = DAOProvider.getInstance().getGenreDAO();
    private static final Validator validator = Validator.getInstance();

    private static final int NAME_LENGTH = 45;

    public GenreServiceImpl() {}

    @Override
    public List<Genre> getGenresList() throws ServiceException {
        try {
            return genreDAO.getGenresList();
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void addGenre(Genre genre) throws ServiceException {

        List<GenreException> exceptions = new ArrayList<>();
        try {
            if (!genreDAO.checkGenre(genre)) {
                exceptions.add(new ExistGenreException());
            }

            if (validator.isEmpty(genre.getName())) {
                exceptions.add(new EmptyGenreNameException());
            } else if (genre.getName().length() > NAME_LENGTH) {
                exceptions.add(new InvalidLengthGenreNameException());
            }

            if (exceptions.isEmpty()) {
                genreDAO.addGenre(genre);
            } else {
                throw new GenreException(exceptions);
            }
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void updateGenre(Genre genre) throws ServiceException {

        List<GenreException> exceptions = new ArrayList<>();
        try {
            if (!genreDAO.checkGenre(genre)) {
                exceptions.add(new ExistGenreException());
            }

            if (validator.isEmpty(genre.getName())) {
                exceptions.add(new EmptyGenreNameException());
            } else if (genre.getName().length() > NAME_LENGTH) {
                exceptions.add(new InvalidLengthGenreNameException());
            }

            if (exceptions.isEmpty()) {
                genreDAO.updateGenre(genre);
            } else {
                throw new GenreException(exceptions);
            }
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Genre getGenre(int genreID) throws ServiceException {
        try {
            return genreDAO.getGenre(genreID);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public boolean deleteGenre(int genreID) throws ServiceException {
        try {
            return genreDAO.deleteGenre(genreID);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }
}
