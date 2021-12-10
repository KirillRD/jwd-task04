package com.epam.library.dao.impl;

import com.epam.library.dao.HallDAO;
import com.epam.library.dao.connection_pool.ConnectionPool;
import com.epam.library.dao.exception.DAOException;
import com.epam.library.entity.instance.Hall;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MYSQLHallDAO implements HallDAO {

    private static final ConnectionPool connectionPool = ConnectionPool.getInstance();
    private static final String COUNT_HALLS = "count_halls";
    private static final String COUNT_INSTANCES = "count_instances";
    private static final String MAX_ID_HALL = "MAX(id)";
    private static final String ID = "id";
    private static final String NAME = "name";
    private static final String SHORT_NAME = "short_name";

    private static final String GET_HALL_BY_NAME = "SELECT COUNT(1) AS count_halls FROM halls WHERE name=?";
    private static final String GET_MAX_ID_HALL = "SELECT MAX(id) FROM halls";
    private static final String INSERT_HALL = "INSERT INTO halls (id, name, short_name) VALUES (?,?,?)";
    private static final String SELECT_HALL = "SELECT * FROM halls WHERE id=?";
    private static final String UPDATE_HALL = "UPDATE halls SET name=?, short_name=? WHERE id=?";
    private static final String GET_INSTANCES = "SELECT COUNT(1) AS count_instances FROM instances WHERE halls_id=?";
    private static final String DELETE_HALL = "DELETE FROM halls WHERE id=?";
    private static final String SELECT_HALLS = "SELECT * FROM halls";

    public MYSQLHallDAO() {}

    @Override
    public void addHall(Hall hall) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = connectionPool.getConnection();

            preparedStatement = connection.prepareStatement(GET_HALL_BY_NAME);
            preparedStatement.setString(1, hall.getName());
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                if (resultSet.getInt(COUNT_HALLS) == 0) {
                    preparedStatement = connection.prepareStatement(GET_MAX_ID_HALL);
                    resultSet = preparedStatement.executeQuery();
                    if (resultSet.next()) {
                        hall.setId(resultSet.getInt(MAX_ID_HALL) + 1);
                    }

                    preparedStatement = connection.prepareStatement(INSERT_HALL);
                    preparedStatement.setInt(1, hall.getId());
                    preparedStatement.setString(2, hall.getName());
                    preparedStatement.setString(3, hall.getShortName());
                    preparedStatement.executeUpdate();
                } else {
                    //TODO logger
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
    }

    @Override
    public Hall getHall(int hallID) throws DAOException {
        Hall hall = new Hall();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = connectionPool.getConnection();

            preparedStatement = connection.prepareStatement(SELECT_HALL);
            preparedStatement.setInt(1, hallID);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                hall.setId(hallID);
                hall.setName(resultSet.getString(NAME));
                hall.setShortName(resultSet.getString(SHORT_NAME));
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
        return hall;
    }

    @Override
    public void updateHall(Hall hall) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = connectionPool.getConnection();

            preparedStatement = connection.prepareStatement(UPDATE_HALL);
            preparedStatement.setString(1, hall.getName());
            preparedStatement.setString(2, hall.getShortName());
            preparedStatement.setInt(3, hall.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException(e);
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
    public void deleteHall(Hall hall) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = connectionPool.getConnection();

            preparedStatement = connection.prepareStatement(GET_INSTANCES);
            preparedStatement.setInt(1, hall.getId());
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                if (resultSet.getInt(COUNT_INSTANCES) == 0) {
                    preparedStatement = connection.prepareStatement(DELETE_HALL);
                    preparedStatement.setInt(1, hall.getId());
                    preparedStatement.executeUpdate();
                } else {
                    //TODO logger
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
    }

    @Override
    public List<Hall> getHallsList() throws DAOException {
        List<Hall> halls = new ArrayList<>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = connectionPool.getConnection();

            preparedStatement = connection.prepareStatement(SELECT_HALLS);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                halls.add(new Hall());
                halls.get(halls.size() - 1).setId(resultSet.getInt(ID));
                halls.get(halls.size() - 1).setName(resultSet.getString(NAME));
                halls.get(halls.size() - 1).setShortName(resultSet.getString(SHORT_NAME));
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
        return halls;
    }
}