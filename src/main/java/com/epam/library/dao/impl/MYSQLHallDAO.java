package com.epam.library.dao.impl;

import com.epam.library.dao.HallDAO;
import com.epam.library.dao.connection_pool.ConnectionPool;
import com.epam.library.dao.connection_pool.exception.ConnectionPoolException;
import com.epam.library.dao.exception.DAOException;
import com.epam.library.entity.instance.hall.Hall;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MYSQLHallDAO implements HallDAO {
    private static final Logger logger = Logger.getLogger(MYSQLHallDAO.class.getName());

    private static final ConnectionPool connectionPool = ConnectionPool.getInstance();
    private static final String ID = "id";
    private static final String NAME = "name";
    private static final String SHORT_NAME = "short_name";

    private static final String SELECT_HALLS = "SELECT * FROM halls";

    public MYSQLHallDAO() {}

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
                Hall hall = new Hall();
                hall.setId(resultSet.getInt(ID));
                hall.setName(resultSet.getString(NAME));
                hall.setShortName(resultSet.getString(SHORT_NAME));
                halls.add(hall);
            }
            return halls;
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
