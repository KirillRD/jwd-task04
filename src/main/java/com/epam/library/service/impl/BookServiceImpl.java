package com.epam.library.service.impl;

import com.epam.library.dao.BookDAO;
import com.epam.library.dao.DAOProvider;
import com.epam.library.dao.exception.DAOException;
import com.epam.library.entity.Book;
import com.epam.library.entity.book.BookInfo;
import com.epam.library.service.BookService;
import com.epam.library.service.exception.BookException;
import com.epam.library.service.exception.ServiceException;
import com.epam.library.service.exception.book.*;
import com.epam.library.service.exception.book.empty.*;
import com.epam.library.service.validation.Validator;

import java.util.ArrayList;
import java.util.List;

public class BookServiceImpl implements BookService {
    private static final BookDAO bookDAO = DAOProvider.getInstance().getBookDAO();
    private static final Validator validator = Validator.getInstance();

    private static final int BOOK_NAME_LENGTH = 100;
    private static final int ANNOTATION_LENGTH = 1500;

    public BookServiceImpl() {}

    @Override
    public void addBook(BookInfo book) throws ServiceException {

        List<BookException> exceptions = new ArrayList<>();
        try {
            if (validator.isEmpty(book.getName())) {
                exceptions.add(new EmptyBookNameException());
            } else if (book.getName().length() > BOOK_NAME_LENGTH) {
                exceptions.add(new InvalidLengthBookNameException());
            }

            if (book.getAuthorsID().isEmpty()) {
                exceptions.add(new EmptyAuthorException());
            } else {
                for (String author : book.getAuthorsID()) {
                    if (!validator.isInteger(author)) {
                        exceptions.add(new InvalidAuthorFormatException());
                        break;
                    }
                }
            }

            if (validator.isEmpty(book.getPublisherID())) {
                exceptions.add(new EmptyPublisherException());
            } else if (!validator.isInteger(book.getPublisherID())) {
                exceptions.add(new InvalidPublisherFormatException());
            }

            if (validator.isEmpty(book.getPublicationYear())) {
                exceptions.add(new EmptyPublicationYearException());
            } else if (!validator.isYear(book.getPublicationYear())) {
                exceptions.add(new InvalidPublicationYearFormatException());
            }

            if (!validator.isEmpty(book.getPart()) && !(book.getPart().length() == 1 && book.getPart().charAt(0) == '0') && !validator.isInteger(book.getPart())) {
                exceptions.add(new InvalidPartFormatException());
            }

            if (validator.isEmpty(book.getPages())) {
                exceptions.add(new EmptyPagesException());
            } else if (!validator.isInteger(book.getPages())) {
                exceptions.add(new InvalidPagesFormatException());
            }

            if (book.getGenresID().isEmpty()) {
                exceptions.add(new EmptyGenreException());
            } else {
                for (String genre : book.getGenresID()) {
                    if (!validator.isInteger(genre)) {
                        exceptions.add(new InvalidGenreFormatException());
                        break;
                    }
                }
            }

            if (validator.isEmpty(book.getTypeID())) {
                exceptions.add(new EmptyTypeException());
            } else if (!validator.isInteger(book.getTypeID())) {
                exceptions.add(new InvalidTypeFormatException());
            }

            if (validator.isEmpty(book.getPrice())) {
                exceptions.add(new EmptyPriceException());
            } else if (!validator.isRationalNumber(book.getPrice())) {
                exceptions.add(new InvalidPriceFormatException());
            }

            if (validator.isEmpty(book.getIsbn()) && validator.isEmpty(book.getIssn())) {
                exceptions.add(new EmptyStandardNumberException());
            } else {
                if (!validator.isEmpty(book.getIsbn()) && !validator.isISBN(book.getIsbn())) {
                    exceptions.add(new InvalidISBNFormatException());
                }

                if (!validator.isEmpty(book.getIssn()) && !validator.isISSN(book.getIssn())) {
                    exceptions.add(new InvalidISSNFormatException());
                }

                if (validator.isISBN(book.getIsbn()) || validator.isISSN(book.getIssn())) {
                    if (!bookDAO.checkStandardNumber(book.getId(), book.getIsbn(), book.getIssn())) {
                        exceptions.add(new ExistStandardNumberException());
                    }
                }
            }

            if (!validator.isEmpty(book.getAnnotation()) && book.getAnnotation().length() > ANNOTATION_LENGTH) {
                exceptions.add(new InvalidLengthAnnotationException());
            }

            if (exceptions.isEmpty()) {
                book.setPart(validator.isEmpty(book.getPart()) ? "0" : book.getPart());
                bookDAO.addBook(book);
            } else {
                throw new BookException(exceptions);
            }

        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Book getBook(int bookID) throws ServiceException {
        Book book;
        try {
            book = bookDAO.getBook(bookID);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        return book;
    }

    @Override
    public void updateBook(BookInfo book) throws ServiceException {

        List<BookException> exceptions = new ArrayList<>();
        try {
            if (validator.isEmpty(book.getName())) {
                exceptions.add(new EmptyBookNameException());
            } else if (book.getName().length() > BOOK_NAME_LENGTH) {
                exceptions.add(new InvalidLengthBookNameException());
            }

            if (book.getAuthorsID().isEmpty()) {
                exceptions.add(new EmptyAuthorException());
            } else {
                for (String author : book.getAuthorsID()) {
                    if (!validator.isInteger(author)) {
                        exceptions.add(new InvalidAuthorFormatException());
                        break;
                    }
                }
            }

            if (validator.isEmpty(book.getPublisherID())) {
                exceptions.add(new EmptyPublisherException());
            } else if (!validator.isInteger(book.getPublisherID())) {
                exceptions.add(new InvalidPublisherFormatException());
            }

            if (validator.isEmpty(book.getPublicationYear())) {
                exceptions.add(new EmptyPublicationYearException());
            } else if (!validator.isYear(book.getPublicationYear())) {
                exceptions.add(new InvalidPublicationYearFormatException());
            }

            if (!validator.isEmpty(book.getPart()) && !(book.getPart().length() == 1 && book.getPart().charAt(0) == '0') && !validator.isInteger(book.getPart())) {
                exceptions.add(new InvalidPartFormatException());
            }

            if (validator.isEmpty(book.getPages())) {
                exceptions.add(new EmptyPagesException());
            } else if (!validator.isInteger(book.getPages())) {
                exceptions.add(new InvalidPagesFormatException());
            }

            if (book.getGenresID().isEmpty()) {
                exceptions.add(new EmptyGenreException());
            } else {
                for (String genre : book.getGenresID()) {
                    if (!validator.isInteger(genre)) {
                        exceptions.add(new InvalidGenreFormatException());
                        break;
                    }
                }
            }

            if (validator.isEmpty(book.getTypeID())) {
                exceptions.add(new EmptyTypeException());
            } else if (!validator.isInteger(book.getTypeID())) {
                exceptions.add(new InvalidTypeFormatException());
            }

            if (validator.isEmpty(book.getPrice())) {
                exceptions.add(new EmptyPriceException());
            } else if (!validator.isRationalNumber(book.getPrice())) {
                exceptions.add(new InvalidPriceFormatException());
            }

            if (validator.isEmpty(book.getIsbn()) && validator.isEmpty(book.getIssn())) {
                exceptions.add(new EmptyStandardNumberException());
            } else {
                if (!validator.isEmpty(book.getIsbn()) && !validator.isISBN(book.getIsbn())) {
                    exceptions.add(new InvalidISBNFormatException());
                }

                if (!validator.isEmpty(book.getIssn()) && !validator.isISSN(book.getIssn())) {
                    exceptions.add(new InvalidISSNFormatException());
                }

                if (validator.isISBN(book.getIsbn()) || validator.isISSN(book.getIssn())) {
                    if (!bookDAO.checkStandardNumber(book.getId(), book.getIsbn(), book.getIssn())) {
                        exceptions.add(new ExistStandardNumberException());
                    }
                }
            }

            if (!validator.isEmpty(book.getAnnotation()) && book.getAnnotation().length() > ANNOTATION_LENGTH) {
                exceptions.add(new InvalidLengthAnnotationException());
            }

            if (exceptions.isEmpty()) {
                book.setPart(validator.isEmpty(book.getPart()) ? "0" : book.getPart());
                bookDAO.updateBook(book);
            } else {
                throw new BookException(exceptions);
            }

        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void updateBookImage(int bookID, String imageURL) throws ServiceException {
        try {
            bookDAO.updateBookImage(bookID, imageURL);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public boolean deleteBook(int bookID) throws ServiceException {
        try {
            return bookDAO.deleteBook(bookID);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }
}
