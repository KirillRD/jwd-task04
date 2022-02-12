package com.epam.library.dao;

import com.epam.library.dao.connection_pool.ConnectionPool;
import com.epam.library.dao.connection_pool.exception.ConnectionPoolException;
import com.epam.library.dao.exception.DAOException;
import com.epam.library.dao.impl.MYSQLPublisherDAO;
import com.epam.library.dao.util.ScriptRunner;
import com.epam.library.entity.book.dictionary.Publisher;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MYSQLPublisherDAOTest {

    static final ConnectionPool connectionPool = ConnectionPool.getInstance();
    static final MYSQLPublisherDAO mysqlPublisherDAO = new MYSQLPublisherDAO();

    @BeforeAll
    static void createTestDB() throws ConnectionPoolException, SQLException, IOException {
        connectionPool.create();
        Connection connection = connectionPool.getConnection();
        ScriptRunner scriptRunner = new ScriptRunner(connection);
        Reader reader_db = new InputStreamReader(new FileInputStream("src/test/resources/library_db.sql"), StandardCharsets.UTF_8);
        Reader reader_data = new InputStreamReader(new FileInputStream("src/test/resources/library_data.sql"), StandardCharsets.UTF_8);
        scriptRunner.runScript(reader_db);
        scriptRunner.runScript(reader_data);
        connectionPool.releaseConnection(connection);
    }

    @AfterAll
    static void shutdownTestDB() throws ConnectionPoolException {
        connectionPool.shutdown();
    }

    @Test
    void addPublisherTest() throws DAOException, ConnectionPoolException, SQLException {
        Connection connection = connectionPool.getConnection();
        PreparedStatement preparedStatement;
        ResultSet resultSet;

        Publisher publisher = new Publisher();
        publisher.setName("Вышэйшая школа");
        publisher.setCity("Минск");

        preparedStatement = connection.prepareStatement("SELECT MAX(id) AS id FROM publishers");
        resultSet = preparedStatement.executeQuery();
        resultSet.next();
        int id = resultSet.getInt("id") + 1;

        mysqlPublisherDAO.addPublisher(publisher);

        publisher.setId(id);

        preparedStatement = connection.prepareStatement("SELECT * FROM publishers WHERE id=(SELECT MAX(id) FROM publishers)");
        resultSet = preparedStatement.executeQuery();
        resultSet.next();
        Publisher publisherDB = new Publisher();
        publisherDB.setId(resultSet.getInt("id"));
        publisherDB.setName(resultSet.getString("name"));
        publisherDB.setCity(resultSet.getString("city"));

        connectionPool.closeConnection(resultSet, preparedStatement, connection);

        Assertions.assertEquals(publisher, publisherDB);
    }

    @Test
    void updatePublisherTest() throws DAOException, ConnectionPoolException, SQLException {
        Connection connection = connectionPool.getConnection();
        PreparedStatement preparedStatement;
        ResultSet resultSet;

        Publisher publisher = new Publisher();
        publisher.setId(1);
        publisher.setName("Белый ветер");
        publisher.setCity("Мозырь");

        mysqlPublisherDAO.updatePublisher(publisher);

        preparedStatement = connection.prepareStatement("SELECT * FROM publishers WHERE id=1");
        resultSet = preparedStatement.executeQuery();
        resultSet.next();
        Publisher publisherDB = new Publisher();
        publisherDB.setId(resultSet.getInt("id"));
        publisherDB.setName(resultSet.getString("name"));
        publisherDB.setCity(resultSet.getString("city"));

        connectionPool.closeConnection(resultSet, preparedStatement, connection);

        Assertions.assertEquals(publisher, publisherDB);
    }

    @Test
    void getPublisherTest() throws DAOException, ConnectionPoolException, SQLException {
        Connection connection = connectionPool.getConnection();
        PreparedStatement preparedStatement;

        connection.setAutoCommit(false);
        preparedStatement = connection.prepareStatement("INSERT INTO publishers (id, name, city) VALUES (100,'Народная асвета','Минск')");
        preparedStatement.executeUpdate();
        connection.commit();
        connection.setAutoCommit(true);

        Publisher publisher = new Publisher();
        publisher.setId(100);
        publisher.setName("Народная асвета");
        publisher.setCity("Минск");

        Publisher publisherDB = mysqlPublisherDAO.getPublisher(100);

        connectionPool.closeConnection(preparedStatement, connection);

        Assertions.assertEquals(publisher, publisherDB);
    }

    @Test
    void deletePublisherTest() throws DAOException, ConnectionPoolException, SQLException {
        Connection connection = connectionPool.getConnection();
        PreparedStatement preparedStatement;

        connection.setAutoCommit(false);
        preparedStatement = connection.prepareStatement("INSERT INTO publishers (id, name, city) VALUES (110,'Аверсэв','Минск')");
        preparedStatement.executeUpdate();
        connection.commit();
        connection.setAutoCommit(true);

        boolean result = mysqlPublisherDAO.deletePublisher(110);

        connectionPool.closeConnection(preparedStatement, connection);

        Assertions.assertTrue(result);
    }

    @Test
    void publisherExistsTest() throws DAOException {
        Publisher publisher = new Publisher();
        publisher.setName("Эксмо");

        boolean result = mysqlPublisherDAO.publisherExists(publisher);

        Assertions.assertTrue(result);
    }
}
