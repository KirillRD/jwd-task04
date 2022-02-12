package com.epam.library.dao;

import com.epam.library.dao.connection_pool.ConnectionPool;
import com.epam.library.dao.connection_pool.exception.ConnectionPoolException;
import com.epam.library.dao.exception.DAOException;
import com.epam.library.dao.impl.MYSQLGenreDAO;
import com.epam.library.dao.util.ScriptRunner;
import com.epam.library.entity.book.dictionary.Genre;
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

public class MYSQLGenreDAOTest {
    static final ConnectionPool connectionPool = ConnectionPool.getInstance();
    static final MYSQLGenreDAO mysqlGenreDAO = new MYSQLGenreDAO();

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
    void addGenreTest() throws DAOException, ConnectionPoolException, SQLException {
        Connection connection = connectionPool.getConnection();
        PreparedStatement preparedStatement;
        ResultSet resultSet;

        Genre genre = new Genre();
        genre.setName("Юмор");

        preparedStatement = connection.prepareStatement("SELECT MAX(id) AS id FROM genres");
        resultSet = preparedStatement.executeQuery();
        resultSet.next();
        int id = resultSet.getInt("id") + 1;

        mysqlGenreDAO.addGenre(genre);

        genre.setId(id);

        preparedStatement = connection.prepareStatement("SELECT * FROM genres WHERE id=(SELECT MAX(id) FROM genres)");
        resultSet = preparedStatement.executeQuery();
        resultSet.next();
        Genre genreDB = new Genre();
        genreDB.setId(resultSet.getInt("id"));
        genreDB.setName(resultSet.getString("name"));

        connectionPool.closeConnection(resultSet, preparedStatement, connection);

        Assertions.assertEquals(genre, genreDB);
    }

    @Test
    void updateGenreTest() throws DAOException, ConnectionPoolException, SQLException {
        Connection connection = connectionPool.getConnection();
        PreparedStatement preparedStatement;
        ResultSet resultSet;

        Genre genre = new Genre();
        genre.setId(1);
        genre.setName("Философия");

        mysqlGenreDAO.updateGenre(genre);

        preparedStatement = connection.prepareStatement("SELECT * FROM genres WHERE id=1");
        resultSet = preparedStatement.executeQuery();
        resultSet.next();
        Genre genreDB = new Genre();
        genreDB.setId(resultSet.getInt("id"));
        genreDB.setName(resultSet.getString("name"));

        connectionPool.closeConnection(resultSet, preparedStatement, connection);

        Assertions.assertEquals(genre, genreDB);
    }

    @Test
    void getGenreTest() throws DAOException, ConnectionPoolException, SQLException {
        Connection connection = connectionPool.getConnection();
        PreparedStatement preparedStatement;

        connection.setAutoCommit(false);
        preparedStatement = connection.prepareStatement("INSERT INTO genres (id, name) VALUES (100,'Ужасы')");
        preparedStatement.executeUpdate();
        connection.commit();
        connection.setAutoCommit(true);

        Genre genre = new Genre();
        genre.setId(100);
        genre.setName("Ужасы");

        Genre genreDB = mysqlGenreDAO.getGenre(100);

        connectionPool.closeConnection(preparedStatement, connection);

        Assertions.assertEquals(genre, genreDB);
    }

    @Test
    void deleteGenreTest() throws DAOException, ConnectionPoolException, SQLException {
        Connection connection = connectionPool.getConnection();
        PreparedStatement preparedStatement;

        connection.setAutoCommit(false);
        preparedStatement = connection.prepareStatement("INSERT INTO genres (id, name) VALUES (110,'Медицина')");
        preparedStatement.executeUpdate();
        connection.commit();
        connection.setAutoCommit(true);

        boolean result = mysqlGenreDAO.deleteGenre(110);

        connectionPool.closeConnection(preparedStatement, connection);

        Assertions.assertTrue(result);
    }

    @Test
    void genreExistsTest() throws DAOException {
        Genre genre = new Genre();
        genre.setName("Приключения");

        boolean result = mysqlGenreDAO.genreExists(genre);

        Assertions.assertTrue(result);
    }
}
