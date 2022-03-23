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
        <main class="w3-container w3-text-blue-grey">
            <div class="w3-auto">
                <p class="w3-center"><b class="w3-xlarge"><fmt:message key="reader-list.label"/></b></p>
                <form class="w3-container w3-card w3-round-large" action="controller" method="get">
                    <input type="hidden" name="command" value="reader-list-page">
                    <div class="w3-row">
                        <div class="w3-third w3-container">
                            <p>
                                <label><fmt:message key="user.filter.last-name"/></label>
                                <input class="input-padding w3-input w3-round" type="text" name="last_name" value="${fn:escapeXml(requestScope.last_name)}">
                            </p>
                            <div class="w3-row">
                                <div class="w3-half">
                                    <input class="input-padding w3-input w3-round w3-left w3-check" type="checkbox" name="reading_hall" ${requestScope.reading_hall}>
                                    <label class="w3-left label-filter"><fmt:message key="user.filter.reading-hall"/></label>
                                </div>
                                <div class="w3-half">
                                    <input class="input-padding w3-input w3-round w3-left w3-check" type="checkbox" name="rental" ${requestScope.rental}>
                                    <label class="w3-left label-filter"><fmt:message key="user.filter.rental"/></label>
                                </div>
                            </div>
                        </div>
                        <div class="w3-third w3-container">
                            <p></p>
                            <label><fmt:message key="user.filter.reservation-date"/></label>
                            <div class="w3-row w3-margin-bottom">
                                <div class="half-from w3-half w3-container">
                                    <input class="input-padding w3-input w3-round" placeholder="<fmt:message key="user.filter.placeholder.form"/>" type="text" name="reservation_date_from" onfocus="(this.type='date')" onblur="if(this.value==''){this.type='text'}" value="${fn:escapeXml(requestScope.reservation_date_from)}" min="1900-01-01" max="2099-12-31">
                                </div>
                                <div class="half-to w3-half w3-container">
                                    <input class="input-padding w3-input w3-round" placeholder="<fmt:message key="user.filter.placeholder.to"/>" type="text" name="reservation_date_to" onfocus="(this.type='date')" onblur="if(this.value==''){this.type='text'}" value="${fn:escapeXml(requestScope.reservation_date_to)}" min="1900-01-01" max="2099-12-31">
                                </div>
                            </div>
                            <div class="w3-row">
                                <div class="w3-half">
                                    <input class="input-padding w3-input w3-round w3-left w3-check" type="checkbox" name="debtors" ${requestScope.debtors}>
                                    <label class="w3-left label-filter"><fmt:message key="user.filter.debtors"/></label>
                                </div>
                                <div class="w3-half">
                                    <input class="input-padding w3-input w3-round w3-left w3-check" type="checkbox" name="reservation" ${requestScope.reservation}>
                                    <label class="w3-left label-filter"><fmt:message key="user.filter.reservation"/></label>
                                </div>
                            </div>
                        </div>
                        <div class="w3-third w3-container">
                            <p>
                                <label class="w3-left"><fmt:message key="user.filter.sort-by"/></label>
                                <select id="single-sort" type="text" name="sort">
                                    <option value="last_name_ascending" ${requestScope.last_name_ascending}><fmt:message key="user.filter.sort.last-name-ascending"/></option>
                                    <option value="last_name_descending" ${requestScope.last_name_descending}><fmt:message key="user.filter.sort.last-name-descending"/></option>
                                    <option value="days_debt_ascending" ${requestScope.days_debt_ascending}><fmt:message key="user.filter.sort.days-debt-ascending"/></option>
                                    <option value="days_debt_descending" ${requestScope.days_debt_descending}><fmt:message key="user.filter.sort.days-debt-descending"/></option>
                                    <option value="days_rental_ascending" ${requestScope.days_rental_ascending}><fmt:message key="user.filter.sort.days-rental-ascending"/></option>
                                    <option value="days_rental_descending" ${requestScope.days_rental_descending}><fmt:message key="user.filter.sort.days-rental-descending"/></option>
                                    <option value="reservation_date_ascending" ${requestScope.reservation_date_ascending}><fmt:message key="user.filter.sort.reserv-date-ascending"/></option>
                                    <option value="reservation_date_descending" ${requestScope.reservation_date_descending}><fmt:message key="user.filter.sort.reserv-date-descending"/></option>
                                </select>
                                <script>
                                    new SlimSelect({
                                        select: '#single-sort'
                                    })
                                </script>
                            </p>
                            <button class="w3-button w3-right w3-theme w3-margin-bottom w3-round-large" type="submit"><fmt:message key="user.filter.button"/></button>
                        </div>
                    </div>
                </form>
            </div>

            <div class="w3-row w3-padding-top-24">
                <div class="w3-col w3-container" style="width: 10%;"></div>
                <div class="w3-col w3-container" style="width: 80%;">
                    <table class="w3-table w3-striped w3-border w3-hoverable">
                        <tr>
                            <th><fmt:message key="user.name"/></th>
                            <th><fmt:message key="user.address"/></th>
                            <th><fmt:message key="user.phone"/></th>
                            <th><fmt:message key="user.birthday"/>&nbsp;&nbsp;&nbsp;&nbsp;</th>
                            <th><fmt:message key="reader-list.reading-hall-1"/><br><fmt:message key="reader-list.reading-hall-2"/></th>
                            <th><fmt:message key="reader-list.rental"/></th>
                            <th><fmt:message key="reader-list.rental-days-1"/><br><fmt:message key="reader-list.rental-days-2"/></th>
                            <th><fmt:message key="user.debts"/></th>
                            <th><fmt:message key="reader-list.days"/><br><fmt:message key="reader-list.debt"/></th>
                            <th><fmt:message key="reader-list.reserv-date"/></th>
                            <th><fmt:message key="reader-list.ready"/>/<br><fmt:message key="reader-list.reserv"/></th>
                        </tr>
                        <c:forEach var="reader" items="${requestScope.reader_list}">
                            <tr>
                                <td>
                                    <a class="w3-hover-text-blue w3-text-dark-grey" href="controller?command=go-to-reader-page&reader_id=${reader.id}">
                                        ${fn:escapeXml(reader.lastName)} ${fn:escapeXml(reader.firstName)} ${fn:escapeXml(reader.fatherName)}
                                    </a>
                                </td>
                                <td>${fn:escapeXml(reader.address)}</td>
                                <td>${reader.phone}</td>
                                <td>${reader.birthday}</td>
                                <td>
                                    <c:if test="${reader.countReadingHallBooks > 0}">
                                        ${reader.countReadingHallBooks}
                                    </c:if>
                                </td>
                                <td>
                                    <c:if test="${reader.countRental > 0}">
                                        ${reader.countRental}
                                    </c:if>
                                </td>
                                <td>
                                    <c:if test="${reader.countDaysRental > 0}">
                                        ${reader.countDaysRental}
                                    </c:if>
                                </td>
                                <td class="w3-text-red">
                                    <c:if test="${reader.countDebts > 0}">
                                        ${reader.countDebts}
                                    </c:if>
                                </td>
                                <td class="w3-text-red">
                                    <c:if test="${reader.countDaysDebts > 0}">
                                        ${reader.countDaysDebts}
                                    </c:if>
                                </td>
                                <td class="
                                    ${reader.reservationDebts ? 'w3-text-red' : ''}
                                ">
                                    ${reader.minDateReservation}
                                </td>
                                <td class="
                                    ${reader.countReservation == reader.countReservationReady ? 'w3-text-green' : ''}
                                ">
                                    <c:if test="${reader.countReservation > 0}">
                                        ${reader.countReservationReady}/${reader.countReservation}
                                    </c:if>
                                </td>
                            </tr>
                        </c:forEach>
                    </table>

                    <div class="w3-center w3-section">
                        <div class="w3-bar">
                            <a href="controller${requestScope.url}&page=${requestScope.page > 1 ? requestScope.page-1 : 1}" class="w3-bar-item w3-button w3-theme-l4">&laquo;</a>
                            <c:forEach var="page_number" begin="1" end="${requestScope.pages_count}">
                                <a href="controller${requestScope.url}&page=${page_number}" class="w3-bar-item w3-button ${page_number == requestScope.page ? 'w3-theme' : 'w3-theme-l4'}">${page_number}</a>
                            </c:forEach>
                            <a href="controller${requestScope.url}&page=${requestScope.page < requestScope.pages_count ? requestScope.page+1 : requestScope.pages_count}" class="w3-bar-item w3-button w3-theme-l4">&raquo;</a>
                        </div>
                    </div>
                </div>
                <div class="w3-col w3-container" style="width: 10%;"></div>
            </div>
        </main>
        <jsp:include page="tempalte/footer.jsp" />
    </body>
</html>
