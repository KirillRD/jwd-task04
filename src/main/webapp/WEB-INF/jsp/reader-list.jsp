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
        <main class="w3-container w3-text-blue-grey">
            <div class="w3-auto">
                <p class="w3-center"><b class="w3-xlarge"><fmt:message key="reader-list.label"/></b></p>
                <form class="w3-container w3-card w3-round-large" action="controller" method="get">
                    <input type="hidden" name="command" value="reader-list-page">
                    <div class="w3-row">
                        <div class="w3-third w3-container">
                            <p>
                                <label><fmt:message key="user.filter.last-name"/></label>
                                <input class="input-padding w3-input w3-round" type="text" name="last_name" value="${requestScope.last_name}">
                            </p>
                            <div class="w3-row">
                                <label class="w3-left label-filter"><fmt:message key="user.filter.debtors"/></label>
                                <input class="input-padding w3-input w3-round w3-left w3-check" type="checkbox" name="debtors" ${requestScope.debtors}>
                            </div>
                        </div>
                        <div class="w3-third w3-container">
                            <p></p>
                            <label><fmt:message key="user.filter.reservation-date"/></label>
                            <div class="w3-row w3-margin-bottom">
                                <div class="half-from w3-half w3-container">
                                    <input class="input-padding w3-input w3-round" placeholder="<fmt:message key="user.filter.placeholder.form"/>" type="text" name="reservation_date_from" onfocus="(this.type='date')" onblur="if(this.value==''){this.type='text'}" value="${requestScope.reservation_date_from}">
                                </div>
                                <div class="half-to w3-half w3-container">
                                    <input class="input-padding w3-input w3-round" placeholder="<fmt:message key="user.filter.placeholder.to"/>" type="text" name="reservation_date_to" onfocus="(this.type='date')" onblur="if(this.value==''){this.type='text'}" value="${requestScope.reservation_date_to}">
                                </div>
                            </div>
                            <div class="w3-row">
                                <label class="w3-left label-filter"><fmt:message key="user.filter.reservation"/></label>
                                <input class="input-padding w3-input w3-round w3-left w3-check" type="checkbox" name="reservation" ${requestScope.reservation}>
                            </div>
                        </div>
                        <div class="w3-third w3-container">
<%--                            <p></p>--%>
<%--&lt;%&ndash;                            <div class="w3-col" style="width: 20%">&ndash;%&gt;--%>
<%--&lt;%&ndash;                                <p>&ndash;%&gt;--%>
<%--&lt;%&ndash;                                    <label class="w3-right label-filter"><fmt:message key="user.filter.sort-by"/></label>&ndash;%&gt;--%>
<%--&lt;%&ndash;                                </p>&ndash;%&gt;--%>
<%--&lt;%&ndash;                            </div>&ndash;%&gt;--%>
<%--                            <div class="w3-col" style="width: 80%">--%>
<%--                                <p>--%>
<%--                                    <label class="w3-right label-filter"><fmt:message key="user.filter.sort-by"/></label>--%>
<%--                                    <select id="single-sort" type="text" name="sort">--%>
<%--                                        <option value="last_name ASC" ${requestScope.last_name_ASC}><fmt:message key="user.filter.sort.last-name.a-z"/></option>--%>
<%--                                        <option value="last_name DESC" ${requestScope.last_name_DESC}><fmt:message key="user.filter.sort.last-name.z-a"/></option>--%>
<%--                                        <option value="count_days_debts ASC, last_name ASC" ${requestScope.count_days_debts_ASC__last_name_ASC}><fmt:message key="user.filter.sort.days-debts.a-z"/></option>--%>
<%--                                        <option value="count_days_debts DESC, last_name ASC" ${requestScope.count_days_debts_DESC__last_name_ASC}><fmt:message key="user.filter.sort.days-debts.z-a"/></option>--%>
<%--                                        <option value="min_date_reservation ASC, last_name ASC" ${requestScope.min_date_reservation_ASC__last_name_ASC}><fmt:message key="user.filter.sort.reserv-date.a-z"/></option>--%>
<%--                                        <option value="min_date_reservation DESC, last_name ASC" ${requestScope.min_date_reservation_DESC__last_name_ASC}><fmt:message key="user.filter.sort.reserv-date.z-a"/></option>--%>
<%--                                    </select>--%>
<%--                                    <script>--%>
<%--                                        new SlimSelect({--%>
<%--                                            select: '#single-sort'--%>
<%--                                        })--%>
<%--                                    </script>--%>
<%--                                </p>--%>
<%--                            </div>--%>
                            <p>
                                <label class="w3-left"><fmt:message key="user.filter.sort-by"/></label>
                                <select id="single-sort" type="text" name="sort">
                                    <option value="last_name_ascending" ${requestScope.last_name_ascending}><fmt:message key="user.filter.sort.last-name-ascending"/></option>
                                    <option value="last_name_descending" ${requestScope.last_name_descending}><fmt:message key="user.filter.sort.last-name-descending"/></option>
                                    <option value="days_debt_ascending" ${requestScope.days_debt_ascending}><fmt:message key="user.filter.sort.days-debt-ascending"/></option>
                                    <option value="days_debt_descending" ${requestScope.days_debt_descending}><fmt:message key="user.filter.sort.days-debt-descending"/></option>
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
                <div class="w3-col w3-container" style="width: 15%;"></div>
                <div class="w3-col w3-container" style="width: 70%;">
                    <table class="w3-table w3-striped w3-border w3-hoverable">
                        <tr>
                            <th><fmt:message key="user.name"/></th>
                            <th><fmt:message key="user.address"/></th>
                            <th><fmt:message key="user.phone"/></th>
                            <th><fmt:message key="user.birthday"/>&nbsp;&nbsp;&nbsp;&nbsp;</th>
                            <th><fmt:message key="user.debts"/></th>
                            <th><fmt:message key="reader-list.days"/><br><fmt:message key="reader-list.debt"/></th>
                            <th><fmt:message key="reader-list.reserv-date"/></th>
                            <th><fmt:message key="reader-list.ready"/>/<br><fmt:message key="reader-list.reserv"/></th>
                        </tr>
                        <c:forEach var="reader" items="${requestScope.reader_list}">
                            <tr>
                                <td>
                                    <form action="controller" method="get">
                                        <input type="hidden" name="command" value="go-to-reader-page">
                                        <input type="hidden" name="reader_id" value="${reader.id}">
                                        <button class="link" type="submit"><span>${reader.lastName} ${reader.firstName} ${reader.fatherName}</span></button>
                                    </form>
                                </td>
                                <td>${reader.address}</td>
                                <td>${reader.phone}</td>
                                <td>${reader.birthday}</td>
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
                                    <c:if test="${reader.reservationDebts}">
                                        w3-text-red
                                    </c:if>
                                ">
                                    ${reader.minDateReservation}
                                </td>
                                <td class="
                                    <c:if test="${reader.countReservation == reader.countReservationReady}">
                                        w3-text-green
                                    </c:if>
                                ">
                                    <c:if test="${reader.countReservation > 0}">
                                        ${reader.countReservationReady}/${reader.countReservation}
                                    </c:if>
                                </td>
                            </tr>
                        </c:forEach>
                    </table>
                </div>
                <div class="w3-col w3-container" style="width: 15%;"></div>
            </div>
        </main>
        <jsp:include page="tempalte/footer.jsp" />
    </body>
</html>
