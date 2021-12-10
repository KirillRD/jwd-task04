package com.epam.library.dao.impl;

import com.epam.library.dao.ReservationDAO;
import com.epam.library.dao.connection_pool.ConnectionPool;
import com.epam.library.dao.exception.DAOException;
import com.epam.library.entity.Reservation;
import com.epam.library.entity.reservation.ReservationInstance;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MYSQLReservationDAO implements ReservationDAO {

    private static final ConnectionPool connectionPool = ConnectionPool.getInstance();
    private static final String MAX_ID = "MAX(id)";
    private static final String ID = "id";
    private static final String READER_ID = "reader_id";
    private static final String DATE = "date";
    private static final String INSTANCES_ID = "instances_id";
    private static final String RESERVATION_ID = "reservation_id";
    private static final String STATUS = "status";

    private static final String GET_MAX_ID_RESERVATION = "SELECT MAX(id) FROM reservation";
    private static final String GET_MAX_ID_INSTANCE_RESERVATION = "SELECT MAX(id) FROM instances_reservation";
    private static final String INSERT_RESERVATION = "INSERT INTO reservation (id, reader_id, date) VALUES (?,?,?)";
    private static final String INSERT_INSTANCE_RESERVATION = "INSERT INTO instances_reservation (id, instances_id, reservation_id, status) VALUES (?,?,?,?)";
    private static final String SELECT_RESERVATION = "SELECT * FROM reservation WHERE id=?";
    private static final String SELECT_INSTANCE_RESERVATION = "SELECT * FROM instances_reservation WHERE reservation_id=?";
    private static final String UPDATE_INSTANCE_RESERVATION = "UPDATE instances_reservation SET instances_id=?, status=? WHERE id=?";
    private static final String DELETE_RESERVATION = "DELETE FROM reservation WHERE id=?";
    private static final String DELETE_INSTANCE_RESERVATION = "DELETE FROM instances_reservation WHERE id=?";

    public MYSQLReservationDAO() {}

    @Override
    public void addReservation(Reservation reservation) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = connectionPool.getConnection();

            preparedStatement = connection.prepareStatement(GET_MAX_ID_RESERVATION);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                reservation.setId(resultSet.getInt(GET_MAX_ID_RESERVATION) + 1);
            }

            preparedStatement = connection.prepareStatement(GET_MAX_ID_INSTANCE_RESERVATION);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                int maxID = resultSet.getInt(GET_MAX_ID_RESERVATION) + 1;
                reservation.getReservationList().get(0).setId(maxID);
                for (int i = 1; i < reservation.getReservationList().size(); i++) {
                    reservation.getReservationList().get(i).setId(maxID + i);
                }
            }

            connection.setAutoCommit(false);

            preparedStatement = connection.prepareStatement(INSERT_RESERVATION);
            preparedStatement.setInt(1, reservation.getId());
            preparedStatement.setInt(2, reservation.getReaderID());
            preparedStatement.setDate(3, reservation.getDate());
            preparedStatement.executeUpdate();

            for (ReservationInstance reservationInstance : reservation.getReservationList()) {
                preparedStatement = connection.prepareStatement(INSERT_INSTANCE_RESERVATION);
                preparedStatement.setInt(1, reservationInstance.getId());
                preparedStatement.setInt(2, reservationInstance.getInstanceID());
                preparedStatement.setInt(3, reservation.getId());
                preparedStatement.setBoolean(4, reservationInstance.isStatus());
                preparedStatement.executeUpdate();
            }

            connection.commit();
            connection.setAutoCommit(true);
        } catch (SQLException e) {
            try {
                if (connection != null) {
                    connection.rollback();
                }
            } catch (SQLException ignored) {
                //TODO logger
            }
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    //TODO logger
                }
            }
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    //TODO logger
                }
            }
            if (connection != null) {
                connectionPool.releaseConnection(connection);
            }
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
                reservation.setReaderID(resultSet.getInt(READER_ID));
                reservation.setDate(resultSet.getDate(DATE));

                preparedStatement = connection.prepareStatement(SELECT_INSTANCE_RESERVATION);
                preparedStatement.setInt(1, reservationID);
                resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    ReservationInstance reservationInstance = new ReservationInstance();
                    reservationInstance.setId(resultSet.getInt(ID));
                    reservationInstance.setInstanceID(resultSet.getInt(INSTANCES_ID));
                    reservationInstance.setStatus(resultSet.getBoolean(STATUS));
                    reservation.getReservationList().add(reservationInstance);
                }
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    //TODO logger
                }
            }
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    //TODO logger
                }
            }
            if (connection != null) {
                connectionPool.releaseConnection(connection);
            }
        }
        return reservation;
    }

    @Override
    public void updateReservation(Reservation reservation) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = connectionPool.getConnection();

            connection.setAutoCommit(false);

            for (ReservationInstance reservationInstance : reservation.getReservationList()) {
                preparedStatement = connection.prepareStatement(UPDATE_INSTANCE_RESERVATION);
                preparedStatement.setInt(1, reservationInstance.getInstanceID());
                preparedStatement.setBoolean(2, reservationInstance.isStatus());
                preparedStatement.setInt(3, reservationInstance.getId());
            }

            connection.commit();
            connection.setAutoCommit(true);
        } catch (SQLException e) {
            try {
                if (connection != null) {
                    connection.rollback();
                }
            } catch (SQLException ignored) {
                //TODO logger
            }
        } finally {
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    //TODO logger
                }
            }
            if (connection != null) {
                connectionPool.releaseConnection(connection);
            }
        }
    }

    @Override
    public void deleteReservation(Reservation reservation) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = connectionPool.getConnection();

            connection.setAutoCommit(false);

            for (ReservationInstance reservationInstance : reservation.getReservationList()) {
                preparedStatement = connection.prepareStatement(DELETE_INSTANCE_RESERVATION);
                preparedStatement.setInt(1, reservationInstance.getId());
                preparedStatement.executeUpdate();
            }

            preparedStatement = connection.prepareStatement(DELETE_RESERVATION);
            preparedStatement.setInt(1, reservation.getId());
            preparedStatement.executeUpdate();

            connection.commit();
            connection.setAutoCommit(true);
        } catch (SQLException e) {
            try {
                if (connection != null) {
                    connection.rollback();
                }
            } catch (SQLException ignored) {
                //TODO logger
            }
        } finally {
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    //TODO logger
                }
            }
            if (connection != null) {
                connectionPool.releaseConnection(connection);
            }
        }
    }
}
