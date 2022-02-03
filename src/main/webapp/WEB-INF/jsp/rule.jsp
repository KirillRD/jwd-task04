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
        <main class="w3-auto w3-container w3-text-dark-gray">
            <div class="w3-container">
                <div class="w3-third">
                    <p>
                        <img class="w3-image" src="images/library/9.png">
                    </p>
                </div>
                <div class="w3-twothird">
                    <div class="w3-container">
                        <p class="w3-center w3-large">
                            <b><fmt:message key="main.rule-library.1"/></b>
                        </p>
                    </div>
                    <p class="w3-left w3-large">
                        <fmt:message key="main.rule-library.2"/>
                    </p>
                    <p class="w3-left w3-large">
                        <fmt:message key="main.rule-library.3"/>
                    </p>
                    <p class="w3-left w3-large">
                        <fmt:message key="main.rule-library.4"/>
                    </p>
                    <p class="w3-left w3-large">
                        <fmt:message key="main.rule-library.5"/>
                    </p>
                    <div class="w3-container">
                        <p class="w3-center w3-large">
                            <b><fmt:message key="main.rule-library.6"/></b>
                        </p>
                        <p class="w3-center w3-large">
                            <b><fmt:message key="main.rule-library.7"/></b>
                        </p>
                        <p class="w3-center w3-large">
                            <b><fmt:message key="main.rule-library.8"/></b>
                        </p>
                    </div>
                </div>
            </div>
        </main>
        <jsp:include page="tempalte/footer.jsp" />
    </body>
</html>
