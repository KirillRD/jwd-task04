package com.epam.library.controller.session;

import com.epam.library.entity.user.SessionUser;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.log4j.Logger;

public final class SessionUserProvider {

    private SessionUserProvider() {}

    private static final Logger logger = Logger.getLogger(SessionUserProvider.class.getName());
    private static final String PUT_SESSION_USER = "Put session user in session";
    private static final String RETURN_SESSION_USER = "Return session user from session";
    private static final String REMOVE_SESSION_USER = "Remove session user from session";

    private static final String SESSION_USER = "session_user";

    public static void setSessionUser(HttpServletRequest request, SessionUser sessionUser) {
        HttpSession session = request.getSession();
        session.setAttribute(SESSION_USER, sessionUser);
        logger.info(PUT_SESSION_USER);
    }

    public static SessionUser getSessionUser(HttpServletRequest request) {
        HttpSession session = request.getSession();
//        logger.info(RETURN_SESSION_USER);
        return (SessionUser) session.getAttribute(SESSION_USER);
    }

    public static void removeSessionUser(HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.removeAttribute(SESSION_USER);
        logger.info(REMOVE_SESSION_USER);
    }
}
