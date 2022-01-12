package com.epam.library.service;

import com.epam.library.entity.issuance.ReaderIssuance;
import com.epam.library.entity.reservation.ReaderReservation;
import com.epam.library.entity.user.Reader;
import com.epam.library.service.exception.ServiceException;

import java.util.List;
import java.util.Map;

public interface ReaderService {
    Reader getReader(int readerID) throws ServiceException;
    List<Reader> getReadersByFilter(Map<String, Object> filters) throws ServiceException;
    List<ReaderIssuance> getReaderIssuanceList(int readerID) throws ServiceException;
    List<ReaderIssuance> getReaderIssuanceHistoryList(int readerID) throws ServiceException;
    List<ReaderReservation> getReaderReservationList(int readerID) throws ServiceException;
    List<ReaderReservation> getReaderReservationHistoryList(int readerID) throws ServiceException;
}
