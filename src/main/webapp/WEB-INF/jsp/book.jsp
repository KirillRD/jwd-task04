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
        <main class="w3-auto w3-container">
            <div class="w3-third w3-container">
                <div class="w3-margin-bottom w3-margin-top w3-display-container book-image-preview">
                    <img class="book-image w3-display-middle" src="images/books/${requestScope.book_info.imageURL}">
                </div>
                <span class="material-icons w3-text-yellow">star</span>
                <span class="book-catalog-span">${requestScope.book_info.rating} (${requestScope.book_info.countRatings})</span>
                <span class="material-icons w3-text-blue-gray">comment</span>
                <span class="book-catalog-span">${requestScope.book_info.countComments}</span>
                <c:if test="${requestScope.book_info.hallFreeInstanceCatalogList.size() > 0}">
                    <span class="material-icons-outlined w3-text-green" title="<fmt:message key="book.free-instances"/>">menu_book</span>
                </c:if>
                <c:if test="${requestScope.book_info.hallFreeInstanceCatalogList.size() == 0}">
                    <span class="material-icons-outlined w3-text-gray" title="<fmt:message key="book.no-free-instances"/>">menu_book</span>
                </c:if>
                <ul>
                    <c:forEach var="instance" items="${requestScope.book_info.hallFreeInstanceCatalogList}">
                        <li>${instance.hallFreeInstances}</li>
                    </c:forEach>
                </ul>
            </div>
            <div class="w3-twothird w3-container">
                <p>
                    <b>${fn:escapeXml(requestScope.book_info.name)}</b>
                    <br>
                    <c:forEach var="author" varStatus="loop" items="${requestScope.book_info.authors}">
                        ${loop.index != 0 ? ',' : ''}
                        ${fn:escapeXml(author)}
                    </c:forEach>
                    <br>
                    <br><fmt:message key="book.publisher"/>: ${fn:escapeXml(requestScope.book_info.publisher)}
                    <br><fmt:message key="book.city"/>: ${fn:escapeXml(requestScope.book_info.city)}
                    <br><fmt:message key="book.publication-year"/>: ${requestScope.book_info.publicationYear}
                    <br><fmt:message key="book.pages"/>: ${requestScope.book_info.pages}
                    <c:if test="${requestScope.book_info.part > 0}">
                        <br><fmt:message key="book.part"/>: ${requestScope.book_info.part}
                    </c:if>
                    <br><fmt:message key="book.type"/>: ${fn:escapeXml(requestScope.book_info.type)}
                    <br><fmt:message key="book.genres"/>:
                    <c:forEach var="genre" varStatus="loop" items="${requestScope.book_info.genres}">
                        ${loop.index != 0 ? ',' : ''}
                        ${fn:escapeXml(genre)}
                    </c:forEach>
                    <br>
                    <c:if test="${requestScope.book_info.isbn != ''}">
                        <br><fmt:message key="book.isbn"/>: ${requestScope.book_info.isbn}
                    </c:if>
                    <c:if test="${requestScope.book_info.issn != ''}">
                        <br><fmt:message key="book.issn"/>: ${requestScope.book_info.issn}
                    </c:if>
                </p>
                <c:if test="${sessionScope.session_user.role != 'ADMIN' && sessionScope.session_user.role != 'LIBRARIAN'}">
                    <form action="controller" method="get">
                        <input type="hidden" name="command" value="go-to-reservation-page">
                        <input type="hidden" name="book_id" value="${requestScope.book_info.id}">
                        <button class="w3-button w3-center w3-margin-bottom w3-margin-top w3-round-large w3-block
                        ${requestScope.book_info.hallFreeInstanceCatalogList.size() == 0 ? 'w3-gray' : 'w3-green'}
                    " style="width: 50%"
                            ${requestScope.book_info.hallFreeInstanceCatalogList.size() == 0 ? 'disabled' : ''}
                        ><fmt:message key="book.reservation-book"/></button>
                    </form>
                </c:if>
                <p class="annotation-p"><fmt:message key="book.annotation"/>: ${fn:escapeXml(requestScope.book_info.annotation)}</p>
            </div>

            <c:if test="${sessionScope.message != null}">
                <div class="w3-row">
                    <div class="w3-panel w3-pale-red w3-leftbar w3-border-red w3-container">
                        <p><fmt:message key="message.${sessionScope.message}"/></p>
                    </div>
                </div>
                <c:remove var="message" scope="session"/>
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

            <c:set var="userCooment" value="true" scope="page"/>
            <c:forEach var="review" items="${requestScope.book_review}">
                <c:if test="${review.userID == sessionScope.session_user.id}">
                    <c:set var="userCooment" value="false" scope="page"/>
                </c:if>
            </c:forEach>

            <div class="w3-container">
                <c:if test="${sessionScope.session_user.role != 'ADMIN' && sessionScope.session_user.role != 'LIBRARIAN'}">
                    <c:if test="${userCooment}">
                        <div class="w3-row">
                            <form action="controller" method="post">
                                <input type="hidden" name="command" value="add-review">
                                <input type="hidden" name="book_id" value="${requestScope.book_info.id}">
                                <h3><fmt:message key="book.review"/></h3>
                                <fieldset class="starability-basic">
                                    <input type="radio" id="rate5" name="rating" value="5" required ${sessionScope.rating == '5' ? 'checked' : ''}/>
                                    <label for="rate5" title="<fmt:message key="book.rating.5"/>"></label>

                                    <input type="radio" id="rate4" name="rating" value="4" required ${sessionScope.rating == '4' ? 'checked' : ''}/>
                                    <label for="rate4" title="<fmt:message key="book.rating.4"/>"></label>

                                    <input type="radio" id="rate3" name="rating" value="3" required ${sessionScope.rating == '3' ? 'checked' : ''}/>
                                    <label for="rate3" title="<fmt:message key="book.rating.3"/>"></label>

                                    <input type="radio" id="rate2" name="rating" value="2" required ${sessionScope.rating == '2' ? 'checked' : ''}/>
                                    <label for="rate2" title="<fmt:message key="book.rating.2"/>"></label>

                                    <input type="radio" id="rate1" name="rating" value="1" required ${sessionScope.rating == '1' ? 'checked' : ''}/>
                                    <label for="rate1" title="<fmt:message key="book.rating.1"/>"></label>
                                    <c:remove var="rating" scope="session"/>
                                </fieldset>
                                <textarea class="w3-input w3-round" maxlength="1000" name="comment" style="resize: vertical">${fn:escapeXml(sessionScope.comment)}</textarea>
                                <c:remove var="comment" scope="session"/>
                                <button class="w3-button w3-right w3-theme w3-margin-bottom w3-margin-top w3-round-large" type="submit"><fmt:message key="book.add-review"/></button>
                            </form>
                        </div>
                    </c:if>
                </c:if>

                <c:if test="${requestScope.book_info.countComments != 0}">
                    <h3><fmt:message key="book.comments"/>:</h3>
                </c:if>
                <c:forEach var="review" items="${requestScope.book_review}">
                    <c:if test="${review.comment != ''}">
                        <div class="w3-row w3-border">
                            <div class="w3-col w3-container w3-center" style="width: 15%">
                                <span>${fn:escapeXml(review.nickname)}</span>
                                <p>
                                    <img src="images/users/${review.imageURL}">
                                </p>
                            </div>
                            <div class="w3-col w3-container" style="width: 85%">
                                <div class="w3-row">
                                    <div class="w3-col" style="width: 18%">
                                        <fieldset class="starability-basic">
                                            <input type="radio" disabled ${review.rating == 5 ? 'checked' : ''}/>
                                            <label title="<fmt:message key="book.rating.5"/>"></label>

                                            <input type="radio" disabled ${review.rating == 4 ? 'checked' : ''}/>
                                            <label title="<fmt:message key="book.rating.4"/>"></label>

                                            <input type="radio" disabled ${review.rating == 3 ? 'checked' : ''}/>
                                            <label title="<fmt:message key="book.rating.3"/>"></label>

                                            <input type="radio" disabled ${review.rating == 2 ? 'checked' : ''}/>
                                            <label title="<fmt:message key="book.rating.2"/>"></label>

                                            <input type="radio" disabled ${review.rating == 1 ? 'checked' : ''}/>
                                            <label title="<fmt:message key="book.rating.1"/>"></label>
                                        </fieldset>
                                    </div>
                                    <div class="w3-col w3-padding-small" style="width: 82%">
                                        <span>${review.date}</span>
                                    </div>
                                </div>

                                <div class="w3-margin-bottom">
                                        ${fn:escapeXml(review.comment)}
                                </div>
                            </div>
                        </div>
                    </c:if>
                </c:forEach>
            </div>
        </main>
        <jsp:include page="tempalte/footer.jsp" />
    </body>
</html>
