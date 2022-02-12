package com.epam.library.service;

import com.epam.library.entity.book.dictionary.Genre;
import com.epam.library.service.exception.ServiceException;

import java.util.List;

/**
 * Genre service interface
 */
public interface GenreService {
    /**
     * Returns list with the generes' data
     * @return list with the generes' data
     * @throws ServiceException if a data processing error occurs
     */
    List<Genre> getGenresList() throws ServiceException;

    /**
     * Adding a genre
     * @param genre to be added
     * @throws ServiceException if a data processing error occurs
     */
    void addGenre(Genre genre) throws ServiceException;

    /**
     * Updating the genre's data
     * @param genre whose data needs to be updated
     * @throws ServiceException if a data processing error occurs
     */
    void updateGenre(Genre genre) throws ServiceException;

    /**
     * Returns genre's data
     * @param genreID ID of the genre whose data need to get
     * @return genre's data
     * @throws ServiceException if a data processing error occurs
     */
    Genre getGenre(int genreID) throws ServiceException;

    /**
     * Returns true if genre was deleted successfully
     * @param genreID ID of the genre to be deleted
     * @return true if genre was deleted successfully
     * @throws ServiceException if a data processing error occurs
     */
    boolean deleteGenre(int genreID) throws ServiceException;
}
