package com.epam.library.controller;

import com.epam.library.controller.constant.ErrorMessage;
import com.epam.library.controller.constant.RedirectCommand;
import com.epam.library.controller.session.SessionUserProvider;
import com.epam.library.controller.util.LogMessageBuilder;
import com.epam.library.service.ServiceProvider;
import com.epam.library.service.UserService;
import com.epam.library.service.exception.ServiceException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.*;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.Serial;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

/**
 * Uploading user image
 */
@MultipartConfig(fileSizeThreshold=1024*1024*2,
                 maxFileSize=1024*1024*10,
                 maxRequestSize=1024*1024*50)
public class UserImageUpload extends HttpServlet {
    @Serial
    private static final long serialVersionUID = 3580196621104043128L;

    private static final Logger logger = Logger.getLogger(UserImageUpload.class.getName());

    private static final String URL = "url";
    private static final String REGEX = "regex";
    private static final String IMAGE_PATTERN = "image-pattern";
    private static final String IMAGE = "image";
    private static final String USERS_IMAGES_DIRECTORY_NON_DEPLOYED_PROJECT = "E:\\Users\\Kirill\\Programs\\EPAM\\jwd-task04\\src\\main\\webapp\\images\\users\\";
    private static final String USERS_IMAGES_DIRECTORY_DEPLOYED_PROJECT = "D:\\Program Files\\Program\\Tomcat 10.0\\webapps\\jwd_task04\\images\\users\\";
    private static final String POINT = ".";
    private static final String MESSAGES = "messages";
    private static final String INVALID_IMAGE_FORMAT = "error-image-format";

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String logMessage = LogMessageBuilder.build(request);
        logger.info(LogMessageBuilder.message(logMessage, "User image upload started"));

        Part part = request.getPart(IMAGE);
        if (part == null) {
            logger.error(LogMessageBuilder.message(logMessage, "User image is null. User image was not uploaded"));
            response.sendRedirect((String) request.getSession().getAttribute(URL));
            return;
        }
        String fileName = part.getSubmittedFileName();
        if (fileName.isEmpty()) {
            logger.error(LogMessageBuilder.message(logMessage, "User image is null. User image was not uploaded"));
            response.sendRedirect((String) request.getSession().getAttribute(URL));
            return;
        }
        ResourceBundle resourceBundle = ResourceBundle.getBundle(REGEX);
        if (!Pattern.compile(resourceBundle.getString(IMAGE_PATTERN)).matcher(fileName).matches()) {
            HttpSession session = request.getSession();
            session.setAttribute(MESSAGES, INVALID_IMAGE_FORMAT);
            logger.error(LogMessageBuilder.message(logMessage, "The selected user image is incorrect. User image was not uploaded"));
            response.sendRedirect((String) request.getSession().getAttribute(URL));
            return;
        }

        int userID = SessionUserProvider.getSessionUser(request).getId();
        String imageURL = userID + fileName.substring(fileName.lastIndexOf(POINT));
        UserService userService = ServiceProvider.getInstance().getUserService();
        try {
            part.write(USERS_IMAGES_DIRECTORY_NON_DEPLOYED_PROJECT + imageURL);
            part.write(USERS_IMAGES_DIRECTORY_DEPLOYED_PROJECT + imageURL);

            userService.updateUserImage(userID, imageURL);

            logger.info(LogMessageBuilder.message(logMessage, "User image upload completed"));

            response.sendRedirect((String) request.getSession().getAttribute(URL));
        } catch (ServiceException e) {
            logger.error(LogMessageBuilder.message(logMessage, "Error uploading user image data"), e);
            RequestManager.redirect(String.format(RedirectCommand.ERROR_PAGE, ErrorMessage.GENERAL_ERROR), request, response);
        }
    }
}
