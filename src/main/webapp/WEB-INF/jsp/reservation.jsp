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
            <c:if test="${param.reservation_message == null}">
                <p class="w3-center w3-text-blue-gray"><b class="w3-xlarge"><fmt:message key="reservation.label"/></b></p>
                <c:if test="${requestScope.reader_reservation.size() != 0 && requestScope.reader_reservation != null}">
                    <div class="w3-container w3-margin-bottom w3-text-blue-gray">
                        <h3><fmt:message key="reader.reserved-books"/></h3>
                        <table class="w3-table w3-striped w3-border w3-hoverable">
                            <tr>
                                <th><fmt:message key="book.name"/></th>
                                <th><fmt:message key="book.authors"/></th>
                                <th><fmt:message key="instance.number"/></th>
                                <th><fmt:message key="instance.hall"/></th>
                                <th><fmt:message key="reader.reservation-date-1"/><br><fmt:message key="reader.reservation-date-2"/></th>
                                <th><fmt:message key="reader.status"/></th>
                                <th><fmt:message key="reader.days-reservation-1"/><br><fmt:message key="reader.days-reservation-2"/></th>
                            </tr>
                            <c:forEach var="reservation" items="${requestScope.reader_reservation}">
                                <tr>
                                    <td>
                                        <a class="w3-hover-text-blue w3-text-dark-grey" href="controller?command=go-to-book-page&book_id=${reservation.bookID}">
                                            ${reservation.bookName}
                                        </a>
                                    </td>
                                    <td>${reservation.authors}</td>
                                    <td>${reservation.instanceNumber}</td>
                                    <td>${reservation.hallName}</td>
                                    <td>${reservation.dateReservation}</td>
                                    <td class="
                                                ${reservation.status == 'READY' ? 'w3-text-green' : ''}
                                            ">
                                            ${reservation.status}
                                    </td>
                                    <td class="
                                                ${reservation.reservationDebts ? 'w3-text-red' : ''}
                                            ">
                                        <c:if test="${reservation.countDaysReservation == 0}">
                                            Today
                                        </c:if>
                                        <c:if test="${reservation.countDaysReservation != 0}">
                                            ${reservation.countDaysReservation}
                                        </c:if>
                                    </td>
                                </tr>
                            </c:forEach>
                        </table>
                    </div>
                </c:if>

                <div class="w3-quarter w3-container">
                    <div class="w3-margin-bottom w3-margin-top w3-display-container book-small-image-preview">
                        <img class="book-small-image w3-display-middle" src="${requestScope.book_info.imageURL}">
                    </div>
                </div>
                <div class="w3-quarter w3-container">
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

                <form action="controller" method="post">
                    <input type="hidden" name="command" value="add-reservation">
                    <input type="hidden" name="book_id" value="${requestScope.book_info.id}">
                    <div class="w3-quarter w3-container">
                        <p>
                            <label><fmt:message key="reservation.halls"/></label>
                            <br>
                            <c:forEach var="instance" items="${requestScope.book_info.hallFreeInstanceCatalogList}">
                                <input class="w3-radio" type="radio" name="hall_id" value="${instance.id}" required>
                                <label>${instance.hallFreeInstances}</label>
                                <br>
                            </c:forEach>
                        </p>
                        <p>
                            <label><fmt:message key="reservation.reservation-date"/></label>
                            <input class="input-padding w3-input w3-round" type="date" name="reservation_date" required>
                        </p>
                    </div>

                    <div class="w3-quarter w3-container">
                        <button class="w3-button w3-green w3-margin-top w3-round-large w3-block" type="submit"><fmt:message key="reservation.confirm-reservation"/></button>
                    </div>
                </form>
            </c:if>


            <c:if test="${param.reservation_message != null}">
                <div class="w3-row w3-padding-64">
                    <div class="w3-third w3-container"></div>
                    <div class="w3-third w3-container w3-round-large w3-card">
                        <p class="w3-center w3-large w3-text-dark-gray"><fmt:message key="reservation.${param.reservation_message}"/></p>
                        <form action="controller" method="get">
                            <input type="hidden" name="command" value="go-to-book-page">
                            <input type="hidden" name="book_id" value="${param.book_id}">
                            <button class="w3-button w3-right w3-theme w3-margin-bottom w3-round-large" type="submit">OK</button>
                        </form>
                    </div>
                    <div class="w3-third w3-container"></div>
                </div>
            </c:if>
        </main>
        <jsp:include page="tempalte/footer.jsp" />
    </body>
</html>
