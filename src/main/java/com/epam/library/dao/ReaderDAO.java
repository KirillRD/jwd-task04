package com.epam.library.dao;

import com.epam.library.dao.exception.DAOException;
import com.epam.library.entity.issuance.ReaderIssuance;
import com.epam.library.entity.reservation.ReaderReservation;
import com.epam.library.entity.user.Reader;

import java.util.List;
import java.util.Map;

/**
 * Reader DAO interface
 */
public interface ReaderDAO {
    /**
     * Returns reader's data
     * @param readerID ID of the reader whose data need to get
     * @return reader's data
     * @throws DAOException if an error occurred while accessing the database
     */
    Reader getReader(int readerID) throws DAOException;

    /**
     * Returns filtered list with readers' data
     * @param filters reader filtering options
     * @param page page number
     * @return filtered list with readers' data
     * @throws DAOException if an error occurred while accessing the database
     */
    List<Reader> getReadersByFilter(Map<String, Object> filters, int page) throws DAOException;

    /**
     * Returns the number of pages in the filtered reader list
     * @param filters reader filtering options
     * @return the number of pages in the filtered reader list
     * @throws DAOException if an error occurred while accessing the database
     */
    int getPagesCount(Map<String, Object> filters) throws DAOException;

    /**
     * Returns list of issued books of the reader
     * @param readerID ID of the reader whose data need to get
     * @return list of issued books of the reader
     * @throws DAOException if an error occurred while accessing the database
     */
    List<ReaderIssuance> getReaderIssuanceList(int readerID) throws DAOException;

    /**
     * Returns history of issuing books to the reader
     * @param readerID ID of the reader whose data need to get
     * @return history of issuing books to the reader
     * @throws DAOException if an error occurred while accessing the database
     */
    List<ReaderIssuance> getReaderIssuanceHistoryList(int readerID) throws DAOException;

    /**
     * Returns list of reserved books of the reader
     * @param readerID ID of the reader whose data need to get
     * @return list of reserved books of the reader
     * @throws DAOException if an error occurred while accessing the database
     */
    List<ReaderReservation> getReaderReservationList(int readerID) throws DAOException;

    /**
     * Returns book reservation history to the reader
     * @param readerID ID of the reader whose data need to get
     * @return book reservation history to the reader
     * @throws DAOException if an error occurred while accessing the database
     */
    List<ReaderReservation> getReaderReservationHistoryList(int readerID) throws DAOException;
}
