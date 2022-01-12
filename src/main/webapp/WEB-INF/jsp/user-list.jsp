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
                <p class="w3-center"><b class="w3-xlarge"><fmt:message key="user-list.label"/></b></p>
                <form class="w3-container w3-card w3-round-large" action="controller" method="get">
                    <input type="hidden" name="command" value="user-list-page">
                    <div class="w3-row">
                        <div class="w3-third w3-container">
                            <p>
                                <label><fmt:message key="user.last-name"/></label>
                                <input class="input-padding w3-input w3-round" type="text" name="last_name" value="${requestScope.last_name}">
                            </p>
                        </div>
                        <div class="w3-third w3-container">
                            <p>
                                <label><fmt:message key="user.email"/></label>
                                <input class="input-padding w3-input w3-round" type="text" name="email" value="${requestScope.email}">
                            </p>
                            <p>
                                <label class="w3-left"><fmt:message key="user.filter.sort-by"/></label>
                                <select id="single-sort" type="text" name="sort">
                                    <option value="last_name_ascending" ${requestScope.last_name_ascending}><fmt:message key="user.filter.sort.last-name-ascending"/></option>
                                    <option value="last_name_descending" ${requestScope.last_name_descending}><fmt:message key="user.filter.sort.last-name-descending"/></option>
                                    <option value="role_ascending" ${requestScope.role_ascending}><fmt:message key="user.filter.sort.role-ascending"/></option>
                                    <option value="role_descending" ${requestScope.role_descending}><fmt:message key="user.filter.sort.role-descending"/></option>
                                </select>
                                <script>
                                    new SlimSelect({
                                        select: '#single-sort'
                                    })
                                </script>
                            </p>
                        </div>
                        <div class="w3-third w3-container">
                            <p>
                                <label><fmt:message key="user.nickname"/></label>
                                <input class="input-padding w3-input w3-round" type="text" name="nickname" value="${requestScope.nickname}">
                            </p>
                            <button class="w3-button w3-margin-top w3-right w3-theme w3-round-large" type="submit"><fmt:message key="user.filter.button"/></button>
                        </div>
                    </div>
                </form>
            </div>

            <div class="w3-row w3-padding-top-24">
                <div class="w3-col w3-container" style="width: 15%;"></div>
                <div class="w3-col w3-container" style="width: 70%;">
                    <table class="w3-table w3-striped w3-border w3-hoverable">
                        <tr>
                            <th></th>
                            <th></th>
                            <th><fmt:message key="user.name"/></th>
                            <th><fmt:message key="user.email"/></th>
                            <th><fmt:message key="user.nickname"/></th>
                            <th><fmt:message key="user.address"/></th>
                            <th><fmt:message key="user.phone"/></th>
                            <th><fmt:message key="user.birthday"/>&nbsp;&nbsp;&nbsp;&nbsp;</th>
                        </tr>
                        <c:forEach var="user" items="${requestScope.user_list}">
                            <tr>
                                <td>
                                    <c:if test="${user.role == 'ADMIN'}">
                                        <span class="material-icons-outlined w3-text-red" title="Admin">manage_accounts</span>
                                    </c:if>
                                    <c:if test="${user.role == 'LIBRARIAN'}">
                                        <span class="material-icons-outlined w3-text-green" title="Librarian">person</span>
                                    </c:if>
                                </td>
                                <td>
                                    <form action="controller" method="get">
                                        <input type="hidden" name="command" value="go-to-user-page">
                                        <input type="hidden" name="user_id" value="${user.id}">
                                        <button class="link" type="submit"><span><img class="book-catalog-image" src="${user.imageURL}"></span></button>
                                    </form>
                                </td>
                                <td>
                                    <form action="controller" method="get">
                                        <input type="hidden" name="command" value="go-to-user-page">
                                        <input type="hidden" name="user_id" value="${user.id}">
                                        <button class="link" type="submit"><span>${user.lastName} ${user.firstName} ${user.fatherName}</span></button>
                                    </form>
                                </td>
                                <td>${user.email}</td>
                                <td>${user.nickname}</td>
                                <td>${user.address}</td>
                                <td>${user.phone}</td>
                                <td>${user.birthday}</td>
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
