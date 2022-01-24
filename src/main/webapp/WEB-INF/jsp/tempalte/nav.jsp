<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="locale"/>
<nav class="w3-theme w3-container w3-margin-bottom">
    <div class="w3-bar">
        <div class="w3-auto">
            <a class="w3-bar-item w3-button" href="controller?command=go-to-main-page"><fmt:message key="nav.main"/></a>
            <a class="w3-bar-item w3-button" href="controller?command=book-catalog-page"><fmt:message key="nav.book-catalog"/></a>
            <a class="w3-bar-item w3-button" href="controller?command=go-to-new-book-catalog-page"><fmt:message key="nav.new"/></a>
            <a class="w3-bar-item w3-button" href="controller?command=go-to-popular-book-catalog-page"><fmt:message key="nav.popular"/></a>
            <a class="w3-bar-item w3-button" href="controller?command=go-to-rule-page"><fmt:message key="nav.rules"/></a>
            <a class="w3-bar-item w3-button" href="controller?command=go-to-contact-page"><fmt:message key="nav.contacts"/></a>

            <c:if test="${sessionScope.session_user.role == 'LIBRARIAN' || sessionScope.session_user.role == 'ADMIN'}">
                <a class="w3-bar-item w3-button" href="controller?command=book-list-page"><fmt:message key="nav.book-list"/></a>
                <a class="w3-bar-item w3-button" href="controller?command=reader-list-page"><fmt:message key="nav.reader-list"/></a>
                <a class="w3-bar-item w3-button" href="controller?command=user-list-page"><fmt:message key="nav.user-list"/></a>
                <div class="w3-dropdown-hover w3-left">
                    <button class="w3-button nav-dropdown">
                        <fmt:message key="nav.dictionaries"/>
                        <span class="material-icons-outlined">arrow_drop_down</span>
                    </button>
                    <div class="w3-dropdown-content w3-bar-block w3-theme-l4">
                        <a class="w3-bar-item w3-button w3-theme" href="controller?command=author-page"><fmt:message key="nav.dictionaries.authors"/></a>
                        <a class="w3-bar-item w3-button w3-theme" href="controller?command=genre-page"><fmt:message key="nav.dictionaries.genres"/></a>
                        <a class="w3-bar-item w3-button w3-theme" href="controller?command=publisher-page"><fmt:message key="nav.dictionaries.publishers"/></a>
                        <a class="w3-bar-item w3-button w3-theme" href="controller?command=type-page"><fmt:message key="nav.dictionaries.types"/></a>
                    </div>
                </div>
            </c:if>

            <div class="w3-dropdown-hover w3-right">
                <button class="w3-button nav-dropdown">
                    ${sessionScope.locale == 'en' ? 'EN' : ''}
                    ${sessionScope.locale == 'ru' ? 'РУ' : ''}
                    <span class="material-icons-outlined">arrow_drop_down</span>
                </button>
                <div class="w3-dropdown-content w3-bar-block w3-theme-l4">
                    <form action="controller" method="get">
                        <input type="hidden" name="command" value="change-locale">
                        <button class="w3-bar-item w3-button w3-theme" type="submit" name="locale" value="en" style="width: 56px">EN</button>
                        <button class="w3-bar-item w3-button w3-theme" type="submit" name="locale" value="ru" style="width: 56px">РУ</button>
                    </form>
                </div>
            </div>

            <c:if test="${sessionScope.session_user == null}">
                <a class="w3-bar-item w3-button w3-right" href="controller?command=go-to-registration-page"><fmt:message key="nav.sign-up"/></a>
                <a class="w3-bar-item w3-button w3-right" href="controller?command=go-to-authentication-page"><fmt:message key="nav.sign-in"/></a>
            </c:if>
            <c:if test="${sessionScope.session_user != null}">
                <a class="w3-bar-item w3-button w3-right" href="controller?command=go-to-user-page">${sessionScope.session_user.nickname}</a>
            </c:if>
        </div>
    </div>
</nav>
