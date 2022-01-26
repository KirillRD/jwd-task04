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
                                <img class="user-image-page w3-display-middle" src="${requestScope.reader.imageURL}">
                            </div>
                            <c:if test="${requestScope.reader.countDebts > 0}">
                                <span class="w3-text-red"><fmt:message key="user.debts"/>: ${requestScope.reader.countDebts}</span>
                                <br><span class="w3-text-red"><fmt:message key="user.days-debt"/>: ${requestScope.reader.countDaysDebts}</span>
                            </c:if>
                            <c:if test="${requestScope.reader.countReservation != 0}">
                                <br><span class="
                                    ${requestScope.reader.countReservation == requestScope.reader.countReservationReady ? 'w3-text-green' : ''}
                                ">
                                    <fmt:message key="user.ready-reserved"/>: ${requestScope.reader.countReservationReady}/${requestScope.reader.countReservation}
                                </span>
                            </c:if>
                        </div>
                        <div class="w3-twothird w3-container">
                            <p>
                                <b>
                                    ${requestScope.reader.lastName}
                                    ${requestScope.reader.firstName}
                                    ${requestScope.reader.fatherName}
                                    <c:if test="${requestScope.reader.lock}">
                                        <span class="material-icons-outlined w3-text-red" title="<fmt:message key="user-list.locked"/>">person_off</span>
                                    </c:if>
                                </b>
                                <br><fmt:message key="user.address"/>: ${requestScope.reader.address}
                                <br><fmt:message key="user.phone"/>: ${requestScope.reader.phone}
                                <br><fmt:message key="user.birthday"/>: ${requestScope.reader.birthday}
                                <br><fmt:message key="user.email"/>: ${requestScope.reader.email}
                                <br><fmt:message key="user.nickname"/>: ${requestScope.reader.nickname}
                            </p>
                            <form action="controller" method="get">
                                <input type="hidden" name="command" value="book-issuance-page">
                                <input type="hidden" name="reader_id" value="${requestScope.reader.id}">
                                <button class="w3-button w3-right w3-theme w3-margin-bottom w3-round-large" type="submit"><fmt:message key="reader.issue"/></button>
                            </form>
                            <form action="controller" method="get">
                                <input type="hidden" name="command" value="go-to-edit-user-page">
                                <input type="hidden" name="reader_id" value="${requestScope.reader.id}">
                                <button class="w3-button w3-right w3-theme w3-margin-bottom w3-round-large w3-margin-right" type="submit"><fmt:message key="user.edit"/></button>
                            </form>
                        </div>
                    </div>
                    <div class="w3-col w3-container " style="width: 15%"></div>
                </div>
            </div>

            <div class="w3-row w3-text-blue-grey">
                <div class="w3-col w3-container" style="width: 10%"></div>
                <div class="w3-col w3-container" style="width: 80%">
                    <c:if test="${requestScope.reader_issuance.size() != 0}">
                        <div class="w3-container">
                            <div class="w3-padding">
                                <b class="w3-large w3-text-dark-gray"><fmt:message key="reader.issued-books"/></b>
                            </div>

                            <c:if test="${sessionScope.issuance_message != null}">
                                <div class="w3-row">
                                    <div class="w3-panel w3-pale-red w3-leftbar w3-border-red w3-container">
                                        <p><fmt:message key="message.${sessionScope.issuance_message}"/></p>
                                    </div>
                                </div>
                                <c:remove var="issuance_message" scope="session"/>
                            </c:if>

                            <form action="controller" method="post">
                                <input type="hidden" name="command" value="reader-issuance-operation">
                                <input type="hidden" name="reader_id" value="${requestScope.reader.id}">
                                <table class="w3-table w3-striped w3-border w3-hoverable">
                                    <tr>
                                        <th><fmt:message key="reader.book"/></th>
                                        <th><fmt:message key="book.authors"/></th>
                                        <th><fmt:message key="instance.number"/></th>
                                        <th><fmt:message key="instance.hall"/></th>
                                        <th><fmt:message key="book.price"/></th>
                                        <th><fmt:message key="reader.rental-price-1"/><br><fmt:message key="reader.rental-price-2"/></th>
                                        <th><fmt:message key="reader.count-rental-price-1"/><br><fmt:message key="reader.count-rental-price-2"/></th>
                                        <th><fmt:message key="reader.issue-date"/></th>
                                        <th><fmt:message key="reader.return-planned-date-1"/><br><fmt:message key="reader.return-planned-date-2"/></th>
                                        <th><fmt:message key="reader.days-debt-1"/><br><fmt:message key="reader.days-debt-2"/></th>
                                        <th><fmt:message key="reader.lost"/></th>
                                        <th><fmt:message key="reader.select"/></th>
                                    </tr>
                                    <c:forEach var="issuance" items="${requestScope.reader_issuance}">
                                        <tr>
                                            <td>
                                                <a class="w3-hover-text-blue w3-text-dark-grey" href="controller?command=go-to-book-page&book_id=${issuance.bookID}">
                                                    ${issuance.bookName}
                                                </a>
                                            </td>
                                            <td>${issuance.authors}</td>
                                            <td>${issuance.instanceNumber}</td>
                                            <td>
                                                <c:if test="${issuance.dateIssue == issuance.dateReturnPlanned && issuance.countDaysRental == 0}">
                                                    <b>${issuance.hallName}</b>
                                                </c:if>
                                                <c:if test="${issuance.dateIssue != issuance.dateReturnPlanned || issuance.countDaysRental != 0}">
                                                    ${issuance.hallName}
                                                </c:if>
                                            </td>
                                            <td>${issuance.bookPrice}</td>
                                            <td>
                                                <c:if test="${issuance.rentalPrice > 0}">
                                                    ${issuance.rentalPrice}
                                                </c:if>
                                            </td>
                                            <td>
                                                <c:if test="${issuance.countDaysRental > 0}">
                                                    ${issuance.countDaysRental}
                                                </c:if>
                                            </td>
                                            <td>${issuance.dateIssue}</td>
                                            <td>${issuance.dateReturnPlanned}</td>
                                            <td class="w3-text-red">
                                                <c:if test="${issuance.countDaysDebts > 0}">
                                                    ${issuance.countDaysDebts}
                                                </c:if>
                                            </td>
                                            <td>
                                                <c:if test="${issuance.lost}">
                                                    <span class="material-icons-outlined w3-text-red" title="<fmt:message key="reader.lost-book"/>">delete</span>
                                                </c:if>
                                            </td>
                                            <td>
                                                <input class="w3-check" type="checkbox" name="check_issuance_operation" value="${issuance.issuanceID}">
                                            </td>
                                        </tr>
                                    </c:forEach>
                                </table>
                                <button class="w3-button w3-right w3-margin-top w3-theme w3-round-large" type="submit" name="issuance_operation" value="lost"><fmt:message key="reader.lost"/></button>
                                <button class="w3-button w3-right w3-margin-top w3-margin-right w3-theme w3-round-large" type="submit" name="issuance_operation" value="extend"><fmt:message key="reader.extend"/></button>
                                <button class="w3-button w3-right w3-margin-top w3-margin-right w3-theme w3-round-large" type="submit" name="issuance_operation" value="return"><fmt:message key="reader.return"/></button>
                            </form>
                        </div>
                    </c:if>

                    <c:if test="${requestScope.reader_reservation.size() != 0}">
                        <div class="w3-container">
                            <div class="w3-padding">
                                <b class="w3-large w3-text-dark-gray"><fmt:message key="reader.reserved-books"/></b>
                            </div>

                            <c:if test="${sessionScope.reservation_message != null}">
                                <div class="w3-row">
                                    <div class="w3-panel w3-pale-red w3-leftbar w3-border-red w3-container">
                                        <p><fmt:message key="message.${sessionScope.reservation_message}"/></p>
                                    </div>
                                </div>
                                <c:remove var="reservation_message" scope="session"/>
                            </c:if>

                            <form action="controller" method="post">
                                <input type="hidden" name="command" value="reader-reservation-operation">
                                <input type="hidden" name="reader_id" value="${requestScope.reader.id}">
                                <table class="w3-table w3-striped w3-border w3-hoverable">
                                    <tr>
                                        <th><fmt:message key="reader.book"/></th>
                                        <th><fmt:message key="book.authors"/></th>
                                        <th><fmt:message key="instance.number"/></th>
                                        <th><fmt:message key="instance.hall"/></th>
                                        <th><fmt:message key="reader.reservation-date-1"/><br><fmt:message key="reader.reservation-date-2"/></th>
                                        <th><fmt:message key="reader.status"/></th>
                                        <th><fmt:message key="reader.days-reservation-1"/><br><fmt:message key="reader.days-reservation-2"/></th>
                                        <th><fmt:message key="reader.select"/></th>
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
                                                <c:choose>
                                                    <c:when test="${reservation.status == 'RESERVED'}"><fmt:message key="status.reservation.reserved"/></c:when>
                                                    <c:when test="${reservation.status == 'READY'}"><fmt:message key="status.reservation.ready"/></c:when>
                                                </c:choose>
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
                                            <td>
                                                <input class="w3-check" type="checkbox" name="check_reservation_operation" value="${reservation.reservationID}">
                                            </td>
                                        </tr>
                                    </c:forEach>
                                </table>
                                <button class="w3-button w3-right w3-margin-top w3-theme w3-round-large" type="submit" name="reservation_operation" value="REJECTED"><fmt:message key="reader.reject"/></button>
                                <button class="w3-button w3-right w3-margin-top w3-margin-right w3-theme w3-round-large" type="submit" name="reservation_operation" value="EXPIRED"><fmt:message key="reader.expired"/></button>
                                <button class="w3-button w3-right w3-margin-top w3-margin-right w3-theme w3-round-large" type="submit" name="reservation_operation" value="CANCELLED"><fmt:message key="reader.cancel"/></button>
                                <button class="w3-button w3-right w3-margin-top w3-margin-right w3-theme w3-round-large" type="submit" name="reservation_operation" value="ISSUED"><fmt:message key="reader.issue"/></button>
                                <button class="w3-button w3-right w3-margin-top w3-margin-right w3-theme w3-round-large" type="submit" name="reservation_operation" value="READY"><fmt:message key="reader.ready"/></button>
                            </form>
                        </div>
                    </c:if>

                    <c:if test="${requestScope.reader_issuance_history.size() != 0}">
                        <div class="w3-container">
                            <button onclick="accordion('issuance-history')" class="w3-button w3-block w3-left-align w3-theme-l3 w3-margin-top">
                                <span class="w3-text-dark-gray w3-large"><fmt:message key="reader.history-of-book-issuance"/></span>
                            </button>

                            <div id="issuance-history" class="w3-hide w3-margin-top">
                                <table class="w3-table w3-striped w3-border w3-hoverable">
                                    <tr>
                                        <th><fmt:message key="reader.book"/></th>
                                        <th><fmt:message key="book.authors"/></th>
                                        <th><fmt:message key="instance.number"/></th>
                                        <th><fmt:message key="instance.hall"/></th>
                                        <th><fmt:message key="book.price"/></th>
                                        <th><fmt:message key="reader.rental-price-1"/><br><fmt:message key="reader.rental-price-2"/></th>
                                        <th><fmt:message key="reader.count-rental-price-1"/><br><fmt:message key="reader.count-rental-price-2"/></th>
                                        <th><fmt:message key="reader.issue-date"/></th>
                                        <th><fmt:message key="reader.return-date"/></th>
                                        <th><fmt:message key="reader.days-debt-1"/><br><fmt:message key="reader.days-debt-2"/></th>
                                        <th><fmt:message key="reader.lost"/></th>
                                    </tr>
                                    <c:forEach var="issuance" items="${requestScope.reader_issuance_history}">
                                        <tr>
                                            <td>
                                                <a class="w3-hover-text-blue w3-text-dark-grey" href="controller?command=go-to-book-page&book_id=${issuance.bookID}">
                                                        ${issuance.bookName}
                                                </a>
                                            </td>
                                            <td>${issuance.authors}</td>
                                            <td>${issuance.instanceNumber}</td>
                                            <td>${issuance.hallName}</td>
                                            <td>
                                                <c:if test="${issuance.bookPrice > 0}">
                                                    ${issuance.bookPrice}
                                                </c:if>
                                            </td>
                                            <td>
                                                <c:if test="${issuance.rentalPrice > 0}">
                                                    ${issuance.rentalPrice}
                                                </c:if>
                                            </td>
                                            <td>
                                                <c:if test="${issuance.countDaysRental > 0}">
                                                    ${issuance.countDaysRental}
                                                </c:if>
                                            </td>
                                            <td>${issuance.dateIssue}</td>
                                            <td>${issuance.dateReturn}</td>
                                            <td class="w3-text-red">
                                                <c:if test="${issuance.countDaysDebts > 0}">
                                                    ${issuance.countDaysDebts}
                                                </c:if>
                                            </td>
                                            <td>
                                                <c:if test="${issuance.lost}">
                                                    <span class="material-icons-outlined w3-text-red" title="<fmt:message key="reader.lost-book"/>">delete</span>
                                                </c:if>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                </table>
                            </div>
                        </div>
                    </c:if>

                    <c:if test="${requestScope.reader_reservation_history.size() != 0}">
                        <div class="w3-container">
                            <button onclick="accordion('reservation-history')" class="w3-button w3-block w3-left-align w3-theme-l3 w3-margin-top">
                                <span class="w3-text-dark-gray w3-large"><fmt:message key="reader.history-of-book-reservation"/></span>
                            </button>

                            <div id="reservation-history" class="w3-hide w3-margin-top">
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
                                                <a class="w3-hover-text-blue w3-text-dark-grey" href="controller?command=go-to-book-page&book_id=${reservation.bookID}">
                                                        ${reservation.bookName}
                                                </a>
                                            </td>
                                            <td>${reservation.authors}</td>
                                            <td>${reservation.instanceNumber}</td>
                                            <td>${reservation.hallName}</td>
                                            <td>${reservation.dateReservation}</td>
                                            <td class="
                                            ${reservation.status != 'ISSUED' ? 'w3-text-red' : ''}
                                        ">
                                                <c:choose>
                                                    <c:when test="${reservation.status == 'ISSUED'}"><fmt:message key="status.reservation.issued"/></c:when>
                                                    <c:when test="${reservation.status == 'CANCELLED'}"><fmt:message key="status.reservation.canceled"/></c:when>
                                                    <c:when test="${reservation.status == 'EXPIRED'}"><fmt:message key="status.reservation.expired"/></c:when>
                                                    <c:when test="${reservation.status == 'REJECTED'}"><fmt:message key="status.reservation.rejected"/></c:when>
                                                </c:choose>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                </table>
                            </div>
                        </div>
                    </c:if>

                    <script>
                        function accordion(id) {
                            var x = document.getElementById(id);
                            if (x.className.indexOf("w3-show") == -1) {
                                x.className += " w3-show";
                            } else {
                                x.className = x.className.replace(" w3-show", "");
                            }
                        }
                    </script>
                </div>
                <div class="w3-col w3-container" style="width: 10%"></div>
            </div>
        </main>
        <jsp:include page="tempalte/footer.jsp" />
    </body>
</html>
