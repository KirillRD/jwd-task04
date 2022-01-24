package com.epam.library.service;

import com.epam.library.entity.book.Genre;
import com.epam.library.service.exception.ServiceException;

import java.util.List;

public interface GenreService {
    List<Genre> getGenresList() throws ServiceException;
    void addGenre(Genre genre) throws ServiceException;
    void updateGenre(Genre genre) throws ServiceException;
    Genre getGenre(int genreID) throws ServiceException;
    boolean deleteGenre(int genreID) throws ServiceException;
}
