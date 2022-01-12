<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="locale"/>
<!DOCTYPE html>
<html>
    <head>
        <title>Rodny kut</title>
        <jsp:include page="tempalte/links.jsp" />
    </head>
    <body class="w3-theme-l4">
        <jsp:include page="tempalte/header.jsp" />
        <jsp:include page="tempalte/nav.jsp" />
        <main class="w3-auto w3-container">
            <div class="w3-row">
                <div class="w3-col w3-container" style="width: 15%"></div>
                <div class="w3-col w3-container w3-text-blue-grey" style="width: 70%">
                    <c:if test="${requestScope.book.id != null}">
                        <p class="w3-center"><b class="w3-xlarge"><fmt:message key="add-edit-book.edit-book"/></b></p>
                    </c:if>
                    <c:if test="${requestScope.book.id == null}">
                        <p class="w3-center"><b class="w3-xlarge"><fmt:message key="add-edit-book.add-book"/></b></p>
                    </c:if>
                    <form class="w3-container w3-card w3-round-large" action="controller" method="post">
                        <input type="hidden" name="command" value="add-edit-book">
                        <c:if test="${requestScope.book != null}">
                            <input type="hidden" name="book_id" value="${requestScope.book.id}">
                        </c:if>
                        <div class="w3-half w3-container">
                            <p>
                                <label><fmt:message key="book.book-name"/></label>
                                <input class="input-padding w3-input w3-round" type="text" name="name" value="${requestScope.book.name}">
                            </p>
                            <p>
                                <label><fmt:message key="book.authors"/></label>
                                <select id="multiple-authors" multiple type="text" name="authors">
                                    <c:forEach var="author" items="${requestScope.authors}">
                                        <option value="${author.id}"
                                                <c:forEach var="saved_author" items="${requestScope.book.authorsID}">
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
                                <label><fmt:message key="book.publisher"/></label>
                                <select id="single-publisher" type="text" name="publisher">
                                    <option data-placeholder="true"></option>
                                    <c:forEach var="publisher" items="${requestScope.publishers}">
                                        <option value="${publisher.id}"
                                                <c:if test="${publisher.id == requestScope.book.publisherID}">
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
                            <p>
                                <label><fmt:message key="book.publication-year"/></label>
                                <input class="input-padding w3-input w3-round" type="text" name="publication_year" value="${requestScope.book.publicationYear}">
                            </p>
                            <p>
                                <label><fmt:message key="book.part"/></label>
                                <input class="input-padding w3-input w3-round" type="number" name="part" value="${requestScope.book.part}">
                            </p>
                            <p>
                                <label><fmt:message key="book.pages"/></label>
                                <input class="input-padding w3-input w3-round" type="number" name="pages" value="${requestScope.book.pages}">
                            </p>
                            <p>
                                <label><fmt:message key="book.genres"/></label>
                                <select id="multiple-genres" multiple type="text" name="genres">
                                    <c:forEach var="genre" items="${requestScope.genres}">
                                        <option value="${genre.id}"
                                                <c:forEach var="saved_genre" items="${requestScope.book.genresID}">
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
                            <p>
                                <label><fmt:message key="book.type"/></label>
                                <select id="single-type" type="text" name="type">
                                    <option data-placeholder="true"></option>
                                    <c:forEach var="type" items="${requestScope.types}">
                                        <option value="${type.id}"
                                                <c:if test="${type.id == requestScope.book.typeID}">
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
                            <p>
                                <label><fmt:message key="book.price"/></label>
                                <input class="input-padding w3-input w3-round" type="number" name="price" value="${requestScope.book.price}">
                            </p>
                        </div>
                        <div class="w3-half w3-container">
                            <br>
                            <c:if test="${requestScope.book == null}">
                                <c:set var="imageURL" value="images/books/default_image_book.jpg" scope="page"/>
                            </c:if>
                            <c:if test="${requestScope.book != null}">
                                <c:set var="imageURL" value="${requestScope.book.imageURL}" scope="page"/>
                            </c:if>
                            <div class="w3-border w3-margin-bottom w3-margin-top w3-display-container image-preview-book" id="imagePreview">
                                <img src="${imageURL}" alt="Image preview" class="w3-display-middle image-preview__image-book">
                                <span class="w3-display-middle image-preview__default-text"></span>
                            </div>
                            <input type="file" name="inpFile" id="inpFile">
                            <script>uploadImageBook("${imageURL}")</script>
                            <p>
                                <label><fmt:message key="book.isbn"/></label>
                                <input class="input-padding w3-input w3-round" type="text" name="isbn" value="${requestScope.book.isbn}">
                            </p>
                            <p>
                                <label><fmt:message key="book.issn"/></label>
                                <input class="input-padding w3-input w3-round" type="text" name="issn" value="${requestScope.book.issn}">
                            </p>
                        </div>
                        <div class="w3-col w3-container">
                            <label><fmt:message key="book.annotation"/></label>
                            <textarea class="w3-input w3-round book-annotation" maxlength="1500" name="annotation">${requestScope.book.annotation}</textarea>
                            <button class="w3-button w3-right w3-theme w3-margin-bottom w3-margin-top w3-round-large" type="submit"><fmt:message key="add-edit-book.button"/></button>
                        </div>
                    </form>
                </div>
                <div class="w3-col w3-container" style="width: 15%"></div>
            </div>
        </main>
        <jsp:include page="tempalte/footer.jsp" />
    </body>
</html>
