package com.epam.library.dao;

import com.epam.library.dao.exception.DAOException;
import com.epam.library.entity.issuance.ReaderIssuance;
import com.epam.library.entity.reservation.ReaderReservation;
import com.epam.library.entity.user.Reader;

import java.util.List;
import java.util.Map;

public interface ReaderDAO {
    Reader getReader(int readerID) throws DAOException;
    List<Reader> getReadersByFilter(Map<String, Object> filters, int page) throws DAOException;
    int getPagesCount() throws DAOException;
    List<ReaderIssuance> getReaderIssuanceList(int readerID) throws DAOException;
    List<ReaderIssuance> getReaderIssuanceHistoryList(int readerID) throws DAOException;
    List<ReaderReservation> getReaderReservationList(int readerID) throws DAOException;
    List<ReaderReservation> getReaderReservationHistoryList(int readerID) throws DAOException;
}
