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
            <div class="w3-quarter w3-container">
                <div class="w3-margin-top w3-display-container book-small-image-preview">
                    <img class="book-small-image w3-display-middle" src="${requestScope.book_info.imageURL}">
                </div>
            </div>
            <div class="w3-threequarter w3-container">
                <p>
                    <b>${requestScope.book_info.name}</b>
                    <br>
                    <c:forEach var="author" varStatus="loop" items="${requestScope.book_info.authors}">
                        ${loop.index != 0 ? ',' : ''}
                        ${author}
                    </c:forEach>
                    <br>
                    <br><fmt:message key="book.publisher"/>: ${requestScope.book_info.publisher}
                    <br><fmt:message key="book.city"/>: ${requestScope.book_info.city}
                    <br><fmt:message key="book.publication-year"/>: ${requestScope.book_info.publicationYear}
                    <br><fmt:message key="book.pages"/>: ${requestScope.book_info.pages}
                    <c:if test="${requestScope.book_info.part > 0}">
                        <br><fmt:message key="book.part"/>: ${requestScope.book_info.part}
                    </c:if>
                    <br><fmt:message key="book.type"/>: ${requestScope.book_info.type}
                    <br><fmt:message key="book.genres"/>:
                    <c:forEach var="genre" varStatus="loop" items="${requestScope.book_info.genres}">
                        ${loop.index != 0 ? ',' : ''}
                        ${genre}
                    </c:forEach>
                    <br>
                    <c:if test="${requestScope.book_info.isbn != ''}">
                        <br><fmt:message key="book.isbn"/>: ${requestScope.book_info.isbn}
                    </c:if>
                    <c:if test="${requestScope.book_info.issn != ''}">
                        <br><fmt:message key="book.issn"/>: ${requestScope.book_info.issn}
                    </c:if>
                </p>
            </div>

            <div class="w3-container w3-text-blue-grey">
                <div class="w3-row">
                    <div class="w3-col w3-container" style="width: 10%"></div>
                    <div class="w3-col w3-container" style="width: 80%">
                        <h3 class="w3-center"><fmt:message key="instance.instances"/></h3>

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
                                <input type="hidden" name="command" value="add-edit-instance">
                                <input type="hidden" name="book_id" value="${requestScope.book_info.id}">
                                <c:if test="${param.instance_id != null}">
                                    <input type="hidden" name="instance_id" value="${sessionScope.instance.id}">
                                </c:if>

                                <div class="w3-half w3-container">
                                    <p>
                                        <label><fmt:message key="instance.number"/></label>
                                        <input class="input-padding w3-input w3-round" type="text" name="number" value="${sessionScope.instance.number}" required maxlength="10">
                                    </p>
                                    <p>
                                        <label><fmt:message key="instance.hall"/></label>
                                        <select id="single-hall" type="text" name="hall">
                                            <option data-placeholder="true"></option>
                                            <c:forEach var="hall" items="${requestScope.halls}">
                                                <option value="${hall.id}"
                                                    ${hall.id == sessionScope.instance.hallID ? 'selected' : ''}
                                                >${hall.name}</option>
                                            </c:forEach>
                                        </select>
                                        <script>
                                            new SlimSelect({
                                                select: '#single-hall',
                                                allowDeselect: true
                                            })
                                        </script>
                                    </p>
                                </div>

                                <div class="w3-half w3-container">
                                    <p>
                                        <label><fmt:message key="instance.received-date"/></label>
                                        <input class="input-padding w3-input w3-round" type="date" name="received_date" value="${sessionScope.instance.receivedDate}" required min="1900-01-01" max="2099-12-31">
                                    </p>
                                    <p>
                                        <label><fmt:message key="instance.write-off-date"/></label>
                                        <input class="input-padding w3-input w3-round" type="date" name="write_off_date" value="${sessionScope.instance.writeOffDate}" min="1900-01-01" max="2099-12-31">
                                    </p>
                                </div>
                                <button class="w3-button w3-right w3-theme w3-margin-bottom w3-round-large" type="submit"><fmt:message key="instance.save"/></button>
                            </form>

                            <form action="controller" method="get">
                                <input type="hidden" name="command" value="instance-page">
                                <input type="hidden" name="book_id" value="${requestScope.book_info.id}">
                                <button class="w3-button w3-right w3-theme w3-margin-bottom w3-margin-right w3-round-large" type="submit"><fmt:message key="instance.cancel"/></button>
                            </form>
                        </div>
                    </div>
                    <div class="w3-col w3-container" style="width: 10%"></div>
                </div>

                <div class="w3-container w3-margin-top">
                    <table class="w3-table w3-striped w3-border w3-hoverable">
                        <tr>
                            <th><fmt:message key="instance.number"/></th>
                            <th><fmt:message key="instance.hall"/></th>
                            <th><fmt:message key="instance.received"/></th>
                            <th><fmt:message key="instance.write-off"/></th>
                            <th><fmt:message key="instance.status"/></th>
                            <th></th>
                            <th></th>
                        </tr>
                        <c:forEach var="instance" items="${requestScope.book_instances}">
                            <tr class="
                                ${instance.id == sessionScope.instance.id ? 'w3-theme-l3' : ''}"
                            >
                                <td>${instance.number}</td>
                                <td>${instance.hallName}</td>
                                <td>${instance.receivedDate}</td>
                                <td>${instance.writeOffDate}</td>
                                <td class="${instance.status == 'LOST' ? 'w3-text-red' : ''}
                                           ${instance.status == 'FREE' ? 'w3-text-green' : ''}
                                           ${instance.status == 'ISSUED' ? 'w3-text-yellow' : ''}
                                           ${instance.status == 'RESERVED' ? 'w3-text-yellow' : ''}">
                                    <c:choose>
                                        <c:when test="${instance.status == 'LOST'}"><fmt:message key="status.instance.lost"/></c:when>
                                        <c:when test="${instance.status == 'FREE'}"><fmt:message key="status.instance.free"/></c:when>
                                        <c:when test="${instance.status == 'ISSUED'}"><fmt:message key="status.instance.issued"/></c:when>
                                        <c:when test="${instance.status == 'RESERVED'}"><fmt:message key="status.instance.reserved"/></c:when>
                                        <c:when test="${instance.status == 'WRITE OFF'}"><fmt:message key="status.instance.write-off"/></c:when>
                                    </c:choose>
                                </td>
                                <td>
                                    <form action="controller" method="get">
                                        <input type="hidden" name="command" value="instance-page">
                                        <input type="hidden" name="book_id" value="${requestScope.book_info.id}">
                                        <input type="hidden" name="instance_id" value="${instance.id}">
                                        <button class="link" type="submit"><span class="material-icons-outlined w3-text-blue-gray" title="<fmt:message key="instance.edit-instance"/>">mode_edit</span></button>
                                    </form>
                                </td>
                                <td>
                                    <form action="controller" method="post" onsubmit="return confirm('<fmt:message key="message.confirm-delete.instance"/>');">
                                        <input type="hidden" name="command" value="delete-instance">
                                        <input type="hidden" name="book_id" value="${requestScope.book_info.id}">
                                        <input type="hidden" name="instance_id" value="${instance.id}">
                                        <button class="link" type="submit"
                                            ${instance.instanceIsUsed ? 'disabled' : ''}
                                        ><span class="material-icons-outlined
                                            ${instance.instanceIsUsed ? 'w3-text-gray' : 'w3-text-red'}"
                                        title="<fmt:message key="instance.delete-instance"/>">clear</span></button>
                                    </form>
                                </td>
                            </tr>
                        </c:forEach>
                    </table>
                </div>
            </div>
            <c:remove var="instance" scope="session"/>
        </main>
        <jsp:include page="tempalte/footer.jsp" />
    </body>
</html>
