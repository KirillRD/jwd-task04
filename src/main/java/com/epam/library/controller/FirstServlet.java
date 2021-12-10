package com.epam.library.controller;

import com.epam.library.entity.user.RegistrationInfo;
import com.epam.library.entity.User;
import com.epam.library.entity.user.Gender;
import com.epam.library.service.ServiceProvider;
import com.epam.library.service.UserService;
import com.epam.library.service.exception.ServiceException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Date;

//@WebServlet(urlPatterns = {"/index.jsp"})
@WebServlet("/addUser")
public class FirstServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ServiceProvider serviceProvider = ServiceProvider.getInstance();
        UserService userService = serviceProvider.getUserService();

        RegistrationInfo registrationInfo = new RegistrationInfo();

        registrationInfo.setLogin(req.getParameter("login"));
        registrationInfo.setPassword(req.getParameter("password"));

        registrationInfo.setEmail(req.getParameter("email"));
        registrationInfo.setLastName(req.getParameter("lastName"));
        registrationInfo.setFirstName(req.getParameter("firstName"));
        registrationInfo.setFatherName(req.getParameter("fatherName"));

//        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy");
//        try {
//            registrationInfo.setBirthdate(simpleDateFormat.parse(req.getParameter("birthdate")));
//        } catch (ParseException e) {
//            throw new ServletException();
//        }
        registrationInfo.setBirthdate(Date.valueOf(req.getParameter("birthdate")));
        registrationInfo.setGender(Gender.valueOf(req.getParameter("gender")));

        registrationInfo.setPassport(req.getParameter("passport"));
        registrationInfo.setAddress(req.getParameter("address"));
        registrationInfo.setPhone(req.getParameter("phone"));
        registrationInfo.setRegistrationDate(new Date(System.currentTimeMillis()));

        try {
            User user = userService.registration(registrationInfo, req.getParameter("repeatedPassword"));
        } catch (ServiceException e) {
            throw new ServletException(e);
        }
    }
}
