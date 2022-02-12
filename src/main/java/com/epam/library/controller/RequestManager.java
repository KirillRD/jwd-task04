package com.epam.library.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

/**
 * Requests manager
 */
public final class RequestManager {

    private RequestManager() {}

    /**
     * Forward request to resource
     * @param resource Resource address
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public static void forward(String resource, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher(resource).forward(request, response);
    }

    /**
     * Redirect request to resource
     * @param resource Resource address
     * @param request
     * @param response
     * @throws IOException
     */
    public static void redirect(String resource, HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.sendRedirect(request.getRequestURI() + resource);
    }
}
