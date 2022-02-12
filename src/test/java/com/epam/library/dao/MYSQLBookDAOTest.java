package com.epam.library.dao;

import com.epam.library.dao.connection_pool.ConnectionPool;
import com.epam.library.dao.connection_pool.exception.ConnectionPoolException;
import com.epam.library.dao.exception.DAOException;
import com.epam.library.dao.impl.MYSQLBookDAO;
import com.epam.library.dao.util.ScriptRunner;
import com.epam.library.entity.Book;
import com.epam.library.entity.book.BookInfo;
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

public class MYSQLBookDAOTest {
    static final ConnectionPool connectionPool = ConnectionPool.getInstance();
    static final MYSQLBookDAO mysqlBookDAO = new MYSQLBookDAO();

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
    void addBookTest() throws DAOException, ConnectionPoolException, SQLException {
        Connection connection = connectionPool.getConnection();
        PreparedStatement preparedStatement;
        ResultSet resultSet;

        BookInfo book = new BookInfo();
        book.setName("Альпийская баллада");
        book.setPublisherID("3");
        book.setTypeID("1");
        book.setPublicationYear("2022");
        book.setPages("160");
        book.setPart("0");
        book.setIsbn("978-5-4467-0093-6");
        book.setIssn("");
        book.setAnnotation("События развиваются в период Великой Отечественной войны. Действие происходит в лагере для военнопленных, находящемся в Альпах. Во время бомбардировки лагеря нескольким узникам удаётся бежать. Среди них и русский солдат. В горах он встречает итальянскую девушку, которая тоже убежала из немецкого плена. Вместе они пытаются спастись от преследования и выжить.");
        book.setPrice(String.valueOf(Double.parseDouble("10")));
        book.setImageURL("default_image_book.jpg");
        book.getGenresID().add("3");
        book.getAuthorsID().add("6");

        preparedStatement = connection.prepareStatement("SELECT MAX(id) AS id FROM books");
        resultSet = preparedStatement.executeQuery();
        resultSet.next();
        int id = resultSet.getInt("id") + 1;

        mysqlBookDAO.addBook(book);

        book.setId(id);

        preparedStatement = connection.prepareStatement("SELECT * FROM books WHERE id=(SELECT MAX(id) FROM books)");
        resultSet = preparedStatement.executeQuery();
        resultSet.next();
        BookInfo bookDB = new BookInfo();
        bookDB.setId(resultSet.getInt("id"));
        bookDB.setName(resultSet.getString("name"));
        bookDB.setPublisherID(resultSet.getString("publishers_id"));
        bookDB.setTypeID(resultSet.getString("types_id"));
        bookDB.setPublicationYear(resultSet.getString("publication_year"));
        bookDB.setPages(resultSet.getString("pages"));
        bookDB.setPart(resultSet.getString("part"));
        bookDB.setIsbn(resultSet.getString("isbn"));
        bookDB.setIssn(resultSet.getString("issn"));
        bookDB.setAnnotation(resultSet.getString("annotation"));
        bookDB.setPrice(String.valueOf(resultSet.getDouble("price")));
        bookDB.setImageURL(resultSet.getString("image"));

        preparedStatement = connection.prepareStatement("SELECT * FROM books_genres WHERE books_id=(SELECT MAX(id) FROM books)");
        resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            bookDB.getGenresID().add(resultSet.getString("genres_id"));
        }

        preparedStatement = connection.prepareStatement("SELECT * FROM books_authors WHERE books_id=(SELECT MAX(id) FROM books)");
        resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            bookDB.getAuthorsID().add(resultSet.getString("authors_id"));
        }

        connectionPool.closeConnection(resultSet, preparedStatement, connection);

        Assertions.assertEquals(book, bookDB);
    }

    @Test
    void updateBookTest() throws DAOException, ConnectionPoolException, SQLException {
        Connection connection = connectionPool.getConnection();
        PreparedStatement preparedStatement;
        ResultSet resultSet;

        BookInfo book = new BookInfo();
        book.setId(1);
        book.setName("Журавлиный крик");
        book.setPublisherID("3");
        book.setTypeID("1");
        book.setPublicationYear("2022");
        book.setPages("432");
        book.setPart("0");
        book.setIsbn("978-5-519-62511-1");
        book.setIssn("");
        book.setAnnotation("Книги, созданные белорусским прозаиком Василем Быковым, принесли ему мировую известность и признание миллионов читателей. Пройдя сквозь ад Великой Отечественной войны, прослужив в послевоенной армии, написав полсотни произведений, жёстких, искренних и беспощадных, Василь Быков до самой своей смерти оставался «совестью» не только Белоруссии, но и каждого отдельного человека вне его национальной принадлежности. В сборник вошли повести и рассказы «Журавлиный крик», «Болото», «Колокола Хатыни», «В тумане».");
        book.setPrice(String.valueOf(Double.parseDouble("10")));
        book.setImageURL("1.jpg");
        book.getGenresID().add("3");
        book.getAuthorsID().add("6");

        mysqlBookDAO.updateBook(book);

        preparedStatement = connection.prepareStatement("SELECT * FROM books WHERE id=1");
        resultSet = preparedStatement.executeQuery();
        resultSet.next();
        BookInfo bookDB = new BookInfo();
        bookDB.setId(resultSet.getInt("id"));
        bookDB.setName(resultSet.getString("name"));
        bookDB.setPublisherID(resultSet.getString("publishers_id"));
        bookDB.setTypeID(resultSet.getString("types_id"));
        bookDB.setPublicationYear(resultSet.getString("publication_year"));
        bookDB.setPages(resultSet.getString("pages"));
        bookDB.setPart(resultSet.getString("part"));
        bookDB.setIsbn(resultSet.getString("isbn"));
        bookDB.setIssn(resultSet.getString("issn"));
        bookDB.setAnnotation(resultSet.getString("annotation"));
        bookDB.setPrice(String.valueOf(resultSet.getDouble("price")));
        bookDB.setImageURL(resultSet.getString("image"));

        preparedStatement = connection.prepareStatement("SELECT * FROM books_genres WHERE books_id=1");
        resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            bookDB.getGenresID().add(resultSet.getString("genres_id"));
        }

        preparedStatement = connection.prepareStatement("SELECT * FROM books_authors WHERE books_id=1");
        resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            bookDB.getAuthorsID().add(resultSet.getString("authors_id"));
        }

        connectionPool.closeConnection(resultSet, preparedStatement, connection);

        Assertions.assertEquals(book, bookDB);
    }

    @Test
    void getBookTest() throws DAOException, ConnectionPoolException, SQLException {
        Connection connection = connectionPool.getConnection();
        PreparedStatement preparedStatement;

        connection.setAutoCommit(false);
        preparedStatement = connection.prepareStatement(
                "INSERT INTO books (id, name, publishers_id, types_id, publication_year, pages, part, isbn, issn, annotation, price, image) " +
                    "VALUES (100,'Круглянский мост',3,1,2022,144,0,'978-5-519-61543-3','','Книги, созданные белорусским прозаиком Василем Быковым, принесли ему мировую известность и признание миллионов читателей. Пройдя сквозь ад Великой Отечественной войны, прослужив в послевоенной армии, написав полсотни произведений, жёстких, искренних и беспощадных, Василь Быков до самой своей смерти оставался \"совестью\" не только Белоруссии, но и каждого отдельного человека вне его национальной принадлежности.'," +
                    "'10.0','default_image_book.jpg')");
        preparedStatement.executeUpdate();
        preparedStatement = connection.prepareStatement("INSERT INTO books_authors (books_id, authors_id) VALUES (100,6)");
        preparedStatement.executeUpdate();
        preparedStatement = connection.prepareStatement("INSERT INTO books_genres (books_id, genres_id) VALUES (100,3)");
        preparedStatement.executeUpdate();
        connection.commit();
        connection.setAutoCommit(true);

        Book book = new Book();
        book.setId(100);
        book.setName("Круглянский мост");
        book.setPublisherID(3);
        book.setTypeID(1);
        book.setPublicationYear(2022);
        book.setPages(144);
        book.setPart(0);
        book.setIsbn("978-5-519-61543-3");
        book.setIssn("");
        book.setAnnotation("Книги, созданные белорусским прозаиком Василем Быковым, принесли ему мировую известность и признание миллионов читателей. Пройдя сквозь ад Великой Отечественной войны, прослужив в послевоенной армии, написав полсотни произведений, жёстких, искренних и беспощадных, Василь Быков до самой своей смерти оставался \"совестью\" не только Белоруссии, но и каждого отдельного человека вне его национальной принадлежности.");
        book.setPrice(10);
        book.setImageURL("default_image_book.jpg");
        book.getGenresID().add(3);
        book.getAuthorsID().add(6);

        Book bookDB = mysqlBookDAO.getBook(100);

        connectionPool.closeConnection(preparedStatement, connection);

        Assertions.assertEquals(book, bookDB);
    }

    @Test
    void deleteBookTest() throws DAOException, ConnectionPoolException, SQLException {
        Connection connection = connectionPool.getConnection();
        PreparedStatement preparedStatement;

        connection.setAutoCommit(false);
        preparedStatement = connection.prepareStatement("INSERT INTO books (id, name, publishers_id, types_id, publication_year, pages, part, isbn, issn, annotation, price, image) " +
                "VALUES (110,'Его батальон',3,1,2022,224,0,'978-5-275-01199-7','','Повесть Василя Быкова \"Его батальон\" заканчивается словами: \"Война продолжалась\". Взятие высоты, описываемое автором - лишь один из эпизодов войны, которых комбату Волошину предстоит пережить немало и, может быть, погибнуть в одном из них. Но, как бы ни было тяжело на фронте, всегда надо оставаться человеком: \"И чем значительнее в человеке истинно человеческое, тем важнее для него своя собственная жизнь и жизнь окружающих его людей\".'," +
                "'10.0','default_image_book.jpg')");
        preparedStatement.executeUpdate();
        preparedStatement = connection.prepareStatement("INSERT INTO books_authors (books_id, authors_id) VALUES (110,6)");
        preparedStatement.executeUpdate();
        preparedStatement = connection.prepareStatement("INSERT INTO books_genres (books_id, genres_id) VALUES (110,3)");
        preparedStatement.executeUpdate();
        connection.commit();
        connection.setAutoCommit(true);

        boolean result = mysqlBookDAO.deleteBook(110);

        connectionPool.closeConnection(preparedStatement, connection);

        Assertions.assertTrue(result);
    }

    @Test
    void bookExistsTest() throws DAOException {
        boolean result = mysqlBookDAO.standardNumberExists(0, "978-5-699-34546-5", "");

        Assertions.assertTrue(result);
    }
}
