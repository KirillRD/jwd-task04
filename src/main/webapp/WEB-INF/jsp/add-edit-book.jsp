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
<%--                    <c:if test="${sessionScope.book.id != 0 && sessionScope.book != null}">--%>
                    <c:if test="${param.book_id != null}">
                        <p class="w3-center"><b class="w3-xlarge"><fmt:message key="add-edit-book.edit-book"/></b></p>
                    </c:if>
<%--                    <c:if test="${sessionScope.book.id == 0 || sessionScope.book == null}">--%>
                    <c:if test="${param.book_id == null}">
                        <p class="w3-center"><b class="w3-xlarge"><fmt:message key="add-edit-book.add-book"/></b></p>
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

                    <form class="w3-container w3-card w3-round-large" action="controller" method="post">
                        <input type="hidden" name="command" value="add-edit-book">
<%--                        <c:if test="${sessionScope.book.id != 0 && sessionScope.book != null}">--%>
<%--                            <input type="hidden" name="book_id" value="${sessionScope.book.id}">--%>
<%--                        </c:if>--%>
                        <c:if test="${param.book_id != null}">
                            <input type="hidden" name="book_id" value="${sessionScope.book.id}">
                        </c:if>
                        <div class="w3-half w3-container">
                            <p>
                                <label><fmt:message key="book.book-name"/></label>
                                <input class="input-padding w3-input w3-round" type="text" name="name" value="${sessionScope.book.name}" required maxlength="100">
                            </p>
                            <p>
                                <label><fmt:message key="book.authors"/></label>
                                <select id="multiple-authors" multiple type="text" name="authors">
                                    <c:forEach var="author" items="${requestScope.authors}">
                                        <option value="${author.id}"
                                                <c:forEach var="saved_author" items="${sessionScope.book.authorsID}">
                                                    ${author.id == saved_author ? 'selected' : ''}
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
                                                ${publisher.id == sessionScope.book.publisherID ? 'selected' : ''}
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
                                <input class="input-padding w3-input w3-round" type="number" name="publication_year" value="${sessionScope.book.publicationYear}" required min="1900" max="2100">
                            </p>
                            <p>
                                <label><fmt:message key="book.part"/></label>
                                <input class="input-padding w3-input w3-round" type="number" name="part" value="${sessionScope.book.part != 0 ? sessionScope.book.part : ''}" min="0">
                            </p>
                            <p>
                                <label><fmt:message key="book.pages"/></label>
                                <input class="input-padding w3-input w3-round" type="number" name="pages" value="${sessionScope.book.pages}" required min="1">
                            </p>
                            <p>
                                <label><fmt:message key="book.genres"/></label>
                                <select id="multiple-genres" multiple type="text" name="genres">
                                    <c:forEach var="genre" items="${requestScope.genres}">
                                        <option value="${genre.id}"
                                                <c:forEach var="saved_genre" items="${sessionScope.book.genresID}">
                                                    ${genre.id == saved_genre ? 'selected' : ''}
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
                                            ${type.id == sessionScope.book.typeID ? 'selected' : ''}
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
                                <input class="input-padding w3-input w3-round" name="price" value="${sessionScope.book.price}" required>
                            </p>
                        </div>
                        <div class="w3-half w3-container">
                            <br>
                            <c:if test="${param.book_id != null}">
                                <c:set var="imageURL" value="${sessionScope.book.imageURL}" scope="page"/>
                            </c:if>
                            <c:if test="${param.book_id == null}">
                                <c:set var="imageURL" value="images/books/default_image_book.jpg" scope="page"/>
                            </c:if>
                            <div class="w3-border w3-margin-bottom w3-margin-top w3-display-container image-preview-book" id="imagePreview">
                                <img src="${imageURL}" alt="Image preview" class="w3-display-middle image-preview__image-book">
                                <span class="w3-display-middle image-preview__default-text"></span>
                            </div>
                            <input type="file" name="inpFile" id="inpFile">
                            <script>uploadImageBook("${imageURL}")</script>
                            <p>
                                <label><fmt:message key="book.isbn"/></label>
                                <input class="input-padding w3-input w3-round" type="text" name="isbn" value="${sessionScope.book.isbn}">
                            </p>
                            <p>
                                <label><fmt:message key="book.issn"/></label>
                                <input class="input-padding w3-input w3-round" type="text" name="issn" value="${sessionScope.book.issn}">
                            </p>
                        </div>
                        <div class="w3-col w3-container">
                            <label><fmt:message key="book.annotation"/></label>
                            <textarea class="w3-input w3-round book-annotation" maxlength="1500" name="annotation">${sessionScope.book.annotation}</textarea>
                            <button class="w3-button w3-right w3-theme w3-margin-bottom w3-margin-top w3-round-large" type="submit"><fmt:message key="add-edit-book.button"/></button>
                        </div>
                    </form>
                    <c:remove var="book" scope="session"/>
                </div>
                <div class="w3-col w3-container" style="width: 15%"></div>
            </div>
        </main>
        <jsp:include page="tempalte/footer.jsp" />
    </body>
</html>
