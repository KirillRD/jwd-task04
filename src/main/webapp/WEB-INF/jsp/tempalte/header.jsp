<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="locale"/>
<header class="w3-display-container">
    <img class="header-image w3-image" src="images/header-background.jpg">
    <div class="w3-display-middle w3-theme-d2 w3-round-large">
        <ul class="w3-ul">
            <li>
                <img class="w3-left logo" src="images/logo.jpg">
                <span class="w3-xxxlarge"><fmt:message key="header.name"/></span><br>
                <span class="w3-large"><fmt:message key="header.library"/></span>
            </li>
        </ul>
    </div>
</header>
