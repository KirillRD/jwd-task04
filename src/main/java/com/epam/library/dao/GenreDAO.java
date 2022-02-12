package com.epam.library.dao;

import com.epam.library.dao.exception.DAOException;
import com.epam.library.entity.book.dictionary.Genre;

import java.util.List;

/**
 * Genre DAO interface
 */
public interface GenreDAO {
    /**
     * Returns true if DB contains the specified genre
     * @param genre whose presence is to be checked
     * @return true if DB contains the specified genre
     * @throws DAOException if an error occurred while accessing the database
     */
    boolean genreExists(Genre genre) throws DAOException;

    /**
     * Adding a genre
     * @param genre to be added
     * @throws DAOException if an error occurred while accessing the database
     */
    void addGenre(Genre genre) throws DAOException;

    /**
     * Returns genre's data
     * @param genreID ID of the genre whose data need to get
     * @return genre's data
     * @throws DAOException if an error occurred while accessing the database
     */
    Genre getGenre(int genreID) throws DAOException;

    /**
     * Updating the genre's data
     * @param genre whose data needs to be updated
     * @throws DAOException if an error occurred while accessing the database
     */
    void updateGenre(Genre genre) throws DAOException;

    /**
     * Returns true if genre was deleted successfully
     * @param genreID ID of the genre to be deleted
     * @return true if genre was deleted successfully
     * @throws DAOException if an error occurred while accessing the database
     */
    boolean deleteGenre(int genreID) throws DAOException;

    /**
     * Returns list with the generes' data
     * @return list with the generes' data
     * @throws DAOException if an error occurred while accessing the database
     */
    List<Genre> getGenresList() throws DAOException;
}
