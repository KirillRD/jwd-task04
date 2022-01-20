package com.epam.library.dao.impl;

import com.epam.library.dao.ReservationDAO;
import com.epam.library.dao.connection_pool.ConnectionPool;
import com.epam.library.dao.connection_pool.exception.ConnectionPoolException;
import com.epam.library.dao.exception.DAOException;
import com.epam.library.entity.Reservation;
import com.epam.library.entity.reservation.ReservationInfo;
import com.epam.library.entity.reservation.ReservationStatus;

import java.sql.*;

public class MYSQLReservationDAO implements ReservationDAO {

    private static final ConnectionPool connectionPool = ConnectionPool.getInstance();
    private static final String MAX_ID = "MAX(id)";
    private static final String ID = "id";
    private static final String READER_ID = "reader_id";
    private static final String DATE = "date";
    private static final String INSTANCES_ID = "instances_id";
    private static final String STATUS = "status";

    private static final String GET_MAX_ID_RESERVATION = "SELECT MAX(id) FROM reservation";
    private static final String INSERT_RESERVATION =
            "INSERT INTO reservation (id, instances_id, reader_id, date, status) VALUES (?,?,?,?,'RESERVED')";
    private static final String GET_FREE_INSTANCE =
            "SELECT instances.id " +
            "FROM instances " +
            "WHERE books_id=? AND halls_id=? AND date_write_off IS NULL AND " +
            "NOT EXISTS(SELECT * FROM issuance WHERE instances_id=instances.id AND (date_return IS NULL OR lost=1)) AND " +
            "NOT EXISTS(SELECT * FROM reservation WHERE instances_id=instances.id AND (status='RESERVED' OR status='READY')) LIMIT 1";
    private static final String SELECT_RESERVATION = "SELECT * FROM reservation WHERE id=?";
    private static final String UPDATE_RESERVATION = "UPDATE reservation SET status=?, instances_id=? WHERE id=?";
    private static final String GET_RESERVATION_NOT_RESERVED = "SELECT * FROM reservation WHERE id=? AND status!='RESERVED'";
    private static final String DELETE_RESERVATION = "DELETE FROM reservation WHERE id=?";

    public MYSQLReservationDAO() {}

    @Override
    public boolean addReservation(ReservationInfo reservation) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = connectionPool.getConnection();

            preparedStatement = connection.prepareStatement(GET_FREE_INSTANCE);
            preparedStatement.setInt(1, reservation.getBookID());
            preparedStatement.setInt(2, Integer.parseInt(reservation.getHallID()));
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                reservation.setInstanceID(resultSet.getInt(ID));
            } else {
                return false;
            }

            preparedStatement = connection.prepareStatement(GET_MAX_ID_RESERVATION);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                reservation.setId(resultSet.getInt(MAX_ID) + 1);
            }

            preparedStatement = connection.prepareStatement(INSERT_RESERVATION);
            preparedStatement.setInt(1, reservation.getId());
            preparedStatement.setInt(2, reservation.getInstanceID());
            preparedStatement.setInt(3, reservation.getReaderID());
            preparedStatement.setDate(4, Date.valueOf(reservation.getDate()));
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException | ConnectionPoolException e) {
            throw new DAOException(e);
        } finally {
            try {
                connectionPool.closeConnection(resultSet, preparedStatement);
            } catch (ConnectionPoolException e) {

            }
            connectionPool.releaseConnection(connection);
        }
    }

    @Override
    public Reservation getReservation(int reservationID) throws DAOException {
        Reservation reservation = new Reservation();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = connectionPool.getConnection();

            preparedStatement = connection.prepareStatement(SELECT_RESERVATION);
            preparedStatement.setInt(1, reservationID);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                reservation.setId(reservationID);
                reservation.setInstanceID(resultSet.getInt(INSTANCES_ID));
                reservation.setReaderID(resultSet.getInt(READER_ID));
                reservation.setDate(resultSet.getDate(DATE));
                reservation.setStatus(ReservationStatus.valueOf(resultSet.getString(STATUS)));
            }
            return reservation;
        } catch (SQLException | ConnectionPoolException e) {
            throw new DAOException(e);
        } finally {
            try {
                connectionPool.closeConnection(resultSet, preparedStatement);
            } catch (ConnectionPoolException e) {

            }
            connectionPool.releaseConnection(connection);
        }
    }

    @Override
    public void updateReservation(Reservation reservation) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = connectionPool.getConnection();

            preparedStatement = connection.prepareStatement(UPDATE_RESERVATION);
            preparedStatement.setString(1, reservation.getStatus().name());
            preparedStatement.setInt(2, reservation.getInstanceID());
            preparedStatement.setInt(3, reservation.getId());
            preparedStatement.executeUpdate();

        } catch (SQLException | ConnectionPoolException e) {
            throw new DAOException(e);
        } finally {
            try {
                connectionPool.closeConnection(preparedStatement);
            } catch (ConnectionPoolException e) {

            }
            connectionPool.releaseConnection(connection);
        }
    }

    @Override
    public boolean deleteReservation(int reservationID) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = connectionPool.getConnection();

            preparedStatement = connection.prepareStatement(GET_RESERVATION_NOT_RESERVED);
            preparedStatement.setInt(1, reservationID);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return false;
            }

            preparedStatement = connection.prepareStatement(DELETE_RESERVATION);
            preparedStatement.setInt(1, reservationID);
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException | ConnectionPoolException e) {
            throw new DAOException(e);
        } finally {
            try {
                connectionPool.closeConnection(resultSet, preparedStatement);
            } catch (ConnectionPoolException e) {

            }
            connectionPool.releaseConnection(connection);
        }
    }
}
