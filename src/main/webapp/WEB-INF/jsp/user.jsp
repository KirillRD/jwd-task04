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
        <main>
            <div class="w3-auto">
                <div class="w3-row">
                    <div class="w3-col w3-container" style="width: 15%"></div>
                    <div class="w3-col w3-container" style="width: 70%">
                        <div class="w3-third w3-container">
                            <div class="w3-margin-bottom w3-margin-top w3-display-container user-image-page">
                                <img class="user-image-page w3-display-middle" src="${requestScope.user.imageURL}">
                            </div>
                            <c:if test="${requestScope.user.role == 'READER'}">
                                <c:if test="${requestScope.reader.countDebts > 0}">
                                    <span class="w3-text-red"><fmt:message key="user.debts"/>: ${requestScope.reader.countDebts}</span>
                                    <br><span class="w3-text-red"><fmt:message key="user.days-debt"/>: ${requestScope.reader.countDaysDebts}</span>
                                </c:if>
                                <c:if test="${requestScope.reader.countReservation != 0}">
                                    <br><span class="
                                        <c:if test="${requestScope.reader.countReservation == requestScope.reader.countReservationReady}">
                                            w3-text-green
                                        </c:if>
                                    ">
                                        <fmt:message key="user.ready-reserved"/>: ${requestScope.reader.countReservationReady}/${requestScope.reader.countReservation}
                                    </span>
                                </c:if>
                            </c:if>
                        </div>
                        <div class="w3-twothird w3-container">
                            <p>
                                <b>
                                    ${requestScope.user.lastName}
                                    ${requestScope.user.firstName}
                                    ${requestScope.user.fatherName}
                                </b>
                                <br><fmt:message key="user.address"/>: ${requestScope.user.address}
                                <br><fmt:message key="user.phone"/>: ${requestScope.user.phone}
                                <br><fmt:message key="user.birthday"/>: ${requestScope.user.birthday}
                                <br><fmt:message key="user.email"/>: ${requestScope.user.email}
                                <br><fmt:message key="user.nickname"/>: ${requestScope.user.nickname}
                                <c:if test="${param.user_id != null}">
                                    <br><fmt:message key="user.role"/>: ${requestScope.user.role}
                                </c:if>
                            </p>
                            <form action="controller" method="get">
                                <input type="hidden" name="command" value="go-to-edit-user-page">
                                <c:if test="${param.user_id != null}">
                                    <input type="hidden" name="user_id" value="${requestScope.user.id}">
                                </c:if>
                                <button class="w3-button w3-right w3-theme w3-margin-bottom w3-round-large" type="submit"><fmt:message key="user.edit"/></button>
                            </form>
                            <c:if test="${sessionScope.session_user.id == requestScope.user.id}">
                                <form action="controller" method="get">
                                    <input type="hidden" name="command" value="log-out">
                                    <button class="w3-button w3-right w3-theme w3-margin-bottom w3-round-large w3-margin-right" type="submit"><fmt:message key="user.log-out"/></button>
                                </form>
                            </c:if>
                        </div>
                    </div>
                    <div class="w3-col w3-container" style="width: 15%"></div>
                </div>
            </div>


            <div class="w3-row w3-text-blue-grey">
                <div class="w3-col w3-container" style="width: 15%"></div>
                <div class="w3-col w3-container" style="width: 70%">
                    <c:if test="${requestScope.reader_issuance.size() != 0 && requestScope.reader_issuance != null}">
                        <div class="w3-container">
                            <h3><b><fmt:message key="reader.issued-books"/></b></h3>
                            <table class="w3-table w3-striped w3-border w3-hoverable">
                                <tr>
                                    <th><fmt:message key="reader.book"/></th>
                                    <th><fmt:message key="book.authors"/></th>
                                    <th><fmt:message key="instance.number"/></th>
                                    <th><fmt:message key="instance.hall"/></th>
                                    <th><fmt:message key="book.price"/></th>
                                    <th><fmt:message key="reader.issue-date"/></th>
                                    <th><fmt:message key="reader.return-planned-date-1"/><br><fmt:message key="reader.return-planned-date-2"/></th>
                                    <th><fmt:message key="reader.days-debt-1"/><br><fmt:message key="reader.days-debt-2"/></th>
                                    <th><fmt:message key="reader.lost"/></th>
                                </tr>
                                <c:forEach var="issuance" items="${requestScope.reader_issuance}">
                                    <tr>
                                        <td>
                                            <form action="controller" method="get">
                                                <input type="hidden" name="command" value="go-to-book-page">
                                                <input type="hidden" name="book_id" value="${issuance.bookID}">
                                                <button class="link" type="submit"><span>${issuance.bookName}</span></button>
                                            </form>
                                        </td>
                                        <td>${issuance.authors}</td>
                                        <td>${issuance.instanceNumber}</td>
                                        <td>${issuance.hallName}</td>
                                        <td>${issuance.bookPrice}</td>
                                        <td>${issuance.dateIssue}</td>
                                        <td>${issuance.dateReturnPlanned}</td>
                                        <td class="w3-text-red">
                                            <c:if test="${issuance.countDaysDebts > 0}">
                                                ${issuance.countDaysDebts}
                                            </c:if>
                                        </td>
                                        <td>
                                            <c:if test="${issuance.lost}">
                                                <span class="material-icons-outlined w3-text-red" title="Book is lost">delete</span>
                                            </c:if>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </table>
                        </div>
                    </c:if>

                    <c:if test="${requestScope.reader_reservation.size() != 0 && requestScope.reader_reservation != null}">
                        <div class="w3-container">
                            <h3><b><fmt:message key="reader.reserved-books"/></b></h3>
                            <table class="w3-table w3-striped w3-border w3-hoverable">
                                <tr>
                                    <th><fmt:message key="reader.book"/></th>
                                    <th><fmt:message key="book.authors"/></th>
                                    <th><fmt:message key="instance.number"/></th>
                                    <th><fmt:message key="instance.hall"/></th>
                                    <th><fmt:message key="reader.reservation-date-1"/><br><fmt:message key="reader.reservation-date-2"/></th>
                                    <th><fmt:message key="reader.status"/></th>
                                    <th><fmt:message key="reader.days-reservation-1"/><br><fmt:message key="reader.days-reservation-2"/></th>
                                    <c:if test="${requestScope.user.id == sessionScope.session_user.id}">
                                        <th></th>
                                    </c:if>
                                </tr>
                                <c:forEach var="reservation" items="${requestScope.reader_reservation}">
                                    <tr>
                                        <td>
                                            <form action="controller" method="get">
                                                <input type="hidden" name="command" value="go-to-book-page">
                                                <input type="hidden" name="book_id" value="${reservation.bookID}">
                                                <button class="link" type="submit"><span>${reservation.bookName}</span></button>
                                            </form>
                                        </td>
                                        <td>${reservation.authors}</td>
                                        <td>${reservation.instanceNumber}</td>
                                        <td>${reservation.hallName}</td>
                                        <td>${reservation.dateReservation}</td>
                                        <td class="
                                                <c:if test="${reservation.status == 'READY'}">
                                                    w3-text-green
                                                </c:if>
                                            ">
                                                ${reservation.status}
                                        </td>
                                        <td class="
                                                <c:if test="${reservation.reservationDebts}">
                                                    w3-text-red
                                                </c:if>
                                            ">
                                            <c:if test="${reservation.countDaysReservation == 0}">
                                                Today
                                            </c:if>
                                            <c:if test="${reservation.countDaysReservation != 0}">
                                                ${reservation.countDaysReservation}
                                            </c:if>
                                        </td>
                                        <c:if test="${requestScope.user.id == sessionScope.session_user.id}">
                                            <td>
                                                <form action="controller" method="post" onsubmit="return confirm('Do you really want this?');">
                                                    <input type="hidden" name="command" value="delete-reservation">
                                                    <input type="hidden" name="reservation_id" value="${reservation.reservationID}">
                                                    <button class="link" type="submit"
                                                        ${reservation.status == 'READY' ? 'disabled' : ''}
                                                    ><span class="material-icons-outlined
                                                        ${reservation.status == 'READY' ? 'w3-text-gray' : ''}
                                                        ${reservation.status == 'RESERVED' ? 'w3-text-red' : ''}"
                                                           title="Cancel reservation">clear</span>
                                                    </button>
                                                </form>
                                            </td>
                                        </c:if>
                                    </tr>
                                </c:forEach>
                            </table>
                        </div>
                    </c:if>

                    <c:if test="${requestScope.reader_issuance_history.size() != 0 && requestScope.reader_issuance_history != null}">
                        <div class="w3-container">
                            <h3><fmt:message key="reader.history-of-book-issuance"/></h3>
                            <table class="w3-table w3-striped w3-border w3-hoverable">
                                <tr>
                                    <th><fmt:message key="reader.book"/></th>
                                    <th><fmt:message key="book.authors"/></th>
                                    <th><fmt:message key="instance.number"/></th>
                                    <th><fmt:message key="instance.hall"/></th>
                                    <th><fmt:message key="book.price"/></th>
                                    <th><fmt:message key="reader.issue-date"/></th>
                                    <th><fmt:message key="reader.return-date"/></th>
                                    <th><fmt:message key="reader.days-debt-1"/><br><fmt:message key="reader.days-debt-2"/></th>
                                    <th><fmt:message key="reader.lost"/></th>
                                </tr>
                                <c:forEach var="issuance" items="${requestScope.reader_issuance_history}">
                                    <tr>
                                        <td>
                                            <form action="controller" method="get">
                                                <input type="hidden" name="command" value="go-to-book-page">
                                                <input type="hidden" name="book_id" value="${issuance.bookID}">
                                                <button class="link" type="submit"><span>${issuance.bookName}</span></button>
                                            </form>
                                        </td>
                                        <td>${issuance.authors}</td>
                                        <td>${issuance.instanceNumber}</td>
                                        <td>${issuance.hallName}</td>
                                        <td>${issuance.bookPrice}</td>
                                        <td>${issuance.dateIssue}</td>
                                        <td>${issuance.dateReturn}</td>
                                        <td class="w3-text-red">
                                            <c:if test="${issuance.countDaysDebts > 0}">
                                                ${issuance.countDaysDebts}
                                            </c:if>
                                        </td>
                                        <td>
                                            <c:if test="${issuance.lost}">
                                                <span class="material-icons-outlined w3-text-red" title="Book is lost">delete</span>
                                            </c:if>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </table>
                        </div>
                    </c:if>

                    <c:if test="${requestScope.reader_reservation_history.size() != 0 && requestScope.reader_reservation_history != null}">
                        <div class="w3-container">
                            <h3><fmt:message key="reader.history-of-book-reservation"/></h3>
                            <table class="w3-table w3-striped w3-border w3-hoverable">
                                <tr>
                                    <th><fmt:message key="reader.book"/></th>
                                    <th><fmt:message key="book.authors"/></th>
                                    <th><fmt:message key="instance.number"/></th>
                                    <th><fmt:message key="instance.hall"/></th>
                                    <th><fmt:message key="reader.reservation-date-1"/><br><fmt:message key="reader.reservation-date-2"/></th>
                                    <th><fmt:message key="reader.status"/></th>
                                </tr>
                                <c:forEach var="reservation" items="${requestScope.reader_reservation_history}">
                                    <tr>
                                        <td>
                                            <form action="controller" method="get">
                                                <input type="hidden" name="command" value="go-to-book-page">
                                                <input type="hidden" name="book_id" value="${reservation.bookID}">
                                                <button class="link" type="submit"><span>${reservation.bookName}</span></button>
                                            </form>
                                        </td>
                                        <td>${reservation.authors}</td>
                                        <td>${reservation.instanceNumber}</td>
                                        <td>${reservation.hallName}</td>
                                        <td>${reservation.dateReservation}</td>
                                        <td class="
                                            <c:if test="${reservation.status != 'ISSUED'}">
                                                w3-text-red
                                            </c:if>
                                        ">
                                                ${reservation.status}
                                        </td>
                                    </tr>
                                </c:forEach>
                            </table>
                        </div>
                    </c:if>
                </div>
                <div class="w3-col w3-container" style="width: 15%"></div>
            </div>
        </main>
        <jsp:include page="tempalte/footer.jsp" />
    </body>
</html>
