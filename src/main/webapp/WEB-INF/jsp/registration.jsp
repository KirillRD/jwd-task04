<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

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
                <div class="w3-quarter w3-container"></div>
                <div class="w3-half w3-container w3-text-blue-grey">
                    <p class="w3-center"><b class="w3-xlarge"><fmt:message key="registration.label"/></b></p>

                    <c:if test="${sessionScope.messages != null}">
                        <div class="w3-row">
                            <div class="w3-panel w3-pale-red w3-leftbar w3-border-red w3-container">
                                <c:forEach var="message" items="${sessionScope.messages}">
                                    <p><fmt:message key="message.${message}"/></p>
                                </c:forEach>
                            </div>
                        </div>
                        <c:remove var="messages" scope="session"/>
                    </c:if>

                    <form class="w3-container w3-card w3-round-large" action="controller" method="post">
                        <input type="hidden" name="command" value="registration">
                        <p>
                            <label><fmt:message key="user.email"/></label>
                            <input class="input-padding w3-input w3-round" type="email" name="email" value="${fn:escapeXml(sessionScope.user.email)}" required>
                        </p>
                        <p>
                            <label><fmt:message key="user.password"/></label>
                            <input class="input-padding w3-input w3-round" type="password" name="password" required>
                        </p>
                        <p>
                            <label><fmt:message key="user.repeated-password"/></label>
                            <input class="input-padding w3-input w3-round" type="password" name="repeated_password" required>
                        </p>
                        <p>
                            <label><fmt:message key="user.nickname"/></label>
                            <input class="input-padding w3-input w3-round" type="text" name="nickname" value="${fn:escapeXml(sessionScope.user.nickname)}" required>
                        </p>
                        <p>
                            <label><fmt:message key="user.last-name"/></label>
                            <input class="input-padding w3-input w3-round" type="text" name="last_name" value="${fn:escapeXml(sessionScope.user.lastName)}" required>
                        </p>
                        <p>
                            <label><fmt:message key="user.first-name"/></label>
                            <input class="input-padding w3-input w3-round" type="text" name="first_name" value="${fn:escapeXml(sessionScope.user.firstName)}" required>
                        </p>
                        <p>
                            <label><fmt:message key="user.father-name"/></label>
                            <input class="input-padding w3-input w3-round" type="text" name="father_name" value="${fn:escapeXml(sessionScope.user.fatherName)}">
                        </p>
                        <p>
                            <label><fmt:message key="user.birthday"/></label>
                            <input class="input-padding w3-input w3-round" type="date" name="birthday" value="${sessionScope.user.birthday}" required min="1900-01-01" max="2099-12-31">
                        </p>
                        <p>
                            <label><fmt:message key="user.gender"/></label>
                            <br>
                            <input class="w3-radio" type="radio" name="gender" value="M"
                                   ${sessionScope.user.gender == 'M' ? 'checked' : ''}
                            required>
                            <label><fmt:message key="user.male"/></label>
                            <input class="w3-radio" type="radio" name="gender" value="F"
                                   ${sessionScope.user.gender == 'F' ? 'checked' : ''}
                            required>
                            <label><fmt:message key="user.female"/></label>
                        </p>
                        <p>
                            <label><fmt:message key="user.passport"/></label>
                            <input class="input-padding w3-input w3-round" type="text" name="passport" value="${fn:escapeXml(sessionScope.user.passport)}">
                        </p>
                        <p>
                            <label><fmt:message key="user.address"/></label>
                            <input class="input-padding w3-input w3-round" type="text" name="address" value="${fn:escapeXml(sessionScope.user.address)}">
                        </p>
                        <p>
                            <label><fmt:message key="user.phone"/></label>
                            <input class="input-padding w3-input w3-round" type="text" name="phone" value="${sessionScope.user.phone}" placeholder="+______________">
                        </p>
                        <button class="w3-button w3-right w3-theme w3-margin-bottom w3-round-large" type="submit"><fmt:message key="registration.button"/></button>
                    </form>
                    <c:remove var="user" scope="session"/>
                </div>
                <div class="w3-quarter w3-container"></div>
            </div>
        </main>
        <jsp:include page="tempalte/footer.jsp" />
    </body>
</html>
