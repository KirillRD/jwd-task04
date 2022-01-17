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
                <div class="w3-third w3-container"></div>
                <div class="w3-third w3-container w3-text-blue-grey">
                    <p class="w3-center"><b class="w3-xlarge"><fmt:message key="authentication.label"/></b></p>

                    <c:if test="${sessionScope.message != null}">
                        <div class="w3-row">
                            <div class="w3-panel w3-pale-red w3-leftbar w3-border-red w3-container">
                                <p><fmt:message key="message.${sessionScope.message}"/></p>
                            </div>
                        </div>
                        <c:remove var="message" scope="session"/>
                    </c:if>

                    <form class="w3-container w3-card w3-round-large" action="controller" method="post">
                        <input type="hidden" name="command" value="authentication">
                        <p>
                            <label><fmt:message key="user.email"/></label>
                            <input class="input-padding w3-input w3-round" type="text" name="email" value="${sessionScope.email}" required>
                            <c:remove var="email" scope="session"/>
                        </p>
                        <p>
                            <label><fmt:message key="user.password"/></label>
                            <input class="input-padding w3-input w3-round" type="password" name="password" required>
                        </p>
                        <button class="w3-button w3-right w3-theme w3-margin-bottom w3-round-large" type="submit"><fmt:message key="authentication.button"/></button>
                    </form>
                </div>
                <div class="w3-third w3-container"></div>
            </div>
        </main>
        <jsp:include page="tempalte/footer.jsp" />
    </body>
</html>
