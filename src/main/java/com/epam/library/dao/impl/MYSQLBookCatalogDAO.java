package com.epam.library.dao.impl;

import com.epam.library.dao.BookCatalogDAO;
import com.epam.library.dao.connection_pool.ConnectionPool;
import com.epam.library.dao.exception.DAOException;
import com.epam.library.entity.book.catalog.BookCatalog;
import com.epam.library.entity.book.catalog.BookCatalogFilterName;
import com.epam.library.entity.book.catalog.HallInstanceCatalog;
import com.epam.library.entity.book.catalog.InstanceCatalog;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class MYSQLBookCatalogDAO implements BookCatalogDAO {

    private static final ConnectionPool connectionPool = ConnectionPool.getInstance();
    private static final String ID = "id";
    private static final String NAME = "name";
    private static final String PUBLISHER = "publisher";
    private static final String CITY = "city";
    private static final String TYPE = "type";
    private static final String PUBLICATION_YEAR = "publication_year";
    private static final String PAGES = "pages";
    private static final String PART = "part";
    private static final String ISBN = "isbn";
    private static final String ISSN = "issn";
    private static final String ANNOTATION = "annotation";
    private static final String PRICE = "price";
    private static final String IMAGE = "image";
    private static final String RATING = "rating";
    private static final String NUMBER = "number";
    private static final String SHORT_NAME = "short_name";
    private static final String COUNT_RATINGS = "count_ratings";
    private static final String COUNT_COMMENTS = "count_comments";
    private static final String COUNT_FREE_INSTANCES = "count_free_instances";

    private static final String BOOK_FILTER_QUERY =
            "SELECT " +
            "DISTINCT books.id, books.name, publishers.name AS publisher, publishers.city, types.name AS type, publication_year, pages, part, isbn, issn, annotation, price, image, " +
            "(SELECT CAST(AVG(rating) AS DECIMAL(2,1)) FROM reviews WHERE books_id=books.id) AS rating, " +
            "(SELECT COUNT(*) FROM reviews WHERE books_id=books.id) AS count_ratings, " +
            "(SELECT COUNT(*) FROM reviews WHERE books_id=books.id AND (comment!='' AND comment IS NOT NULL)) AS count_comments " +
            "FROM books " +
            "INNER JOIN publishers ON books.publishers_id=publishers.id " +
            "INNER JOIN types ON books.types_id=types.id " +
            "INNER JOIN books_authors ON books.id = books_authors.books_id " +
            "INNER JOIN books_genres ON books.id = books_genres.books_id ";

    private static final String NEW_BOOKS =
            "SELECT " +
            "DISTINCT books.id, books.name, publishers.name AS publisher, publishers.city, types.name AS type, publication_year, pages, part, isbn, issn, annotation, price, image, " +
            "(SELECT CAST(AVG(rating) AS DECIMAL(2,1)) FROM reviews WHERE books_id=books.id) AS rating, " +
            "(SELECT COUNT(*) FROM reviews WHERE books_id=books.id) AS count_ratings, " +
            "(SELECT COUNT(*) FROM reviews WHERE books_id=books.id AND (comment!='' OR comment IS NOT NULL)) AS count_comments " +
            "FROM books " +
            "INNER JOIN publishers ON books.publishers_id=publishers.id " +
            "INNER JOIN types ON books.types_id=types.id " +
            "INNER JOIN books_authors ON books.id = books_authors.books_id " +
            "INNER JOIN books_genres ON books.id = books_genres.books_id " +
            "WHERE " +
            "books.publication_year = CASE WHEN MONTH(CURDATE())<=6 THEN YEAR(CURDATE())-1  ELSE YEAR(CURDATE()) END OR " +
            "books.publication_year = YEAR(CURDATE())";

    private static final String POPULAR_BOOKS = "SELECT " +
            "DISTINCT books.id, books.name, publishers.name AS publisher, publishers.city, types.name AS type, publication_year, pages, part, isbn, issn, annotation, price, image, " +
            "(SELECT CAST(AVG(rating) AS DECIMAL(2,1)) FROM reviews WHERE books_id=books.id) AS rating, " +
            "(SELECT COUNT(*) FROM reviews WHERE books_id=books.id) AS count_ratings, " +
            "(SELECT COUNT(*) FROM reviews WHERE books_id=books.id AND (comment!='' OR comment IS NOT NULL)) AS count_comments," +
            "(SELECT COUNT(*) FROM issuance INNER JOIN instances ON issuance.instances_id=instances.id WHERE instances.books_id=books.id) + " +
            "(SELECT COUNT(*) FROM reviews WHERE books_id=books.id) AS count_all " +
            "FROM books " +
            "INNER JOIN publishers ON books.publishers_id=publishers.id " +
            "INNER JOIN types ON books.types_id=types.id " +
            "INNER JOIN books_authors ON books.id = books_authors.books_id " +
            "INNER JOIN books_genres ON books.id = books_genres.books_id " +
            "ORDER BY count_all DESC, books.name " +
            "LIMIT 15";

    private static final String BOOK_AUTHORS =
            "SELECT TRIM(CONCAT(first_name, " +
                "CASE WHEN (TRIM(IFNULL(father_name, ''))<>'') THEN ' ' ELSE '' END, " +
                "father_name, " +
                "CASE WHEN (TRIM(IFNULL(last_name, ''))<>'') THEN ' ' ELSE '' END, " +
                "last_name)) AS name " +
            "FROM authors " +
            "INNER JOIN books_authors ON authors.id=books_authors.authors_id " +
            "WHERE books_id=?";
    private static final String BOOK_GENRES =
            "SELECT name " +
            "FROM genres " +
            "INNER JOIN books_genres ON genres.id=books_genres.genres_id " +
            "WHERE books_id=?";
    private static final String FREE_BOOK_INSTANCES_COUNT =
            "SELECT halls.name, halls.id, COUNT(*) AS count_free_instances " +
            "FROM instances " +
            "INNER JOIN halls ON instances.halls_id=halls.id " +
            "WHERE books_id=? AND date_write_off IS NULL AND " +
            "NOT EXISTS(SELECT * FROM issuance WHERE instances_id=instances.id AND (date_return IS NULL OR lost=1)) AND " +
            "NOT EXISTS(SELECT * FROM reservation WHERE instances_id=instances.id AND (status='RESERVED' OR status='READY')) " +
            "GROUP BY halls.name";
    private static final String FREE_BOOK_INSTANCES =
            "SELECT instances.id, instances.number, halls.short_name " +
            "FROM instances " +
            "INNER JOIN halls ON instances.halls_id=halls.id " +
            "WHERE books_id=? AND date_write_off IS NULL AND " +
            "NOT EXISTS(SELECT * FROM issuance WHERE instances_id=instances.id AND (date_return IS NULL OR lost=1)) AND " +
            "NOT EXISTS(SELECT * FROM reservation WHERE instances_id=instances.id AND (status='RESERVED' OR status='READY')) ";
    private static final String COUNT_INSTANCES = "count_instances";
    private static final String GET_INSTANCES = "SELECT COUNT(1) AS count_instances FROM instances WHERE books_id=?";

    private static final String WHERE = "WHERE ";
    private static final String AND = " AND ";
    private static final String OR = " OR ";
    private static final String BRACKET_LEFT = "(";
    private static final String BRACKET_RIGHT = ")";
    private static final String SPACE = " ";
    private static final String PERCENT = "%";
    private static final String POINT = ".";
    private static final String REGEX_END_ANNOTATION = "\\.|,|!|/?|:|-|;";
    private static final int LENGTH_ANNOTATION = 195;
    private static final String NAME_FILTER = "books.name LIKE ?";
    private static final String AUTHOR_FILTER = "books_authors.authors_id=?";
    private static final String GENRE_FILTER = "books_genres.genres_id=?";
    private static final String PUBLISHER_FILTER = "books.publishers_id=?";
    private static final String PUBLICATION_YEAR_FROM_FILTER = "books.publication_year>=?";
    private static final String PUBLICATION_YEAR_TO_FILTER = "books.publication_year<=?";
    private static final String PAGES_FROM_FILTER = "books.pages>=?";
    private static final String PAGES_TO_FILTER = "books.pages<=?";
    private static final String ISBN_FILTER = "books.isbn LIKE ?";
    private static final String ISSN_FILTER = "books.issn LIKE ?";
    private static final String TYPE_FILTER = "books.types_id=?";
    private static final String FREE_INSTANCES_FILTER =
            "(SELECT COUNT(*) FROM instances WHERE instances.books_id=books.id AND date_write_off IS NULL AND " +
            "NOT EXISTS (SELECT * FROM issuance WHERE issuance.instances_id = instances.id AND (date_return IS NULL OR lost=1)) AND " +
            "NOT EXISTS(SELECT * FROM reservation WHERE instances_id=instances.id AND (status='RESERVED' OR status='READY')))>0";
    private static final String SORT_FILTER = "ORDER BY books.name ";
    private static final String BOOK_INFO_BY_ID = "books.id=?";
    private static final Map<String, String> sortValue = Map.of(
            BookCatalogFilterName.NAME_ASCENDING, "ASC",
            BookCatalogFilterName.NAME_DESCENDING, "DESC"
    );

    public MYSQLBookCatalogDAO() {}

    @Override
    public BookCatalog getBookCatalog(int bookID) throws DAOException {
        BookCatalog bookInfo = new BookCatalog();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = connectionPool.getConnection();

            preparedStatement = connection.prepareStatement(BOOK_FILTER_QUERY + WHERE + BOOK_INFO_BY_ID);
            preparedStatement.setInt(1, bookID);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                bookInfo.setId(resultSet.getInt(ID));
                bookInfo.setName(resultSet.getString(NAME));
                bookInfo.setPublisher(resultSet.getString(PUBLISHER));
                bookInfo.setCity(resultSet.getString(CITY));
                bookInfo.setType(resultSet.getString(TYPE));
                bookInfo.setPublicationYear(resultSet.getInt(PUBLICATION_YEAR));
                bookInfo.setPages(resultSet.getInt(PAGES));
                bookInfo.setPart(resultSet.getInt(PART));
                bookInfo.setIsbn(resultSet.getString(ISBN));
                bookInfo.setIssn(resultSet.getString(ISSN));
                bookInfo.setAnnotation(resultSet.getString(ANNOTATION));
                bookInfo.setPrice(resultSet.getDouble(PRICE));
                bookInfo.setImageURL(resultSet.getString(IMAGE));
                bookInfo.setRating(resultSet.getDouble(RATING));
                bookInfo.setCountRatings(resultSet.getInt(COUNT_RATINGS));
                bookInfo.setCountComments(resultSet.getInt(COUNT_COMMENTS));

                preparedStatement = connection.prepareStatement(BOOK_AUTHORS);
                preparedStatement.setInt(1, bookInfo.getId());
                resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    bookInfo.getAuthors().add(resultSet.getString(NAME));
                }

                preparedStatement = connection.prepareStatement(BOOK_GENRES);
                preparedStatement.setInt(1, bookInfo.getId());
                resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    bookInfo.getGenres().add(resultSet.getString(NAME));
                }

                preparedStatement = connection.prepareStatement(FREE_BOOK_INSTANCES_COUNT);
                preparedStatement.setInt(1, bookInfo.getId());
                resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    HallInstanceCatalog hallInstanceCatalog = new HallInstanceCatalog();
                    hallInstanceCatalog.setId(resultSet.getInt(ID));
                    hallInstanceCatalog.setHallFreeInstances(resultSet.getString(NAME) + SPACE + BRACKET_LEFT + resultSet.getString(COUNT_FREE_INSTANCES) + BRACKET_RIGHT);
                    bookInfo.getHallFreeInstanceCatalogList().add(hallInstanceCatalog);
                }
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            connectionPool.releaseConnection(connection);
            connectionPool.closeConnection(resultSet, preparedStatement);
        }
        return bookInfo;
    }

    @Override
    public List<BookCatalog> getNewBookCatalogList() throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        List<BookCatalog> bookCatalog = new ArrayList<>();
        try {
            connection = connectionPool.getConnection();

            preparedStatement = connection.prepareStatement(NEW_BOOKS);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                BookCatalog bookInfo = new BookCatalog();
                bookInfo.setId(resultSet.getInt(ID));
                bookInfo.setName(resultSet.getString(NAME));
                bookInfo.setPublisher(resultSet.getString(PUBLISHER));
                bookInfo.setCity(resultSet.getString(CITY));
                bookInfo.setType(resultSet.getString(TYPE));
                bookInfo.setPublicationYear(resultSet.getInt(PUBLICATION_YEAR));
                bookInfo.setPages(resultSet.getInt(PAGES));
                bookInfo.setPart(resultSet.getInt(PART));
                bookInfo.setIsbn(resultSet.getString(ISBN));
                bookInfo.setIssn(resultSet.getString(ISSN));
                String annotation = resultSet.getString(ANNOTATION);
                if (annotation.length() < LENGTH_ANNOTATION) {
                    bookInfo.setAnnotation(annotation);
                } else {
                    annotation = annotation.substring(0, annotation.indexOf(SPACE, LENGTH_ANNOTATION));
                    if (annotation.substring(annotation.length() - 1).matches(REGEX_END_ANNOTATION)) {
                        annotation = annotation.substring(0, annotation.length() - 1);
                    }
                    annotation = annotation + POINT + POINT + POINT;
                }
                bookInfo.setAnnotation(annotation);
                bookInfo.setPrice(resultSet.getDouble(PRICE));
                bookInfo.setImageURL(resultSet.getString(IMAGE));
                bookInfo.setRating(resultSet.getDouble(RATING));
                bookInfo.setCountRatings(resultSet.getInt(COUNT_RATINGS));
                bookInfo.setCountComments(resultSet.getInt(COUNT_COMMENTS));

                ResultSet resultSetList;

                preparedStatement = connection.prepareStatement(BOOK_AUTHORS);
                preparedStatement.setInt(1, bookInfo.getId());
                resultSetList = preparedStatement.executeQuery();
                while (resultSetList.next()) {
                    bookInfo.getAuthors().add(resultSetList.getString(NAME));
                }

                preparedStatement = connection.prepareStatement(BOOK_GENRES);
                preparedStatement.setInt(1, bookInfo.getId());
                resultSetList = preparedStatement.executeQuery();
                while (resultSetList.next()) {
                    bookInfo.getGenres().add(resultSetList.getString(NAME));
                }

                preparedStatement = connection.prepareStatement(GET_INSTANCES);
                preparedStatement.setInt(1, bookInfo.getId());
                resultSetList = preparedStatement.executeQuery();
                if (resultSetList.next()) {
                    if (resultSetList.getInt(COUNT_INSTANCES) != 0) {
                        bookInfo.setBookIsUsed(true);
                    }
                }

                preparedStatement = connection.prepareStatement(FREE_BOOK_INSTANCES_COUNT);
                preparedStatement.setInt(1, bookInfo.getId());
                resultSetList = preparedStatement.executeQuery();
                while (resultSetList.next()) {
                    HallInstanceCatalog hallInstanceCatalog = new HallInstanceCatalog();
                    hallInstanceCatalog.setId(resultSetList.getInt(ID));
                    hallInstanceCatalog.setHallFreeInstances(resultSetList.getString(NAME) + SPACE + BRACKET_LEFT + resultSetList.getString(COUNT_FREE_INSTANCES) + BRACKET_RIGHT);
                    bookInfo.getHallFreeInstanceCatalogList().add(hallInstanceCatalog);
                }

                preparedStatement = connection.prepareStatement(FREE_BOOK_INSTANCES);
                preparedStatement.setInt(1, bookInfo.getId());
                resultSetList = preparedStatement.executeQuery();
                while (resultSetList.next()) {
                    InstanceCatalog instanceCatalog = new InstanceCatalog();
                    instanceCatalog.setId(resultSetList.getInt(ID));
                    instanceCatalog.setNumber(resultSetList.getString(NUMBER));
                    instanceCatalog.setHall(resultSetList.getString(SHORT_NAME));
                    bookInfo.getFreeInstanceCatalogList().add(instanceCatalog);
                }

                resultSetList.close();
                bookCatalog.add(bookInfo);
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            connectionPool.releaseConnection(connection);
            connectionPool.closeConnection(resultSet, preparedStatement);
        }
        return bookCatalog;
    }

    @Override
    public List<BookCatalog> getPopularBookCatalogList() throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        List<BookCatalog> bookCatalog = new ArrayList<>();
        try {
            connection = connectionPool.getConnection();

            preparedStatement = connection.prepareStatement(POPULAR_BOOKS);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                BookCatalog bookInfo = new BookCatalog();
                bookInfo.setId(resultSet.getInt(ID));
                bookInfo.setName(resultSet.getString(NAME));
                bookInfo.setPublisher(resultSet.getString(PUBLISHER));
                bookInfo.setCity(resultSet.getString(CITY));
                bookInfo.setType(resultSet.getString(TYPE));
                bookInfo.setPublicationYear(resultSet.getInt(PUBLICATION_YEAR));
                bookInfo.setPages(resultSet.getInt(PAGES));
                bookInfo.setPart(resultSet.getInt(PART));
                bookInfo.setIsbn(resultSet.getString(ISBN));
                bookInfo.setIssn(resultSet.getString(ISSN));
                String annotation = resultSet.getString(ANNOTATION);
                if (annotation.length() < LENGTH_ANNOTATION) {
                    bookInfo.setAnnotation(annotation);
                } else {
                    annotation = annotation.substring(0, annotation.indexOf(SPACE, LENGTH_ANNOTATION));
                    if (annotation.substring(annotation.length() - 1).matches(REGEX_END_ANNOTATION)) {
                        annotation = annotation.substring(0, annotation.length() - 1);
                    }
                    annotation = annotation + POINT + POINT + POINT;
                }
                bookInfo.setAnnotation(annotation);
                bookInfo.setPrice(resultSet.getDouble(PRICE));
                bookInfo.setImageURL(resultSet.getString(IMAGE));
                bookInfo.setRating(resultSet.getDouble(RATING));
                bookInfo.setCountRatings(resultSet.getInt(COUNT_RATINGS));
                bookInfo.setCountComments(resultSet.getInt(COUNT_COMMENTS));

                ResultSet resultSetList;

                preparedStatement = connection.prepareStatement(BOOK_AUTHORS);
                preparedStatement.setInt(1, bookInfo.getId());
                resultSetList = preparedStatement.executeQuery();
                while (resultSetList.next()) {
                    bookInfo.getAuthors().add(resultSetList.getString(NAME));
                }

                preparedStatement = connection.prepareStatement(BOOK_GENRES);
                preparedStatement.setInt(1, bookInfo.getId());
                resultSetList = preparedStatement.executeQuery();
                while (resultSetList.next()) {
                    bookInfo.getGenres().add(resultSetList.getString(NAME));
                }

                preparedStatement = connection.prepareStatement(GET_INSTANCES);
                preparedStatement.setInt(1, bookInfo.getId());
                resultSetList = preparedStatement.executeQuery();
                if (resultSetList.next()) {
                    if (resultSetList.getInt(COUNT_INSTANCES) != 0) {
                        bookInfo.setBookIsUsed(true);
                    }
                }

                preparedStatement = connection.prepareStatement(FREE_BOOK_INSTANCES_COUNT);
                preparedStatement.setInt(1, bookInfo.getId());
                resultSetList = preparedStatement.executeQuery();
                while (resultSetList.next()) {
                    HallInstanceCatalog hallInstanceCatalog = new HallInstanceCatalog();
                    hallInstanceCatalog.setId(resultSetList.getInt(ID));
                    hallInstanceCatalog.setHallFreeInstances(resultSetList.getString(NAME) + SPACE + BRACKET_LEFT + resultSetList.getString(COUNT_FREE_INSTANCES) + BRACKET_RIGHT);
                    bookInfo.getHallFreeInstanceCatalogList().add(hallInstanceCatalog);
                }

                preparedStatement = connection.prepareStatement(FREE_BOOK_INSTANCES);
                preparedStatement.setInt(1, bookInfo.getId());
                resultSetList = preparedStatement.executeQuery();
                while (resultSetList.next()) {
                    InstanceCatalog instanceCatalog = new InstanceCatalog();
                    instanceCatalog.setId(resultSetList.getInt(ID));
                    instanceCatalog.setNumber(resultSetList.getString(NUMBER));
                    instanceCatalog.setHall(resultSetList.getString(SHORT_NAME));
                    bookInfo.getFreeInstanceCatalogList().add(instanceCatalog);
                }

                resultSetList.close();
                bookCatalog.add(bookInfo);
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            connectionPool.releaseConnection(connection);
            connectionPool.closeConnection(resultSet, preparedStatement);
        }
        return bookCatalog;
    }

    @Override
    public List<BookCatalog> getBookCatalogByFilter(Map<String, Object> filters) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        List<BookCatalog> bookCatalog = new ArrayList<>();
        try {
            connection = connectionPool.getConnection();

            StringBuilder query = new StringBuilder(BOOK_FILTER_QUERY);
            if (filters.size() > 1) {
                query.append(WHERE);
                for (String filterName : filters.keySet()) {
                    if (filterName.equals(BookCatalogFilterName.AUTHORS) || filterName.equals(BookCatalogFilterName.GENRES)) {
                        query.append(BRACKET_LEFT);
                        for (int i = 0; i < ((String[]) filters.get(filterName)).length - 1; i++) {
                            buildQueryByFilter(query, filterName);
                        }
                        if (filterName.equals(BookCatalogFilterName.AUTHORS)) {
                            query.append(AUTHOR_FILTER);
                        } else {
                            query.append(GENRE_FILTER);
                        }
                        query.append(BRACKET_RIGHT);
                        query.append(AND);
                    } else if (!filterName.equals(BookCatalogFilterName.SORT)) {
                        buildQueryByFilter(query, filterName);
                    }
                }
                query.setLength(query.length() - 4);
            }
            query.append(SORT_FILTER);
            query.append(sortValue.get(filters.get(BookCatalogFilterName.SORT)));

            preparedStatement = connection.prepareStatement(query.toString());
            List<String> filterNames = new ArrayList<>(filters.keySet());
            for (int i = 0, n = 1; i < filterNames.size() - 1; i++, n++) {
                switch (filterNames.get(i)) {
                    case BookCatalogFilterName.NAME:
                    case BookCatalogFilterName.ISBN:
                    case BookCatalogFilterName.ISSN:
                        preparedStatement.setString(n, PERCENT + filters.get(filterNames.get(i)).toString() + PERCENT);
                        break;

                    case BookCatalogFilterName.PUBLISHER:
                    case BookCatalogFilterName.PUBLICATION_YEAR_FROM:
                    case BookCatalogFilterName.PUBLICATION_YEAR_TO:
                    case BookCatalogFilterName.PAGES_FROM:
                    case BookCatalogFilterName.PAGES_TO:
                    case BookCatalogFilterName.TYPE:
                        preparedStatement.setInt(n, Integer.parseInt(filters.get(filterNames.get(i)).toString()));
                        break;

                    case BookCatalogFilterName.AUTHORS:
                    case BookCatalogFilterName.GENRES:
                        String[] values = (String[]) filters.get(filterNames.get(i));
                        for (int j = 0; j < values.length; j++, n++) {
                            preparedStatement.setInt(n, Integer.parseInt(values[j]));
                        }
                        break;
                }
            }

            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                BookCatalog bookInfo = new BookCatalog();
                bookInfo.setId(resultSet.getInt(ID));
                bookInfo.setName(resultSet.getString(NAME));
                bookInfo.setPublisher(resultSet.getString(PUBLISHER));
                bookInfo.setCity(resultSet.getString(CITY));
                bookInfo.setType(resultSet.getString(TYPE));
                bookInfo.setPublicationYear(resultSet.getInt(PUBLICATION_YEAR));
                bookInfo.setPages(resultSet.getInt(PAGES));
                bookInfo.setPart(resultSet.getInt(PART));
                bookInfo.setIsbn(resultSet.getString(ISBN));
                bookInfo.setIssn(resultSet.getString(ISSN));
                String annotation = resultSet.getString(ANNOTATION);
                if (annotation.length() < LENGTH_ANNOTATION) {
                    bookInfo.setAnnotation(annotation);
                } else {
                    annotation = annotation.substring(0, annotation.indexOf(SPACE, LENGTH_ANNOTATION));
                    if (annotation.substring(annotation.length() - 1).matches(REGEX_END_ANNOTATION)) {
                        annotation = annotation.substring(0, annotation.length() - 1);
                    }
                    annotation = annotation + POINT + POINT + POINT;
                }
                bookInfo.setAnnotation(annotation);
                bookInfo.setPrice(resultSet.getDouble(PRICE));
                bookInfo.setImageURL(resultSet.getString(IMAGE));
                bookInfo.setRating(resultSet.getDouble(RATING));
                bookInfo.setCountRatings(resultSet.getInt(COUNT_RATINGS));
                bookInfo.setCountComments(resultSet.getInt(COUNT_COMMENTS));

                ResultSet resultSetList;

                preparedStatement = connection.prepareStatement(BOOK_AUTHORS);
                preparedStatement.setInt(1, bookInfo.getId());
                resultSetList = preparedStatement.executeQuery();
                while (resultSetList.next()) {
                    bookInfo.getAuthors().add(resultSetList.getString(NAME));
                }

                preparedStatement = connection.prepareStatement(BOOK_GENRES);
                preparedStatement.setInt(1, bookInfo.getId());
                resultSetList = preparedStatement.executeQuery();
                while (resultSetList.next()) {
                    bookInfo.getGenres().add(resultSetList.getString(NAME));
                }

                preparedStatement = connection.prepareStatement(GET_INSTANCES);
                preparedStatement.setInt(1, bookInfo.getId());
                resultSetList = preparedStatement.executeQuery();
                if (resultSetList.next()) {
                    if (resultSetList.getInt(COUNT_INSTANCES) != 0) {
                        bookInfo.setBookIsUsed(true);
                    }
                }

                preparedStatement = connection.prepareStatement(FREE_BOOK_INSTANCES_COUNT);
                preparedStatement.setInt(1, bookInfo.getId());
                resultSetList = preparedStatement.executeQuery();
                while (resultSetList.next()) {
                    HallInstanceCatalog hallInstanceCatalog = new HallInstanceCatalog();
                    hallInstanceCatalog.setId(resultSetList.getInt(ID));
                    hallInstanceCatalog.setHallFreeInstances(resultSetList.getString(NAME) + SPACE + BRACKET_LEFT + resultSetList.getString(COUNT_FREE_INSTANCES) + BRACKET_RIGHT);
                    bookInfo.getHallFreeInstanceCatalogList().add(hallInstanceCatalog);
                }

                preparedStatement = connection.prepareStatement(FREE_BOOK_INSTANCES);
                preparedStatement.setInt(1, bookInfo.getId());
                resultSetList = preparedStatement.executeQuery();
                while (resultSetList.next()) {
                    InstanceCatalog instanceCatalog = new InstanceCatalog();
                    instanceCatalog.setId(resultSetList.getInt(ID));
                    instanceCatalog.setNumber(resultSetList.getString(NUMBER));
                    instanceCatalog.setHall(resultSetList.getString(SHORT_NAME));
                    bookInfo.getFreeInstanceCatalogList().add(instanceCatalog);
                }

                resultSetList.close();
                bookCatalog.add(bookInfo);
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            connectionPool.releaseConnection(connection);
            connectionPool.closeConnection(resultSet, preparedStatement);
        }
        return bookCatalog;
    }

    private void buildQueryByFilter(StringBuilder query, String filterName) {
        switch (filterName) {
            case BookCatalogFilterName.NAME:
                query.append(NAME_FILTER);
                query.append(AND);
                break;

            case BookCatalogFilterName.AUTHORS:
                query.append(AUTHOR_FILTER);
                query.append(OR);
                break;

            case BookCatalogFilterName.GENRES:
                query.append(GENRE_FILTER);
                query.append(OR);
                break;

            case BookCatalogFilterName.PUBLISHER:
                query.append(PUBLISHER_FILTER);
                query.append(AND);
                break;

            case BookCatalogFilterName.PUBLICATION_YEAR_FROM:
                query.append(PUBLICATION_YEAR_FROM_FILTER);
                query.append(AND);
                break;

            case BookCatalogFilterName.PUBLICATION_YEAR_TO:
                query.append(PUBLICATION_YEAR_TO_FILTER);
                query.append(AND);
                break;

            case BookCatalogFilterName.PAGES_FROM:
                query.append(PAGES_FROM_FILTER);
                query.append(AND);
                break;

            case BookCatalogFilterName.PAGES_TO:
                query.append(PAGES_TO_FILTER);
                query.append(AND);
                break;

            case BookCatalogFilterName.ISBN:
                query.append(ISBN_FILTER);
                query.append(AND);
                break;

            case BookCatalogFilterName.ISSN:
                query.append(ISSN_FILTER);
                query.append(AND);
                break;

            case BookCatalogFilterName.TYPE:
                query.append(TYPE_FILTER);
                query.append(AND);
                break;

            case BookCatalogFilterName.FREE_INSTANCES:
                query.append(FREE_INSTANCES_FILTER);
                query.append(AND);
                break;
        }
    }
}
