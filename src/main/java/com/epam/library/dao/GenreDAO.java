package com.epam.library.dao;

import com.epam.library.dao.exception.DAOException;
import com.epam.library.entity.book.dictionary.Genre;

import java.util.List;

public interface GenreDAO {
    boolean checkGenre(Genre genre) throws DAOException;
    void addGenre(Genre genre) throws DAOException;
    Genre getGenre(int genreID) throws DAOException;
    void updateGenre(Genre genre) throws DAOException;
    boolean deleteGenre(int genreID) throws DAOException;
    List<Genre> getGenresList() throws DAOException;
}
