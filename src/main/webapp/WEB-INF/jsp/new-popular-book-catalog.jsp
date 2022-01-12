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
        <main class="w3-auto w3-container w3-text-blue-grey">
            <p class="w3-center">
                <b class="w3-xlarge">
                    <c:if test="${param.command == 'go-to-new-book-catalog-page'}">
                        <fmt:message key="new-popular-book-catalog.new-books"/>
                    </c:if>
                    <c:if test="${param.command == 'go-to-popular-book-catalog-page'}">
                        <fmt:message key="new-popular-book-catalog.popular-books"/>
                    </c:if>
                </b>
            </p>
            <div class="w3-row-padding w3-margin-top">
                <c:forEach var="bookInfo" items="${requestScope.book_catalog}">
                    <div class="w3-third w3-small">
                        <div class="w3-margin-bottom w3-round-large book-catalog-background-color book-catalog-main w3-hover-shadow">
                            <div class="w3-row book-catalog-half">
                                <div class="book-catalog-half-left w3-half">
                                    <form action="controller" method="get">
                                        <input type="hidden" name="command" value="go-to-book-page">
                                        <input type="hidden" name="book_id" value="${bookInfo.id}">
                                        <button class="link" type="submit"><span><img class="book-catalog-image" src="${bookInfo.imageURL}"></span></button>
                                    </form>
                                        <%--                                    <img class="book-catalog-image" src="images/books/${bookInfo.imageURL}">--%>
                                    <span class="material-icons w3-text-yellow">star</span>
                                    <span class="book-catalog-span">${bookInfo.rating} (${bookInfo.countRatings})</span>
                                    <span class="material-icons w3-text-blue-gray">comment</span>
                                    <span class="book-catalog-span">${bookInfo.countComments}</span>
                                    <c:if test="${bookInfo.hallFreeInstanceCatalogList.size() > 0}">
                                        <span class="material-icons-outlined w3-text-green" title="<fmt:message key="book.free-instances"/>">menu_book</span>
                                    </c:if>
                                    <c:if test="${bookInfo.hallFreeInstanceCatalogList.size() == 0}">
                                        <span class="material-icons-outlined w3-text-gray" title="<fmt:message key="book.no-free-instances"/>">menu_book</span>
                                    </c:if>
                                </div>
                                <div class="book-catalog-half-right w3-half">
                                    <form action="controller" method="get">
                                        <input type="hidden" name="command" value="go-to-book-page">
                                        <input type="hidden" name="book_id" value="${bookInfo.id}">
                                        <button class="link" type="submit"><span><b>${bookInfo.name}</b></span></button>
                                    </form>
                                    <c:forEach var="author" varStatus="loop" items="${bookInfo.authors}">
                                        <c:if test="${loop.index != 0}">
                                            ,
                                        </c:if>
                                        ${author}
                                    </c:forEach>
                                    <br>
                                    <br><fmt:message key="book.publisher"/>: ${bookInfo.publisher}
                                    <br><fmt:message key="book.publication-year"/>: ${bookInfo.publicationYear}
                                    <br><fmt:message key="book.pages"/>: ${bookInfo.pages}
                                    <c:if test="${bookInfo.part > 0}">
                                        <br><fmt:message key="book.part"/>: ${bookInfo.part}
                                    </c:if>
                                    <br>Type: ${bookInfo.type}
                                    <br>Genres:
                                    <c:forEach var="genre" varStatus="loop" items="${bookInfo.genres}">
                                        <c:if test="${loop.index != 0}">
                                            ,
                                        </c:if>
                                        ${genre}
                                    </c:forEach>
                                    <br>
                                    <c:if test="${bookInfo.isbn != ''}">
                                        <br><fmt:message key="book.isbn"/>: ${bookInfo.isbn}
                                    </c:if>
                                    <c:if test="${bookInfo.issn != ''}">
                                        <br><fmt:message key="book.issn"/>: ${bookInfo.issn}
                                    </c:if>
                                </div>
                            </div>
                            <div class="w3-col annotation-div">
                                <p class="w3-small annotation-p">${bookInfo.annotation}</p>
                            </div>
                        </div>
                    </div>
                </c:forEach>
            </div>
        </main>
        <jsp:include page="tempalte/footer.jsp" />
    </body>
</html>
