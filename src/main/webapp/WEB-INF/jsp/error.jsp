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
            <div class="w3-row w3-padding-64">
                <div class="w3-third w3-container"></div>
                <div class="w3-third w3-container w3-round-large w3-card w3-text-blue-grey">
                    <h3 class="w3-text-red w3-center"><fmt:message key="error.error"/></h3>
                    <p class="w3-large w3-center"><fmt:message key="error.${requestScope.error}"/></p>
                </div>
                <div class="w3-third w3-container"></div>
            </div>
        </main>
        <jsp:include page="tempalte/footer.jsp" />
    </body>
</html>
