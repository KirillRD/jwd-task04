package com.epam.library.service.impl;

import com.epam.library.dao.DAOProvider;
import com.epam.library.dao.ReaderDAO;
import com.epam.library.dao.exception.DAOException;
import com.epam.library.entity.issuance.ReaderIssuance;
import com.epam.library.entity.reservation.ReaderReservation;
import com.epam.library.entity.user.Reader;
import com.epam.library.constant.ReaderListFilterName;
import com.epam.library.service.ReaderService;
import com.epam.library.service.exception.GeneralException;
import com.epam.library.service.exception.ServiceException;
import com.epam.library.service.validation.Validator;

import java.util.List;
import java.util.Map;

public class ReaderServiceImpl implements ReaderService {
    private static final ReaderDAO readerDAO = DAOProvider.getInstance().getReaderDAO();
    private static final Validator validator = Validator.getInstance();

    public ReaderServiceImpl() {}

    @Override
    public Reader getReader(int readerID) throws ServiceException {
        try {
            return readerDAO.getReader(readerID);
        } catch (DAOException e) {
            throw new GeneralException(e);
        }
    }

    @Override
    public List<Reader> getReadersByFilter(Map<String, Object> filters, int page) throws ServiceException {
        try {
            if (filters.get(ReaderListFilterName.RESERVATION_DATE_FROM) != null) {
                if (!validator.isDate(filters.get(ReaderListFilterName.RESERVATION_DATE_FROM).toString())) {
                    filters.remove(ReaderListFilterName.RESERVATION_DATE_FROM);
                }
            }

            if (filters.get(ReaderListFilterName.RESERVATION_DATE_TO) != null) {
                if (!validator.isDate(filters.get(ReaderListFilterName.RESERVATION_DATE_TO).toString())) {
                    filters.remove(ReaderListFilterName.RESERVATION_DATE_TO);
                }
            }

            return readerDAO.getReadersByFilter(filters, page);
        } catch (DAOException e) {
            throw new GeneralException(e);
        }
    }

    @Override
    public int getPagesCount(Map<String, Object> filters) throws ServiceException {
        try {
            return readerDAO.getPagesCount(filters);
        } catch (DAOException e) {
            throw new GeneralException(e);
        }
    }

    @Override
    public List<ReaderIssuance> getReaderIssuanceList(int readerID) throws ServiceException {
        try {
            return readerDAO.getReaderIssuanceList(readerID);
        } catch (DAOException e) {
            throw new GeneralException(e);
        }
    }

    @Override
    public List<ReaderIssuance> getReaderIssuanceHistoryList(int readerID) throws ServiceException {
        try {
            return readerDAO.getReaderIssuanceHistoryList(readerID);
        } catch (DAOException e) {
            throw new GeneralException(e);
        }
    }

    @Override
    public List<ReaderReservation> getReaderReservationList(int readerID) throws ServiceException {
        try {
            return readerDAO.getReaderReservationList(readerID);
        } catch (DAOException e) {
            throw new GeneralException(e);
        }
    }

    @Override
    public List<ReaderReservation> getReaderReservationHistoryList(int readerID) throws ServiceException {
        try {
            return readerDAO.getReaderReservationHistoryList(readerID);
        } catch (DAOException e) {
            throw new GeneralException(e);
        }
    }
}
