package com.epam.library.dao;

import com.epam.library.dao.exception.DAOException;
import com.epam.library.entity.book.Genre;

import java.util.List;

public interface GenreDAO {
    void addGenre(Genre genre) throws DAOException;
    Genre getGenre(int genreID) throws DAOException;
    void updateGenre(Genre genre) throws DAOException;
    void deleteGenre(Genre genre) throws DAOException;
    List<Genre> getGenresList() throws DAOException;
}
