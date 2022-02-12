package com.epam.library.service;

import com.epam.library.entity.issuance.ReaderIssuance;
import com.epam.library.entity.reservation.ReaderReservation;
import com.epam.library.entity.user.Reader;
import com.epam.library.service.exception.ServiceException;

import java.util.List;
import java.util.Map;

/**
 * Reader service interface
 */
public interface ReaderService {
    /**
     * Returns reader's data
     * @param readerID ID of the reader whose data need to get
     * @return reader's data
     * @throws ServiceException if a data processing error occurs
     */
    Reader getReader(int readerID) throws ServiceException;

    /**
     * Returns filtered list with readers' data
     * @param filters reader filtering options
     * @param page page number
     * @return filtered list with readers' data
     * @throws ServiceException if a data processing error occurs
     */
    List<Reader> getReadersByFilter(Map<String, Object> filters, int page) throws ServiceException;

    /**
     * Returns the number of pages in the filtered reader list
     * @param filters reader filtering options
     * @return the number of pages in the filtered reader list
     * @throws ServiceException if a data processing error occurs
     */
    int getPagesCount(Map<String, Object> filters) throws ServiceException;

    /**
     * Returns list of issued books of the reader
     * @param readerID ID of the reader whose data need to get
     * @return list of issued books of the reader
     * @throws ServiceException if a data processing error occurs
     */
    List<ReaderIssuance> getReaderIssuanceList(int readerID) throws ServiceException;

    /**
     * Returns history of issuing books to the reader
     * @param readerID ID of the reader whose data need to get
     * @return history of issuing books to the reader
     * @throws ServiceException if a data processing error occurs
     */
    List<ReaderIssuance> getReaderIssuanceHistoryList(int readerID) throws ServiceException;

    /**
     * Returns list of reserved books of the reader
     * @param readerID ID of the reader whose data need to get
     * @return list of reserved books of the reader
     * @throws ServiceException if a data processing error occurs
     */
    List<ReaderReservation> getReaderReservationList(int readerID) throws ServiceException;

    /**
     * Returns book reservation history to the reader
     * @param readerID ID of the reader whose data need to get
     * @return book reservation history to the reader
     * @throws ServiceException if a data processing error occurs
     */
    List<ReaderReservation> getReaderReservationHistoryList(int readerID) throws ServiceException;
}
