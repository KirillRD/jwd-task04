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
                <div class="w3-quarter w3-container"></div>
                <div class="w3-half w3-container w3-text-blue-grey">
                    <p class="w3-center"><b class="w3-xlarge"><fmt:message key="registration.label"/></b></p>
                    <form class="w3-container w3-card w3-round-large" action="controller" method="post">
                        <input type="hidden" name="command" value="registration">
                        <p>
                            <label><fmt:message key="user.email"/></label>
                            <input class="input-padding w3-input w3-round" type="text" name="email">
                        </p>
                        <p>
                            <label><fmt:message key="user.password"/></label>
                            <input class="input-padding w3-input w3-round" type="password" name="password">
                        </p>
                        <p>
                            <label><fmt:message key="user.repeated-password"/></label>
                            <input class="input-padding w3-input w3-round" type="password" name="repeated_password">
                        </p>
                        <p>
                            <label><fmt:message key="user.nickname"/></label>
                            <input class="input-padding w3-input w3-round" type="text" name="nickname">
                        </p>
                        <p>
                            <label><fmt:message key="user.last-name"/></label>
                            <input class="input-padding w3-input w3-round" type="text" name="last_name">
                        </p>
                        <p>
                            <label><fmt:message key="user.first-name"/></label>
                            <input class="input-padding w3-input w3-round" type="text" name="first_name">
                        </p>
                        <p>
                            <label><fmt:message key="user.father-name"/></label>
                            <input class="input-padding w3-input w3-round" type="text" name="father_name">
                        </p>
                        <p>
                            <label><fmt:message key="user.birthday"/></label>
                            <input class="input-padding w3-input w3-round" type="date" name="birthday">
                        </p>
                        <p>
                            <label><fmt:message key="user.gender"/></label>
                            <br>
                            <input class="w3-radio" type="radio" name="gender" value="M" checked>
                            <label><fmt:message key="user.male"/></label>
                            <input class="w3-radio" type="radio" name="gender" value="F">
                            <label><fmt:message key="user.female"/></label>
                        </p>
                        <p>
                            <label><fmt:message key="user.passport"/></label>
                            <input class="input-padding w3-input w3-round" type="text" name="passport">
                        </p>
                        <p>
                            <label><fmt:message key="user.address"/></label>
                            <input class="input-padding w3-input w3-round" type="text" name="address">
                        </p>
                        <p>
                            <label><fmt:message key="user.phone"/></label>
                            <input class="input-padding w3-input w3-round" type="text" name="phone">
                        </p>
                        <button class="w3-button w3-right w3-theme w3-margin-bottom w3-round-large" type="submit"><fmt:message key="registration.button"/></button>
                    </form>
                </div>
                <div class="w3-quarter w3-container"></div>
            </div>
        </main>
        <jsp:include page="tempalte/footer.jsp" />
<%--        <h2>Hello World!</h2>--%>
<%--        <form class="w3-container w3-card" action=addUser method="post">--%>
<%--            <p>--%>
<%--                <label>Login</label>--%>
<%--                <input class="w3-input w3-border" type="text" name="login">--%>
<%--            </p>--%>
<%--            <p>--%>
<%--                <label>Password</label>--%>
<%--                <input class="w3-input w3-border" type="text" name="password">--%>
<%--            </p>--%>
<%--            <p>--%>
<%--                <label>Repeated password</label>--%>
<%--                <input class="w3-input w3-border" type="text" name="repeatedPassword">--%>
<%--            </p>--%>
<%--            <p>--%>
<%--                <label>Email</label>--%>
<%--                <input class="w3-input w3-border" type="text" name="email">--%>
<%--            </p>--%>
<%--            <p>--%>
<%--                <label>Last name</label>--%>
<%--                <input class="w3-input w3-border" type="text" name="lastName">--%>
<%--            </p>--%>
<%--            <p>--%>
<%--                <label>First name</label>--%>
<%--                <input class="w3-input w3-border" type="text" name="firstName">--%>
<%--            </p>--%>
<%--            <p>--%>
<%--                <label>Father name</label>--%>
<%--                <input class="w3-input w3-border" type="text" name="fatherName">--%>
<%--            </p>--%>
<%--            <p>--%>
<%--                <label>Birthdate</label>--%>
<%--                <input class="w3-input w3-border" type="text" name="birthdate">--%>
<%--            </p>--%>
<%--            <p>--%>
<%--                <label>Gender</label>--%>
<%--                <input class="w3-input w3-border" type="text" name="gender">--%>
<%--            </p>--%>
<%--            <p>--%>
<%--                <label>Passport</label>--%>
<%--                <input class="w3-input w3-border" type="text" name="passport">--%>
<%--            </p>--%>
<%--            <p>--%>
<%--                <label>Address</label>--%>
<%--                <input class="w3-input w3-border" type="text" name="address">--%>
<%--            </p>--%>
<%--            <p>--%>
<%--                <label>Phone</label>--%>
<%--                <input class="w3-input w3-border" type="text" name="phone">--%>
<%--            </p>--%>
<%--            <p>--%>
<%--                <button class="w3-btn" type="submit">Registration</button>--%>
<%--            </p>--%>
<%--        </form>--%>
    </body>
</html>
