<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="locale"/>
<!DOCTYPE html>
<html>
    <head>
        <title><fmt:message key="title"/></title>
        <jsp:include page="tempalte/links.jsp" />
    </head>
        <body class="w3-theme-l4">
        <jsp:include page="tempalte/header.jsp" />
        <jsp:include page="tempalte/nav.jsp" />
        <main class="w3-auto w3-container">
            <div class="w3-row">
                <div class="w3-twothird w3-container">
                    <p>
                        <img class="w3-image" src="images/library/1.jpg">
                    </p>
                </div>
                <div class="w3-third w3-container w3-text-dark-gray w3-large">
                    <p><b><fmt:message key="main.contact-library.hours-1"/> </b><fmt:message key="main.contact-library.hours-2"/></p>
                    <p><b><fmt:message key="main.contact-library.address-1"/> </b><fmt:message key="main.contact-library.address-2"/></p>
                    <p><b><fmt:message key="main.contact-library.phone"/> </b>+375 17 263-34-92</p>
                    <p><b><fmt:message key="main.contact-library.email"/> </b>rodny_kut@gmail.com</p>
                    <iframe src="https://www.google.com/maps/embed?pb=!1m18!1m12!1m3!1d698.3849740949029!2d27.610829511959455!3d53.93036370059616!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0x46dbcf9f5e516ea7%3A0xaf7a6d80816e764c!2z0JHQuNCx0LvQuNC-0YLQtdC60LA!5e0!3m2!1sru!2sby!4v1642979228276!5m2!1sru!2sby" width="337" height="215" style="border:0;" allowfullscreen="" loading="lazy"></iframe>
                </div>
            </div>
        </main>
        <jsp:include page="tempalte/footer.jsp" />
    </body>
</html>
