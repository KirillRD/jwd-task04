package com.epam.library.controller;

import com.epam.library.controller.constant.ErrorMessage;
import com.epam.library.controller.constant.RedirectCommand;
import com.epam.library.controller.util.LogMessageBuilder;
import com.epam.library.controller.util.Util;
import com.epam.library.entity.Book;
import com.epam.library.service.BookService;
import com.epam.library.service.ServiceProvider;
import com.epam.library.service.exception.ServiceException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.*;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

@MultipartConfig(fileSizeThreshold=1024*1024*2,
                 maxFileSize=1024*1024*10,
                 maxRequestSize=1024*1024*50)
public class BookImageUpload extends HttpServlet {
    private static final Logger logger = Logger.getLogger(BookImageUpload.class.getName());

    private static final String URL = "url";
    private static final String REGEX = "regex";
    private static final String IMAGE_PATTERN = "image-pattern";
    private static final String IMAGE = "image";
    private static final String BOOKS_IMAGES_DIRECTORY_NON_DEPLOYED_PROJECT = "E:\\Users\\Kirill\\Programs\\EPAM\\jwd-task04\\src\\main\\webapp\\images\\books\\";
    private static final String BOOKS_IMAGES_DIRECTORY_DEPLOYED_PROJECT = "D:\\Program Files\\Program\\Tomcat 10.0\\webapps\\jwd_task04\\images\\books\\";
    private static final String POINT = ".";
    private static final String DEFAULT_IMAGE_BOOK = "default_image_book.jpg";
    private static final String BOOK_ID = "book_id";
    private static final String MESSAGES = "messages";
    private static final String INVALID_IMAGE_FORMAT = "error-image-format";

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String logMessage = LogMessageBuilder.build(request);
        logger.info(LogMessageBuilder.message(logMessage, "Book image upload started"));

        Part part = request.getPart(IMAGE);
        if (part == null) {
            logger.error(LogMessageBuilder.message(logMessage, "Book image is null. Book image was not uploaded"));
            response.sendRedirect((String) request.getSession().getAttribute(URL));
            return;
        }
        String fileName = part.getSubmittedFileName();
        if (fileName.isEmpty()) {
            logger.error(LogMessageBuilder.message(logMessage, "Book image is null. Book image was not uploaded"));
            response.sendRedirect((String) request.getSession().getAttribute(URL));
            return;
        }
        ResourceBundle resourceBundle = ResourceBundle.getBundle(REGEX);
        if (!Pattern.compile(resourceBundle.getString(IMAGE_PATTERN)).matcher(fileName).matches()) {
            HttpSession session = request.getSession();
            session.setAttribute(MESSAGES, INVALID_IMAGE_FORMAT);
            logger.error(LogMessageBuilder.message(logMessage, "The selected book image is incorrect. Book image was not uploaded"));
            response.sendRedirect((String) request.getSession().getAttribute(URL));
            return;
        }

        int bookID;
        if (Util.isID(request.getParameter(BOOK_ID))) {
            bookID = Integer.parseInt(request.getParameter(BOOK_ID));
        } else {
            logger.error(LogMessageBuilder.message(logMessage, "Invalid page attributes. Book image was not uploaded"));
            RequestProvider.redirect(String.format(RedirectCommand.ERROR_PAGE, ErrorMessage.PAGE_NOT_FOUND), request, response);
            return;
        }
        String imageURL = bookID + fileName.substring(fileName.lastIndexOf(POINT));
        BookService bookService = ServiceProvider.getInstance().getBookService();
        try {
            Book book = bookService.getBook(bookID);
            String bookImage = book.getImageURL();
            if (!DEFAULT_IMAGE_BOOK.equals(bookImage) && !bookImage.equals(imageURL)) {
                Files.deleteIfExists(Paths.get(BOOKS_IMAGES_DIRECTORY_NON_DEPLOYED_PROJECT + bookImage));
                Files.deleteIfExists(Paths.get(BOOKS_IMAGES_DIRECTORY_DEPLOYED_PROJECT + bookImage));
                bookService.updateBookImage(bookID, imageURL);
            } else if (DEFAULT_IMAGE_BOOK.equals(bookImage)) {
                bookService.updateBookImage(bookID, imageURL);
            }

            part.write(BOOKS_IMAGES_DIRECTORY_NON_DEPLOYED_PROJECT + imageURL);
            part.write(BOOKS_IMAGES_DIRECTORY_DEPLOYED_PROJECT + imageURL);

            logger.info(LogMessageBuilder.message(logMessage, "Book image upload completed"));

            response.sendRedirect((String) request.getSession().getAttribute(URL));
        } catch (ServiceException e) {
            logger.error(LogMessageBuilder.message(logMessage, "Error uploading book image data"), e);
            RequestProvider.redirect(String.format(RedirectCommand.ERROR_PAGE, ErrorMessage.GENERAL_ERROR), request, response);
        }
    }
}
