package com.epam.library.dao.impl;

import com.epam.library.dao.ReaderDAO;
import com.epam.library.dao.connection_pool.ConnectionPool;
import com.epam.library.dao.connection_pool.exception.ConnectionPoolException;
import com.epam.library.dao.exception.DAOException;
import com.epam.library.entity.issuance.ReaderIssuance;
import com.epam.library.entity.reservation.ReaderReservation;
import com.epam.library.constant.ReservationStatus;
import com.epam.library.entity.user.constant.Gender;
import com.epam.library.entity.user.Reader;
import com.epam.library.constant.ReaderListFilterName;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MYSQLReaderDAO implements ReaderDAO {
    private static final Logger logger = Logger.getLogger(MYSQLReaderDAO.class.getName());

    private static final ConnectionPool connectionPool = ConnectionPool.getInstance();
    private static final String ID = "id";
    private static final String NICKNAME = "nickname";
    private static final String EMAIL = "email";
    private static final String LAST_NAME = "last_name";
    private static final String FIRST_NAME = "first_name";
    private static final String FATHER_NAME = "father_name";
    private static final String BIRTHDAY = "birthday";
    private static final String GENDER = "gender";
    private static final String PASSPORT = "passport";
    private static final String ADDRESS = "address";
    private static final String PHONE = "phone";
    private static final String IMAGE = "image";
    private static final String COUNT_DEBTS = "count_debts";
    private static final String COUNT_DAYS_DEBTS = "count_days_debts";
    private static final String MIN_DATE_RESERVATION = "min_date_reservation";
    private static final String RESERVATION_DEBTS = "reservation_debts";
    private static final String COUNT_RESERVATION = "count_reservation";
    private static final String COUNT_RESERVATION_READY = "count_reservation_ready";
    private static final String COUNT_RENTAL = "count_rental";
    private static final String COUNT_DAYS_RENTAL = "count_days_rental";
    private static final String COUNT_READING_HALL_BOOKS = "count_reading_hall_books";
    private static final String RENTAL_PRICE = "rental_price";
    private static final String LOCK = "lock";

    private static final String READER_FILTER_QUERY =
            "SELECT id, nickname, email, last_name, first_name, father_name, birthday, gender, passport, address, phone, image, `lock`, " +
            "(SELECT COUNT(*) FROM issuance INNER JOIN instances ON issuance.instances_id=instances.id WHERE halls_id=1 AND reader_id=users.id AND date_return IS NULL AND date_return_planned<CURDATE()) AS count_debts, " +
            "(SELECT IFNULL(DATEDIFF(CURDATE(),MIN(date_return_planned)),0) FROM issuance INNER JOIN instances ON issuance.instances_id=instances.id WHERE halls_id=1 AND reader_id=users.id AND date_return IS NULL AND date_return_planned<CURDATE()) AS count_days_debts, " +
            "(SELECT COUNT(*) FROM issuance INNER JOIN instances ON issuance.instances_id=instances.id WHERE halls_id=2 AND reader_id=users.id AND date_return IS NULL AND date_return_planned=CURDATE()) AS count_reading_hall_books, " +
            "(SELECT COUNT(*) FROM issuance INNER JOIN instances ON issuance.instances_id=instances.id WHERE halls_id=2 AND reader_id=users.id AND date_return IS NULL AND date_return_planned<CURDATE()) AS count_rental, " +
            "(SELECT IFNULL(DATEDIFF(CURDATE(),MIN(date_return_planned)),0) FROM issuance INNER JOIN instances ON issuance.instances_id=instances.id WHERE halls_id=2 AND reader_id=users.id AND date_return IS NULL AND date_return_planned<CURDATE()) AS count_days_rental, " +
            "(SELECT MIN(date) FROM reservation WHERE reader_id=users.id AND (status='RESERVED' OR status='READY')) AS min_date_reservation, " +
            "(SELECT CASE WHEN CURDATE()<=IFNULL(MIN(date),CURDATE()) THEN 0 ELSE 1 END FROM reservation WHERE reader_id=users.id AND (status='RESERVED' OR status='READY')) AS reservation_debts, " +
            "(SELECT COUNT(*) FROM reservation WHERE reader_id=users.id AND (status='RESERVED' OR status='READY')) AS count_reservation, " +
            "(SELECT COUNT(*) FROM reservation WHERE reader_id=users.id AND status='READY') AS count_reservation_ready " +
            " FROM users WHERE roles_id=3";

    private static final String AND = " AND ";
    private static final String ORDER_BY = " ORDER BY ";
    private static final String LAST_NAME_FILTER = "users.last_name LIKE ?";
    private static final String PERCENT = "%";
    private static final String DEBTORS_FILTER =
            "(SELECT COUNT(*) FROM issuance INNER JOIN instances ON issuance.instances_id=instances.id WHERE halls_id=1 AND reader_id=users.id " +
            "AND date_return IS NULL AND date_return_planned<CURDATE())>0";
    private static final String READING_HALL_FILTER =
            "(SELECT COUNT(*) FROM issuance INNER JOIN instances ON issuance.instances_id=instances.id WHERE halls_id=2 AND reader_id=users.id " +
                    "AND date_return IS NULL AND date_return_planned=CURDATE())>0";
    private static final String RENTAL_FILTER =
            "(SELECT COUNT(*) FROM issuance INNER JOIN instances ON issuance.instances_id=instances.id WHERE halls_id=2 AND reader_id=users.id " +
            "AND date_return IS NULL AND date_return_planned<CURDATE())>0";
    private static final String RESERVATION_FILTER = "(SELECT COUNT(*) FROM reservation WHERE reader_id=users.id AND (status='RESERVED' OR status='READY'))>0";
    private static final String RESERVATION_DATE_FROM_FILTER = "(SELECT COUNT(*) FROM reservation WHERE reader_id=users.id AND (status='RESERVED' OR status='READY') AND date>=?)>0";
    private static final String RESERVATION_DATE_TO_FILTER = "(SELECT COUNT(*) FROM reservation WHERE reader_id=users.id AND (status='RESERVED' OR status='READY') AND date<=?)>0";

    private static final Map<String, String> filterValues = Map.of(
            ReaderListFilterName.LAST_NAME, LAST_NAME_FILTER,
            ReaderListFilterName.DEBTORS, DEBTORS_FILTER,
            ReaderListFilterName.READING_HALL, READING_HALL_FILTER,
            ReaderListFilterName.RENTAL, RENTAL_FILTER,
            ReaderListFilterName.RESERVATION, RESERVATION_FILTER,
            ReaderListFilterName.RESERVATION_DATE_FROM, RESERVATION_DATE_FROM_FILTER,
            ReaderListFilterName.RESERVATION_DATE_TO, RESERVATION_DATE_TO_FILTER
    );

    private static final String READER_BY_ID = "users.id=?";

    private static final String READER_ISSUANCE =
            "SELECT issuance.id AS issuance_id, books.id AS book_id, books.name AS book_name, instances.number AS instance_number, halls.short_name, books.price, date_issue, date_return, date_return_planned, lost, " +
            " CASE WHEN halls_id=1 THEN CASE WHEN IFNULL(DATEDIFF(CURDATE(),date_return_planned),0)<0 THEN 0 ELSE IFNULL(DATEDIFF(CURDATE(),date_return_planned),0) END ELSE 0 END AS count_days_debts, " +
            " CASE WHEN date_return_planned<CURDATE() AND halls_id=2 THEN IFNULL(DATEDIFF(CURDATE(),date_return_planned),0) ELSE 0 END AS count_days_rental, " +
            " CASE WHEN date_return_planned<CURDATE() AND halls_id=2 THEN IFNULL(DATEDIFF(CURDATE(),date_return_planned),0)*(SELECT CAST(param_value AS decimal(4,2)) FROM config WHERE param_name = 'rental_price') ELSE 0 END AS rental_price " +
            " FROM issuance LEFT JOIN instances LEFT JOIN books ON instances.books_id = books.id  " +
            " LEFT JOIN halls ON instances.halls_id = halls.id " +
            " ON issuance.instances_id = instances.id " +
            " WHERE reader_id=? AND date_return IS NULL ORDER BY issuance.date_issue DESC, books.name";

    private static final String READER_ISSUANCE_HISTORY =
            "SELECT issuance.id AS issuance_id, books.id AS book_id, books.name AS book_name, instances.number AS instance_number, halls.short_name, issuance.price, rental_price, date_issue, date_return, date_return_planned, lost, " +
            " CASE WHEN halls_id=1 THEN IFNULL(DATEDIFF(date_return,date_return_planned),0) ELSE 0 END AS count_days_debts, " +
            " CASE WHEN halls_id=2 THEN IFNULL(DATEDIFF(date_return,date_return_planned),0) ELSE 0 END AS count_days_rental " +
            " FROM issuance LEFT JOIN instances LEFT JOIN books ON instances.books_id = books.id " +
            " LEFT JOIN halls ON instances.halls_id = halls.id ON issuance.instances_id = instances.id " +
            " WHERE reader_id=? AND date_return IS NOT NULL ORDER BY issuance.date_return DESC, issuance.date_issue DESC, books.name";

    private static final String ISSUANCE_ID = "issuance_id";
    private static final String BOOK_ID = "book_id";
    private static final String BOOK_NAME = "book_name";
    private static final String INSTANCE_NUMBER = "instance_number";
    private static final String SHORT_NAME = "short_name";
    private static final String PRICE = "price";
    private static final String DATE_ISSUE = "date_issue";
    private static final String DATE_RETURN = "date_return";
    private static final String DATE_RETURN_PLANNED = "date_return_planned";
    private static final String LOST = "lost";
    private static final String BOOK_AUTHORS =
            "SELECT  TRIM(CONCAT(last_name,' ', " +
            "CASE WHEN (TRIM(IFNULL(first_name, ''))<>'') THEN CONCAT(LEFT(first_name,1),'. ') ELSE '' END, " +
            "CASE WHEN (TRIM(IFNULL(father_name, ''))<>'') THEN CONCAT(LEFT(father_name,1),'.') ELSE '' END)) AS name " +
            "FROM authors INNER JOIN books_authors ON authors.id=books_authors.authors_id " +
            "WHERE books_id=?";
    private static final String NAME = "name";
    private static final String COMMA = ", ";

    private static final String READER_RESERVATION =
            "SELECT reservation.id AS reservation_id, books.id AS book_id, books.name AS book_name, instances.number AS instance_number, halls.short_name, reservation.date, reservation.status, " +
                "CASE WHEN CURDATE()<=reservation.date THEN IFNULL(DATEDIFF(reservation.date,CURDATE()),0) " +
                "ELSE IFNULL(DATEDIFF(CURDATE(),reservation.date),0) END AS count_days_reservation, " +
                "CASE WHEN CURDATE()<=reservation.date THEN 0 ELSE 1 END AS reservation_debts " +
            "FROM reservation LEFT JOIN instances LEFT JOIN books ON instances.books_id = books.id " +
            "LEFT JOIN halls ON instances.halls_id = halls.id " +
            "ON reservation.instances_id = instances.id " +
            "WHERE reader_id=? AND (status='RESERVED' OR status='READY') ORDER BY reservation_debts, count_days_reservation, books.name";
    private static final String READER_RESERVATION_HISTORY =
            "SELECT reservation.id AS reservation_id, books.id AS book_id, books.name AS book_name, instances.number AS instance_number, halls.short_name, reservation.date, reservation.status, " +
                "CASE WHEN CURDATE()<=reservation.date THEN IFNULL(DATEDIFF(reservation.date,CURDATE()),0) " +
                "ELSE IFNULL(DATEDIFF(CURDATE(),reservation.date),0) END AS count_days_reservation, " +
                "CASE WHEN CURDATE()<=reservation.date THEN 0 ELSE 1 END AS reservation_debts " +
            "FROM reservation LEFT JOIN instances LEFT JOIN books ON instances.books_id = books.id " +
            "LEFT JOIN halls ON instances.halls_id = halls.id " +
            "ON reservation.instances_id = instances.id " +
            "WHERE reader_id=? AND NOT (status='RESERVED' OR status='READY') ORDER BY reservation_debts, count_days_reservation, books.name";
    private static final String RESERVATION_ID = "reservation_id";
    private static final String DATE = "date";
    private static final String STATUS = "status";
    private static final String COUNT_DAYS_RESERVATION = "count_days_reservation";
    private static final Map<String, String> sortValue = Map.of(
            ReaderListFilterName.LAST_NAME_ASCENDING, "last_name ASC",
            ReaderListFilterName.LAST_NAME_DESCENDING, "last_name DESC",
            ReaderListFilterName.DAYS_DEBT_ASCENDING, "IF(count_days_debts>0,count_days_debts,999) ASC, last_name ASC",
            ReaderListFilterName.DAYS_DEBT_DESCENDING, "count_days_debts DESC, last_name ASC",
            ReaderListFilterName.DAYS_RENTAL_ASCENDING, "IF(count_days_rental>0,count_days_rental,999) ASC, last_name ASC",
            ReaderListFilterName.DAYS_RENTAL_DESCENDING, "count_days_rental DESC, last_name ASC",
            ReaderListFilterName.RESERVATION_DATE_ASCENDING, "IF(min_date_reservation IS NOT NULL,min_date_reservation,'9999-09-09')  ASC, last_name ASC",
            ReaderListFilterName.RESERVATION_DATE_DESCENDING, "min_date_reservation DESC, last_name ASC"
    );

    private static final String PAGES_COUNT = "pages_count";
    private static final String GET_PAGES_COUNT = "SELECT COUNT(*) AS pages_count FROM ( %s ) query";
    private static final String COLON = ": ";
    private static final String PAGE_LIMIT = " LIMIT %s, 10";

    public MYSQLReaderDAO () {}

    @Override
    public Reader getReader(int readerID) throws DAOException {
        Reader reader = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = connectionPool.getConnection();

            preparedStatement = connection.prepareStatement(READER_FILTER_QUERY + AND + READER_BY_ID);
            preparedStatement.setInt(1, readerID);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                reader = new Reader();
                reader.setId(resultSet.getInt(ID));
                reader.setNickname(resultSet.getString(NICKNAME));
                reader.setEmail(resultSet.getString(EMAIL));
                reader.setLastName(resultSet.getString(LAST_NAME));
                reader.setFirstName(resultSet.getString(FIRST_NAME));
                reader.setFatherName(resultSet.getString(FATHER_NAME));
                reader.setBirthday(resultSet.getDate(BIRTHDAY));
                reader.setGender(Gender.valueOf(resultSet.getString(GENDER)));
                reader.setPassport(resultSet.getString(PASSPORT));
                reader.setAddress(resultSet.getString(ADDRESS));
                reader.setPhone(resultSet.getString(PHONE));
                reader.setImageURL(resultSet.getString(IMAGE));
                reader.setCountDebts(resultSet.getInt(COUNT_DEBTS));
                reader.setCountDaysDebts(resultSet.getInt(COUNT_DAYS_DEBTS));
                reader.setMinDateReservation(resultSet.getDate(MIN_DATE_RESERVATION));
                reader.setReservationDebts(resultSet.getBoolean(RESERVATION_DEBTS));
                reader.setCountReservation(resultSet.getInt(COUNT_RESERVATION));
                reader.setCountReservationReady(resultSet.getInt(COUNT_RESERVATION_READY));
                reader.setCountReadingHallBooks(resultSet.getInt(COUNT_READING_HALL_BOOKS));
                reader.setCountRental(resultSet.getInt(COUNT_RENTAL));
                reader.setCountDaysRental(resultSet.getInt(COUNT_DAYS_RENTAL));
                reader.setLock(resultSet.getBoolean(LOCK));
            }
            return reader;
        } catch (SQLException | ConnectionPoolException e) {
            throw new DAOException(e);
        } finally {
            try {
                connectionPool.closeConnection(resultSet, preparedStatement, connection);
            } catch (ConnectionPoolException e) {
                logger.error("Error closing resources", e);
            }
        }
    }

    @Override
    public int getPagesCount(Map<String, Object> filters) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = connectionPool.getConnection();

            StringBuilder query = new StringBuilder(READER_FILTER_QUERY);
            if (filters.size() > 1) {
                for (String filterName : filters.keySet()) {
                    if (!filterName.equals(ReaderListFilterName.SORT)) {
                        query.append(AND);
                        query.append(filterValues.get(filterName));
                    }
                }
            }
            query.append(ORDER_BY);
            query.append(sortValue.get(filters.get(ReaderListFilterName.SORT)));

            preparedStatement = connection.prepareStatement(query.toString());
            List<String> filterNames = new ArrayList<>(filters.keySet());
            for (int i = 0, n = 1; i < filterNames.size() - 1; i++, n++) {
                String filterName = filterNames.get(i);
                if (filterName.equals(ReaderListFilterName.LAST_NAME)) {
                    preparedStatement.setString(n, PERCENT + filters.get(filterNames.get(i)) + PERCENT);
                } else if (filterName.equals(ReaderListFilterName.RESERVATION_DATE_FROM) || filterName.equals(ReaderListFilterName.RESERVATION_DATE_TO)) {
                    preparedStatement.setDate(n, Date.valueOf(filters.get(filterNames.get(i)).toString()));
                } else if (filterName.equals(ReaderListFilterName.DEBTORS) || filterName.equals(ReaderListFilterName.RESERVATION)) {
                    n--;
                }
            }

            String pagesQuery = preparedStatement.toString();
            pagesQuery = pagesQuery.substring(pagesQuery.indexOf(COLON) + 2);

            preparedStatement = connection.prepareStatement(String.format(GET_PAGES_COUNT, pagesQuery));
            resultSet = preparedStatement.executeQuery();
            resultSet.next();
            int pagesCount = resultSet.getInt(PAGES_COUNT);

            return pagesCount % 10 == 0 && pagesCount != 0 ? pagesCount/10 : pagesCount/10+1;
        } catch (SQLException | ConnectionPoolException e) {
            throw new DAOException(e);
        } finally {
            try {
                connectionPool.closeConnection(resultSet, preparedStatement, connection);
            } catch (ConnectionPoolException e) {
                logger.error("Error closing resources", e);
            }
        }
    }

    @Override
    public List<Reader> getReadersByFilter(Map<String, Object> filters, int page) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        List<Reader> readers = new ArrayList<>();
        try {
            connection = connectionPool.getConnection();

            StringBuilder query = new StringBuilder(READER_FILTER_QUERY);
            if (filters.size() > 1) {
                for (String filterName : filters.keySet()) {
                    if (!filterName.equals(ReaderListFilterName.SORT)) {
                        query.append(AND);
                        query.append(filterValues.get(filterName));
                    }
                }
            }
            query.append(ORDER_BY);
            query.append(sortValue.get(filters.get(ReaderListFilterName.SORT)));

            page = 10*page-10;
            String limit = String.format(PAGE_LIMIT, page);
            query.append(limit);

            preparedStatement = connection.prepareStatement(query.toString());
            List<String> filterNames = new ArrayList<>(filters.keySet());
            for (int i = 0, n = 1; i < filterNames.size() - 1; i++, n++) {
                String filterName = filterNames.get(i);
                if (filterName.equals(ReaderListFilterName.LAST_NAME)) {
                    preparedStatement.setString(n, PERCENT + filters.get(filterNames.get(i)) + PERCENT);
                } else if (filterName.equals(ReaderListFilterName.RESERVATION_DATE_FROM) || filterName.equals(ReaderListFilterName.RESERVATION_DATE_TO)) {
                    preparedStatement.setDate(n, Date.valueOf(filters.get(filterNames.get(i)).toString()));
                } else if (filterName.equals(ReaderListFilterName.DEBTORS) || filterName.equals(ReaderListFilterName.RESERVATION)) {
                    n--;
                }
            }

            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Reader reader = new Reader();
                reader.setId(resultSet.getInt(ID));
                reader.setNickname(resultSet.getString(NICKNAME));
                reader.setEmail(resultSet.getString(EMAIL));
                reader.setLastName(resultSet.getString(LAST_NAME));
                reader.setFirstName(resultSet.getString(FIRST_NAME));
                reader.setFatherName(resultSet.getString(FATHER_NAME));
                reader.setBirthday(resultSet.getDate(BIRTHDAY));
                reader.setGender(Gender.valueOf(resultSet.getString(GENDER)));
                reader.setPassport(resultSet.getString(PASSPORT));
                reader.setAddress(resultSet.getString(ADDRESS));
                reader.setPhone(resultSet.getString(PHONE));
                reader.setImageURL(resultSet.getString(IMAGE));
                reader.setCountDebts(resultSet.getInt(COUNT_DEBTS));
                reader.setCountDaysDebts(resultSet.getInt(COUNT_DAYS_DEBTS));
                reader.setMinDateReservation(resultSet.getDate(MIN_DATE_RESERVATION));
                reader.setReservationDebts(resultSet.getBoolean(RESERVATION_DEBTS));
                reader.setCountReservation(resultSet.getInt(COUNT_RESERVATION));
                reader.setCountReservationReady(resultSet.getInt(COUNT_RESERVATION_READY));
                reader.setCountReadingHallBooks(resultSet.getInt(COUNT_READING_HALL_BOOKS));
                reader.setCountRental(resultSet.getInt(COUNT_RENTAL));
                reader.setCountDaysRental(resultSet.getInt(COUNT_DAYS_RENTAL));
                readers.add(reader);
            }
            return readers;
        } catch (SQLException | ConnectionPoolException e) {
            throw new DAOException(e);
        } finally {
            try {
                connectionPool.closeConnection(resultSet, preparedStatement, connection);
            } catch (ConnectionPoolException e) {
                logger.error("Error closing resources", e);
            }
        }
    }

    @Override
    public List<ReaderIssuance> getReaderIssuanceList(int readerID) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        List<ReaderIssuance> readerIssuanceList = new ArrayList<>();
        try {
            connection = connectionPool.getConnection();

            preparedStatement = connection.prepareStatement(READER_ISSUANCE);
            preparedStatement.setInt(1, readerID);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                ReaderIssuance readerIssuance = new ReaderIssuance();
                readerIssuance.setIssuanceID(resultSet.getInt(ISSUANCE_ID));
                readerIssuance.setBookID(resultSet.getInt(BOOK_ID));
                readerIssuance.setBookName(resultSet.getString(BOOK_NAME));
                readerIssuance.setInstanceNumber(resultSet.getString(INSTANCE_NUMBER));
                readerIssuance.setHallName(resultSet.getString(SHORT_NAME));
                readerIssuance.setBookPrice(resultSet.getDouble(PRICE));
                readerIssuance.setDateIssue(resultSet.getDate(DATE_ISSUE));
                readerIssuance.setDateReturn(resultSet.getDate(DATE_RETURN));
                readerIssuance.setDateReturnPlanned(resultSet.getDate(DATE_RETURN_PLANNED));
                readerIssuance.setLost(resultSet.getBoolean(LOST));
                readerIssuance.setCountDaysDebts(resultSet.getInt(COUNT_DAYS_DEBTS));
                readerIssuance.setCountDaysRental(resultSet.getInt(COUNT_DAYS_RENTAL));
                readerIssuance.setRentalPrice(resultSet.getDouble(RENTAL_PRICE));

                preparedStatement = connection.prepareStatement(BOOK_AUTHORS);
                preparedStatement.setInt(1, readerIssuance.getBookID());
                ResultSet resultSetList = preparedStatement.executeQuery();
                StringBuilder stringBuilder = new StringBuilder();
                while (resultSetList.next()) {
                    if (stringBuilder.toString().length() != 0) {
                        stringBuilder.append(COMMA);
                    }
                    stringBuilder.append(resultSetList.getString(NAME));
                }
                resultSetList.close();
                readerIssuance.setAuthors(stringBuilder.toString());

                readerIssuanceList.add(readerIssuance);
            }
            return readerIssuanceList;
        } catch (SQLException | ConnectionPoolException e) {
            throw new DAOException(e);
        } finally {
            try {
                connectionPool.closeConnection(resultSet, preparedStatement, connection);
            } catch (ConnectionPoolException e) {
                logger.error("Error closing resources", e);
            }
        }
    }

    @Override
    public List<ReaderIssuance> getReaderIssuanceHistoryList(int readerID) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        List<ReaderIssuance> readerIssuanceHistoryList = new ArrayList<>();
        try {
            connection = connectionPool.getConnection();

            preparedStatement = connection.prepareStatement(READER_ISSUANCE_HISTORY);
            preparedStatement.setInt(1, readerID);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                ReaderIssuance readerIssuance = new ReaderIssuance();
                readerIssuance.setIssuanceID(resultSet.getInt(ISSUANCE_ID));
                readerIssuance.setBookID(resultSet.getInt(BOOK_ID));
                readerIssuance.setBookName(resultSet.getString(BOOK_NAME));
                readerIssuance.setInstanceNumber(resultSet.getString(INSTANCE_NUMBER));
                readerIssuance.setHallName(resultSet.getString(SHORT_NAME));
                readerIssuance.setBookPrice(resultSet.getDouble(PRICE));
                readerIssuance.setDateIssue(resultSet.getDate(DATE_ISSUE));
                readerIssuance.setDateReturn(resultSet.getDate(DATE_RETURN));
                readerIssuance.setDateReturnPlanned(resultSet.getDate(DATE_RETURN_PLANNED));
                readerIssuance.setLost(resultSet.getBoolean(LOST));
                readerIssuance.setCountDaysDebts(resultSet.getInt(COUNT_DAYS_DEBTS));
                readerIssuance.setCountDaysRental(resultSet.getInt(COUNT_DAYS_RENTAL));
                readerIssuance.setRentalPrice(resultSet.getDouble(RENTAL_PRICE));

                preparedStatement = connection.prepareStatement(BOOK_AUTHORS);
                preparedStatement.setInt(1, readerIssuance.getBookID());
                ResultSet resultSetList = preparedStatement.executeQuery();
                StringBuilder stringBuilder = new StringBuilder();
                while (resultSetList.next()) {
                    if (stringBuilder.toString().length() != 0) {
                        stringBuilder.append(COMMA);
                    }
                    stringBuilder.append(resultSetList.getString(NAME));
                }
                resultSetList.close();
                readerIssuance.setAuthors(stringBuilder.toString());

                readerIssuanceHistoryList.add(readerIssuance);
            }
            return readerIssuanceHistoryList;
        } catch (SQLException | ConnectionPoolException e) {
            throw new DAOException(e);
        } finally {
            try {
                connectionPool.closeConnection(resultSet, preparedStatement, connection);
            } catch (ConnectionPoolException e) {
                logger.error("Error closing resources", e);
            }
        }
    }

    @Override
    public List<ReaderReservation> getReaderReservationList(int readerID) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        List<ReaderReservation> readerReservationList = new ArrayList<>();
        try {
            connection = connectionPool.getConnection();

            preparedStatement = connection.prepareStatement(READER_RESERVATION);
            preparedStatement.setInt(1, readerID);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                ReaderReservation readerReservation = new ReaderReservation();
                readerReservation.setReservationID(resultSet.getInt(RESERVATION_ID));
                readerReservation.setBookID(resultSet.getInt(BOOK_ID));
                readerReservation.setBookName(resultSet.getString(BOOK_NAME));
                readerReservation.setInstanceNumber(resultSet.getString(INSTANCE_NUMBER));
                readerReservation.setHallName(resultSet.getString(SHORT_NAME));
                readerReservation.setDateReservation(resultSet.getDate(DATE));
                readerReservation.setStatus(ReservationStatus.valueOf(resultSet.getString(STATUS)));
                readerReservation.setCountDaysReservation(resultSet.getInt(COUNT_DAYS_RESERVATION));
                readerReservation.setReservationDebts(resultSet.getBoolean(RESERVATION_DEBTS));

                preparedStatement = connection.prepareStatement(BOOK_AUTHORS);
                preparedStatement.setInt(1, readerReservation.getBookID());
                ResultSet resultSetList = preparedStatement.executeQuery();
                StringBuilder stringBuilder = new StringBuilder();
                while (resultSetList.next()) {
                    if (stringBuilder.toString().length() != 0) {
                        stringBuilder.append(COMMA);
                    }
                    stringBuilder.append(resultSetList.getString(NAME));
                }
                resultSetList.close();
                readerReservation.setAuthors(stringBuilder.toString());

                readerReservationList.add(readerReservation);
            }
            return readerReservationList;
        } catch (SQLException | ConnectionPoolException e) {
            throw new DAOException(e);
        } finally {
            try {
                connectionPool.closeConnection(resultSet, preparedStatement, connection);
            } catch (ConnectionPoolException e) {
                logger.error("Error closing resources", e);
            }
        }
    }

    @Override
    public List<ReaderReservation> getReaderReservationHistoryList(int readerID) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        List<ReaderReservation> readerReservationHistoryList = new ArrayList<>();
        try {
            connection = connectionPool.getConnection();

            preparedStatement = connection.prepareStatement(READER_RESERVATION_HISTORY);
            preparedStatement.setInt(1, readerID);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                ReaderReservation readerReservation = new ReaderReservation();
                readerReservation.setReservationID(resultSet.getInt(RESERVATION_ID));
                readerReservation.setBookID(resultSet.getInt(BOOK_ID));
                readerReservation.setBookName(resultSet.getString(BOOK_NAME));
                readerReservation.setInstanceNumber(resultSet.getString(INSTANCE_NUMBER));
                readerReservation.setHallName(resultSet.getString(SHORT_NAME));
                readerReservation.setDateReservation(resultSet.getDate(DATE));
                readerReservation.setStatus(ReservationStatus.valueOf(resultSet.getString(STATUS)));
                readerReservation.setCountDaysReservation(resultSet.getInt(COUNT_DAYS_RESERVATION));
                readerReservation.setReservationDebts(resultSet.getBoolean(RESERVATION_DEBTS));

                preparedStatement = connection.prepareStatement(BOOK_AUTHORS);
                preparedStatement.setInt(1, readerReservation.getBookID());
                ResultSet resultSetList = preparedStatement.executeQuery();
                StringBuilder stringBuilder = new StringBuilder();
                while (resultSetList.next()) {
                    if (stringBuilder.toString().length() != 0) {
                        stringBuilder.append(COMMA);
                    }
                    stringBuilder.append(resultSetList.getString(NAME));
                }
                resultSetList.close();
                readerReservation.setAuthors(stringBuilder.toString());

                readerReservationHistoryList.add(readerReservation);
            }
            return readerReservationHistoryList;
        } catch (SQLException | ConnectionPoolException e) {
            throw new DAOException(e);
        } finally {
            try {
                connectionPool.closeConnection(resultSet, preparedStatement, connection);
            } catch (ConnectionPoolException e) {
                logger.error("Error closing resources", e);
            }
        }
    }
}
