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
                <p class="w3-center"><b class="w3-xlarge"><fmt:message key="book-list.label"/></b></p>
                <form class="w3-container w3-card w3-round-large" action="controller" method="get">
                    <input type="hidden" name="command" value="book-list-page">
                    <div class="w3-row">
                        <div class="w3-third w3-container">
                            <p>
                                <label><fmt:message key="book.filter.book-name"/></label>
                                <input class="input-padding w3-input w3-round" type="text" name="name" value="${requestScope.name}">
                            </p>
                            <p>
                                <label><fmt:message key="book.filter.authors"/></label>
                                <select id="multiple-authors" multiple type="text" name="authors">
                                    <c:forEach var="author" items="${requestScope.authors}">
                                        <option value="${author.id}"
                                                <c:forEach var="saved_author" items="${requestScope.saved_authors}">
                                                    <c:if test="${author.id == saved_author}">
                                                        selected
                                                    </c:if>
                                                </c:forEach>
                                        >${author.lastName} ${author.firstName} ${author.fatherName}</option>
                                    </c:forEach>
                                </select>
                                <script>
                                    new SlimSelect({
                                        select: '#multiple-authors'
                                    })
                                </script>
                            </p>
                            <p>
                                <label><fmt:message key="book.filter.genres"/></label>
                                <select id="multiple-genres" multiple type="text" name="genres">
                                    <c:forEach var="genre" items="${requestScope.genres}">
                                        <option value="${genre.id}"
                                                <c:forEach var="saved_genre" items="${requestScope.saved_genres}">
                                                    <c:if test="${genre.id == saved_genre}">
                                                        selected
                                                    </c:if>
                                                </c:forEach>
                                        >${genre.name}</option>
                                    </c:forEach>
                                </select>
                                <script>
                                    new SlimSelect({
                                        select: '#multiple-genres'
                                    })
                                </script>
                            </p>
                            <div class="w3-row">
                                <label class="w3-left label-filter"><fmt:message key="book.filter.free-instances"/></label>
                                <input class="input-padding w3-input w3-round w3-left w3-check" type="checkbox" name="free_instances" ${requestScope.free_instances}>
                            </div>
                        </div>
                        <div class="w3-third w3-container">
                            <p>
                                <label><fmt:message key="book.filter.publisher"/></label>
                                <select id="single-publisher" type="text" name="publisher">
                                    <option data-placeholder="true"></option>
                                    <c:forEach var="publisher" items="${requestScope.publishers}">
                                        <option value="${publisher.id}"
                                                <c:if test="${publisher.id == requestScope.saved_publisher}">
                                                    selected
                                                </c:if>
                                        >${publisher.name}</option>
                                    </c:forEach>
                                </select>
                                <script>
                                    new SlimSelect({
                                        select: '#single-publisher',
                                        allowDeselect: true
                                    })
                                </script>
                            </p>
                            <label><fmt:message key="book.filter.publication-year"/></label>
                            <div class="w3-row">
                                <div class="half-from w3-half w3-container">
                                    <input class="input-padding w3-input w3-round" placeholder="<fmt:message key="book.filter.placeholder.from"/>" type="text" name="publication_year_from" value="${requestScope.publication_year_from}">
                                </div>
                                <div class="half-to w3-half w3-container">
                                    <input class="input-padding w3-input w3-round" placeholder="<fmt:message key="book.filter.placeholder.to"/>" type="text" name="publication_year_to" value="${requestScope.publication_year_to}">
                                </div>
                            </div>
                            <p></p>
                            <label><fmt:message key="book.filter.pages"/></label>
                            <div class="w3-row w3-margin-bottom">
                                <div class="half-from w3-half w3-container">
                                    <input class="input-padding w3-input w3-round" placeholder="<fmt:message key="book.filter.placeholder.from"/>" type="text" name="pages_from" value="${requestScope.pages_from}">
                                </div>
                                <div class="half-to w3-half w3-container">
                                    <input class="input-padding w3-input w3-round" placeholder="<fmt:message key="book.filter.placeholder.to"/>" type="text" name="pages_to" value="${requestScope.pages_to}">
                                </div>
                            </div>
                            <div class="w3-half">
                                <label class="w3-right label-filter"><fmt:message key="book.filter.sort-by"/></label>
                            </div>
                            <div class="w3-half">
                                <select id="single-sort" type="text" name="sort">
                                    <option value="name_ascending" ${requestScope.name_ascending}><fmt:message key="book.filter.sort.name-ascending"/></option>
                                    <option value="name_descending" ${requestScope.name_descending}><fmt:message key="book.filter.sort.name-descending"/></option>
                                </select>
                                <script>
                                    new SlimSelect({
                                        select: '#single-sort'
                                    })
                                </script>
                            </div>
                        </div>
                        <div class="w3-third w3-container">
                            <p>
                                <label><fmt:message key="book.filter.isbn"/></label>
                                <input class="input-padding w3-input w3-round" type="text" name="isbn" value="${requestScope.isbn}">
                            </p>
                            <p>
                                <label><fmt:message key="book.filter.issn"/></label>
                                <input class="input-padding w3-input w3-round" type="text" name="issn" value="${requestScope.issn}">
                            </p>
                            <p>
                                <label><fmt:message key="book.filter.type"/></label>
                                <select id="single-type" type="text" name="type">
                                    <option data-placeholder="true"></option>
                                    <c:forEach var="type" items="${requestScope.types}">
                                        <option value="${type.id}"
                                                <c:if test="${type.id == requestScope.saved_type}">
                                                    selected
                                                </c:if>
                                        >${type.name}</option>
                                    </c:forEach>
                                </select>
                                <script>
                                    new SlimSelect({
                                        select: '#single-type',
                                        allowDeselect: true
                                    })
                                </script>
                            </p>
                            <button class="w3-button w3-right w3-theme w3-margin-bottom w3-round-large" type="submit"><fmt:message key="book.filter.button"/></button>
                        </div>
                    </div>
                </form>

                <form action="controller" method="get">
                    <input type="hidden" name="command" value="go-to-add-edit-book-page">
                    <button class="w3-button w3-right w3-theme w3-margin-bottom w3-margin-top w3-round-large" type="submit"><fmt:message key="book-list.button.add-book"/></button>
                </form>
            </div>

            <div class="w3-row">
                <div class="w3-col w3-container" style="width: 15%;"></div>
                <div class="w3-col w3-container" style="width: 70%;">
                    <table class="w3-table w3-striped w3-border w3-hoverable">
                        <tr>
                            <th></th>
                            <th><fmt:message key="book.name"/></th>
                            <th><fmt:message key="book.authors"/></th>
                            <th><fmt:message key="book.publisher"/></th>
                            <th><fmt:message key="book.year"/></th>
                            <th><fmt:message key="book.part"/></th>
                            <th><fmt:message key="book.pages"/></th>
                            <th><fmt:message key="book.genres"/></th>
                            <th></th>
                            <th></th>
                            <th></th>
                        </tr>
                        <c:forEach var="bookInfo" items="${requestScope.book_catalog}">
                            <tr>
                                <td>
                                    <form action="controller" method="get">
                                        <input type="hidden" name="command" value="go-to-book-page">
                                        <input type="hidden" name="book_id" value="${bookInfo.id}">
                                        <button class="link" type="submit"><span><img class="book-catalog-image" src="${bookInfo.imageURL}"></span></button>
                                    </form>
                                </td>
                                <td>
                                    <form action="controller" method="get">
                                        <input type="hidden" name="command" value="go-to-book-page">
                                        <input type="hidden" name="book_id" value="${bookInfo.id}">
                                        <button class="link" type="submit"><span>${bookInfo.name}</span></button>
                                    </form>
                                </td>
                                <td>
                                    <c:forEach var="author" varStatus="loop" items="${bookInfo.authors}">
                                        <c:if test="${loop.index != 0}">
                                            ,
                                        </c:if>
                                        ${author}
                                    </c:forEach>
                                </td>
                                <td>${bookInfo.publisher}</td>
                                <td>${bookInfo.publicationYear}</td>
                                <td>
                                    <c:if test="${bookInfo.part > 0}">
                                        ${bookInfo.part}
                                    </c:if>
                                </td>
                                <td>${bookInfo.pages}</td>
                                <td>
                                    <c:forEach var="genre" varStatus="loop" items="${bookInfo.genres}">
                                        <c:if test="${loop.index != 0}">
                                            ,
                                        </c:if>
                                        ${genre}
                                    </c:forEach>
                                </td>
                                <td>
                                    <form action="controller" method="get">
                                        <input type="hidden" name="command" value="instance-page">
                                        <input type="hidden" name="book_id" value="${bookInfo.id}">
                                        <button class="link" type="submit">
                                            <c:if test="${bookInfo.hallFreeInstanceCatalogList.size() > 0}">
                                                <span class="material-icons-outlined w3-text-green" title="<fmt:message key="book-list.button.edit-instances"/>">menu_book</span>
                                            </c:if>
                                            <c:if test="${bookInfo.hallFreeInstanceCatalogList.size() == 0}">
                                                <span class="material-icons-outlined w3-text-gray" title="<fmt:message key="book-list.button.edit-instances"/>">menu_book</span>
                                            </c:if>
                                        </button>
                                    </form>
                                </td>
                                <td>
                                    <form action="controller" method="get">
                                        <input type="hidden" name="command" value="go-to-add-edit-book-page">
                                        <input type="hidden" name="book_id" value="${bookInfo.id}">
                                        <button class="link" type="submit">
                                            <span class="material-icons-outlined w3-text-blue-gray" title="<fmt:message key="book-list.button.edit-book"/>">mode_edit</span>
                                        </button>
                                    </form>
                                </td>
                                <td>
                                    <form action="controller" method="post" onsubmit="return confirm(<fmt:message key="delete.submit.message"/>);">
                                        <input type="hidden" name="command" value="delete-book">
                                        <input type="hidden" name="book_id" value="${bookInfo.id}">
                                        <button class="link" type="submit"
                                            <c:if test="${bookInfo.bookIsUsed}">
                                                disabled
                                            </c:if>
                                        ><span class="material-icons-outlined
                                            <c:if test="${bookInfo.bookIsUsed}">
                                                w3-text-gray
                                            </c:if>
                                            <c:if test="${!bookInfo.bookIsUsed}">
                                                w3-text-red
                                            </c:if>"
                                        title="<fmt:message key="book-list.button.delete-book"/>">clear</span></button>
                                    </form>
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
