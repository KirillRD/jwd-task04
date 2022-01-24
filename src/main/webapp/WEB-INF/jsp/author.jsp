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
            <div class="w3-container w3-text-blue-grey">
                <div class="w3-row">
                    <div class="w3-col w3-container" style="width: 10%"></div>
                    <div class="w3-col w3-container" style="width: 80%">
                        <h3 class="w3-center"><fmt:message key="author.authors"/></h3>

                        <c:if test="${sessionScope.message != null}">
                            <div class="w3-row">
                                <div class="w3-panel w3-pale-red w3-leftbar w3-border-red w3-container">
                                    <p><fmt:message key="message.${sessionScope.message}"/></p>
                                </div>
                            </div>
                            <c:remove var="message" scope="session"/>
                        </c:if>

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

                        <div class="w3-container w3-card w3-round-large">
                            <form action="controller" method="post">
                                <input type="hidden" name="command" value="add-edit-author">
                                <c:if test="${param.author_id != null}">
                                    <input type="hidden" name="author_id" value="${sessionScope.author.id}">
                                </c:if>

                                <div class="w3-third w3-container">
                                    <p>
                                        <label><fmt:message key="author.last-name"/></label>
                                        <input class="input-padding w3-input w3-round" type="text" name="last_name" value="${sessionScope.author.lastName}" required maxlength="30">
                                    </p>
                                </div>

                                <div class="w3-third w3-container">
                                    <p>
                                        <label><fmt:message key="author.first-name"/></label>
                                        <input class="input-padding w3-input w3-round" type="text" name="first_name" value="${sessionScope.author.firstName}" required maxlength="30">
                                    </p>
                                </div>

                                <div class="w3-third w3-container">
                                    <p>
                                        <label><fmt:message key="author.father-name"/></label>
                                        <input class="input-padding w3-input w3-round" type="text" name="father_name" value="${sessionScope.author.fatherName}" maxlength="30">
                                    </p>
                                </div>

                                <button class="w3-button w3-right w3-theme w3-margin-bottom w3-round-large" type="submit"><fmt:message key="author.save"/></button>
                            </form>

                            <form action="controller" method="get">
                                <input type="hidden" name="command" value="author-page">
                                <button class="w3-button w3-right w3-theme w3-margin-bottom w3-margin-right w3-round-large" type="submit"><fmt:message key="author.cancel"/></button>
                            </form>
                        </div>

                        <table class="w3-table w3-striped w3-border w3-hoverable w3-margin-top">
                            <tr>
                                <th><fmt:message key="author.last-name"/></th>
                                <th><fmt:message key="author.first-name"/></th>
                                <th><fmt:message key="author.father-name"/></th>
                                <th></th>
                                <th></th>
                            </tr>
                            <c:forEach var="author" items="${requestScope.authors}">
                                <tr class="
                                ${author.id == sessionScope.author.id ? 'w3-theme-l3' : ''}"
                                >
                                    <td>${author.lastName}</td>
                                    <td>${author.firstName}</td>
                                    <td>${author.fatherName}</td>
                                    <td>
                                        <form action="controller" method="get">
                                            <input type="hidden" name="command" value="author-page">
                                            <input type="hidden" name="author_id" value="${author.id}">
                                            <button class="link" type="submit"><span class="material-icons-outlined w3-text-blue-gray" title="<fmt:message key="author.edit-author"/>">mode_edit</span></button>
                                        </form>
                                    </td>
                                    <td>
                                        <form action="controller" method="post" onsubmit="return confirm('<fmt:message key="message.confirm-delete.author"/>');">
                                            <input type="hidden" name="command" value="delete-author">
                                            <input type="hidden" name="author_id" value="${author.id}">
                                            <button class="link" type="submit"
                                                ${author.authorIsUsed ? 'disabled' : ''}
                                            ><span class="material-icons-outlined
                                            ${author.authorIsUsed ? 'w3-text-gray' : 'w3-text-red'}"
                                                   title="<fmt:message key="author.delete-author"/>">clear</span></button>
                                        </form>
                                    </td>
                                </tr>
                            </c:forEach>
                        </table>
                    </div>
                    <div class="w3-col w3-container" style="width: 10%"></div>
                </div>
            </div>
            <c:remove var="author" scope="session"/>
        </main>
        <jsp:include page="tempalte/footer.jsp" />
    </body>
</html>
