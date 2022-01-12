<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="locale"/>
<nav class="w3-theme w3-container w3-margin-bottom">
    <div class="w3-bar">
        <div class="w3-auto">
            <form action="controller" method="get">
                <input type="hidden" name="command" value="go-to-main-page">
                <button class="w3-bar-item w3-button" type="submit"><fmt:message key="nav.main"/></button>
            </form>
            <form action="controller" method="get">
                <input type="hidden" name="command" value="book-catalog-page">
                <button class="w3-bar-item w3-button" type="submit"><fmt:message key="nav.book-catalog"/></button>
            </form>
            <form action="controller" method="get">
                <input type="hidden" name="command" value="go-to-new-book-catalog-page">
                <button class="w3-bar-item w3-button" type="submit"><fmt:message key="nav.new"/></button>
            </form>
            <form action="controller" method="get">
                <input type="hidden" name="command" value="go-to-popular-book-catalog-page">
                <button class="w3-bar-item w3-button" type="submit"><fmt:message key="nav.popular"/></button>
            </form>
            <form action="controller" method="get">
                <input type="hidden" name="command" value="eee">
                <button class="w3-bar-item w3-button" type="submit"><fmt:message key="nav.rules"/></button>
            </form>
            <form action="controller" method="get">
                <input type="hidden" name="command" value="eee">
                <button class="w3-bar-item w3-button" type="submit"><fmt:message key="nav.contacts"/></button>
            </form>
            <c:if test="${sessionScope.session_user.role == 'LIBRARIAN' || sessionScope.session_user.role == 'ADMIN'}">
                <form action="controller" method="get">
                    <input type="hidden" name="command" value="book-list-page">
                    <button class="w3-bar-item w3-button" type="submit"><fmt:message key="nav.book-list"/></button>
                </form>
                <form action="controller" method="get">
                    <input type="hidden" name="command" value="reader-list-page">
                    <button class="w3-bar-item w3-button" type="submit"><fmt:message key="nav.reader-list"/></button>
                </form>
                <form action="controller" method="get">
                    <input type="hidden" name="command" value="user-list-page">
                    <button class="w3-bar-item w3-button" type="submit"><fmt:message key="nav.user-list"/></button>
                </form>
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
                <form action="controller" method="get">
                    <input type="hidden" name="command" value="go-to-registration-page">
                    <button class="w3-bar-item w3-button w3-right" type="submit"><fmt:message key="nav.sign-up"/></button>
                </form>
                <form action="controller" method="get">
                    <input type="hidden" name="command" value="go-to-authentication-page">
                    <button class="w3-bar-item w3-button w3-right" type="submit"><fmt:message key="nav.sign-in"/></button>
                </form>
            </c:if>
            <c:if test="${sessionScope.session_user != null}">
                <form action="controller" method="get">
                    <input type="hidden" name="command" value="go-to-user-page">
                    <button class="w3-bar-item w3-button w3-right" type="submit">${sessionScope.session_user.nickname}</button>
                </form>
            </c:if>
        </div>
    </div>
</nav>
