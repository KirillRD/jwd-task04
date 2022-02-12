package com.epam.library.dao;

import com.epam.library.dao.connection_pool.ConnectionPool;
import com.epam.library.dao.connection_pool.exception.ConnectionPoolException;
import com.epam.library.dao.exception.DAOException;
import com.epam.library.dao.impl.MYSQLAuthorDAO;
import com.epam.library.dao.util.ScriptRunner;
import com.epam.library.entity.book.dictionary.Author;
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

public class MYSQLAuthorDAOTest {

    static final ConnectionPool connectionPool = ConnectionPool.getInstance();
    static final MYSQLAuthorDAO mysqlAuthorDAO = new MYSQLAuthorDAO();

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
    void addAuthorTest() throws DAOException, ConnectionPoolException, SQLException {
        Connection connection = connectionPool.getConnection();
        PreparedStatement preparedStatement;
        ResultSet resultSet;

        Author author = new Author();
        author.setFirstName("Максим");
        author.setLastName("Богданович");

        preparedStatement = connection.prepareStatement("SELECT MAX(id) AS id FROM authors");
        resultSet = preparedStatement.executeQuery();
        resultSet.next();
        int id = resultSet.getInt("id") + 1;

        mysqlAuthorDAO.addAuthor(author);

        author.setId(id);

        preparedStatement = connection.prepareStatement("SELECT * FROM authors WHERE id=(SELECT MAX(id) FROM authors)");
        resultSet = preparedStatement.executeQuery();
        resultSet.next();
        Author authorDB = new Author();
        authorDB.setId(resultSet.getInt("id"));
        authorDB.setFirstName(resultSet.getString("first_name"));
        authorDB.setLastName(resultSet.getString("last_name"));

        connectionPool.closeConnection(resultSet, preparedStatement, connection);

        Assertions.assertEquals(author, authorDB);
    }

    @Test
    void updateAuthorTest() throws DAOException, ConnectionPoolException, SQLException {
        Connection connection = connectionPool.getConnection();
        PreparedStatement preparedStatement;
        ResultSet resultSet;

        Author author = new Author();
        author.setId(1);
        author.setFirstName("Янка");
        author.setLastName("Купала");

        mysqlAuthorDAO.updateAuthor(author);

        preparedStatement = connection.prepareStatement("SELECT * FROM authors WHERE id=1");
        resultSet = preparedStatement.executeQuery();
        resultSet.next();
        Author authorDB = new Author();
        authorDB.setId(resultSet.getInt("id"));
        authorDB.setFirstName(resultSet.getString("first_name"));
        authorDB.setLastName(resultSet.getString("last_name"));

        connectionPool.closeConnection(resultSet, preparedStatement, connection);

        Assertions.assertEquals(author, authorDB);
    }

    @Test
    void getAuthorTest() throws DAOException, ConnectionPoolException, SQLException {
        Connection connection = connectionPool.getConnection();
        PreparedStatement preparedStatement;

        connection.setAutoCommit(false);
        preparedStatement = connection.prepareStatement("INSERT INTO authors (id, first_name, last_name) VALUES (100,'Кузьма','Чорный')");
        preparedStatement.executeUpdate();
        connection.commit();
        connection.setAutoCommit(true);

        Author author = new Author();
        author.setId(100);
        author.setFirstName("Кузьма");
        author.setLastName("Чорный");

        Author authorDB = mysqlAuthorDAO.getAuthor(100);

        connectionPool.closeConnection(preparedStatement, connection);

        Assertions.assertEquals(author, authorDB);
    }

    @Test
    void deleteAuthorTest() throws DAOException, ConnectionPoolException, SQLException {
        Connection connection = connectionPool.getConnection();
        PreparedStatement preparedStatement;

        connection.setAutoCommit(false);
        preparedStatement = connection.prepareStatement("INSERT INTO authors (id, first_name, last_name) VALUES (110,'Светлана','Алексиевич')");
        preparedStatement.executeUpdate();
        connection.commit();
        connection.setAutoCommit(true);

        boolean result = mysqlAuthorDAO.deleteAuthor(110);

        connectionPool.closeConnection(preparedStatement, connection);

        Assertions.assertTrue(result);
    }

    @Test
    void authorExistsTest() throws DAOException {
        Author author = new Author();
        author.setFirstName("Василий");
        author.setLastName("Быков");
        author.setFatherName("");

        boolean result = mysqlAuthorDAO.authorExists(author);

        Assertions.assertTrue(result);
    }
}
