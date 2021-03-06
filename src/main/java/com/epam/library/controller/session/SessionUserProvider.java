package com.epam.library.controller.session;

import com.epam.library.entity.user.SessionUser;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.log4j.Logger;

/**
 * Class for managing data about user in session
 */
public final class SessionUserProvider {
    private static final Logger logger = Logger.getLogger(SessionUserProvider.class.getName());

    private SessionUserProvider() {}

    private static final String PUT_SESSION_USER = "Put data about user in session";
    private static final String REMOVE_SESSION_USER = "Remove data about user from session";

    private static final String SESSION_USER = "session_user";
    private static final String VERTICAL_BAR = "|";

    public static void setSessionUser(HttpServletRequest request, SessionUser sessionUser) {
        HttpSession session = request.getSession();
        session.setAttribute(SESSION_USER, sessionUser);
        logger.info(PUT_SESSION_USER + VERTICAL_BAR + sessionUser.toString());
    }

    public static SessionUser getSessionUser(HttpServletRequest request) {
        HttpSession session = request.getSession();
        return (SessionUser) session.getAttribute(SESSION_USER);
    }

    public static void removeSessionUser(HttpServletRequest request) {
        HttpSession session = request.getSession();
        SessionUser sessionUser = (SessionUser) session.getAttribute(SESSION_USER);
        session.removeAttribute(SESSION_USER);
        logger.info(REMOVE_SESSION_USER + VERTICAL_BAR + sessionUser.toString());
    }
}
