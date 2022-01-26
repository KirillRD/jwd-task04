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
                    <div class="w3-col w3-container" style="width: 25%"></div>
                    <div class="w3-col w3-container" style="width: 50%">
                        <h3 class="w3-center"><fmt:message key="genre.genres"/></h3>

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
                                <input type="hidden" name="command" value="add-edit-genre">
                                <c:if test="${param.genre_id != null}">
                                    <input type="hidden" name="genre_id" value="${sessionScope.genre.id}">
                                </c:if>

                                <div class="w3-container">
                                    <p>
                                        <label><fmt:message key="genre.name"/></label>
                                        <input class="input-padding w3-input w3-round" type="text" name="name" value="${sessionScope.genre.name}" required maxlength="45">
                                    </p>
                                </div>

                                <button class="w3-button w3-right w3-theme w3-margin-bottom w3-round-large" type="submit"><fmt:message key="genre.save"/></button>
                            </form>

                            <form action="controller" method="get">
                                <input type="hidden" name="command" value="genre-page">
                                <button class="w3-button w3-right w3-theme w3-margin-bottom w3-margin-right w3-round-large" type="submit"><fmt:message key="genre.cancel"/></button>
                            </form>
                        </div>

                        <table class="w3-table w3-striped w3-border w3-hoverable w3-margin-top">
                            <tr>
                                <th><fmt:message key="genre.name"/></th>
                                <th></th>
                                <th></th>
                            </tr>
                            <c:forEach var="genre" items="${requestScope.genres}">
                                <tr class="
                                ${genre.id == sessionScope.genre.id ? 'w3-theme-l3' : ''}"
                                >
                                    <td>${genre.name}</td>
                                    <td>
                                        <form action="controller" method="get">
                                            <input type="hidden" name="command" value="genre-page">
                                            <input type="hidden" name="genre_id" value="${genre.id}">
                                            <button class="link" type="submit"><span class="material-icons-outlined w3-text-blue-gray" title="<fmt:message key="genre.edit-genre"/>">mode_edit</span></button>
                                        </form>
                                    </td>
                                    <td>
                                        <button onclick="document.getElementById('delete-${genre.id}').style.display='block'" class="link" ${genre.genreIsUsed ? 'disabled' : ''}>
                                        <span class="material-icons-outlined
                                            ${genre.genreIsUsed ? 'w3-text-gray' : 'w3-text-red'}"
                                              title="<fmt:message key="genre.delete-genre"/>">clear</span>
                                        </button>

                                        <div id="delete-${genre.id}" class="w3-modal">
                                            <div class="w3-modal-content w3-card-4 w3-animate-opacity" style="max-width:400px">
                                                <div class="w3-container w3-padding-large w3-border-bottom w3-light-gray">
                                                    <b class="w3-text-dark-gray"><fmt:message key="message.confirm-delete.genre"/></b>
                                                </div>

                                                <div class="w3-container w3-padding-large w3-border-top w3-theme-l4">
                                                    <button onclick="document.getElementById('delete-${genre.id}').style.display='none'" type="button" class="w3-button w3-red w3-right"><fmt:message key="delete.cancel"/></button>
                                                    <form action="controller" method="post">
                                                        <input type="hidden" name="command" value="delete-genre">
                                                        <input type="hidden" name="genre_id" value="${genre.id}">
                                                        <button class="w3-button w3-theme w3-right w3-margin-right" type="submit"><fmt:message key="delete.confirm"/></button>
                                                    </form>
                                                </div>
                                            </div>
                                        </div>
                                    </td>
                                </tr>
                            </c:forEach>
                        </table>
                    </div>
                    <div class="w3-col w3-container" style="width: 25%"></div>
                </div>
            </div>
            <c:remove var="genre" scope="session"/>
        </main>
        <jsp:include page="tempalte/footer.jsp" />
    </body>
</html>
