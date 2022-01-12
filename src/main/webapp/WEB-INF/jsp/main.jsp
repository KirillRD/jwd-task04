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
<%--        <main>--%>
        <main class="w3-auto w3-container">

<%--            <div class="w3-row">--%>
<%--                <div class="w3-twothird w3-container">--%>
<%--                    <p>--%>
<%--                        <img class="w3-image" src="images/library/1.jpg">--%>
<%--                    </p>--%>
<%--                </div>--%>
<%--                <div class="w3-third w3-container"></div>--%>
<%--            </div>--%>
            <div class="w3-row">
                <div class="w3-twothird w3-container">
                    <p>
                        <img class="w3-image" src="images/library/3.jpg">
                    </p>
                </div>
                <div class="w3-third w3-container">
                    <p class="w3-text-blue-gray w3-center w3-large" style="margin-top: 165px">
                        <fmt:message key="main.about-library.1"/>
                    </p>
                </div>
            </div>

            <div class="w3-row">
                <div class="w3-third w3-container">
                    <p class="w3-text-blue-gray w3-center w3-large" style="margin-top: 165px">
                        <fmt:message key="main.about-library.2"/>
                    </p>
                </div>
                <div class="w3-twothird w3-container">
                    <p>
                        <img class="w3-image" src="images/library/7.jpg">
                    </p>
                </div>
            </div>

            <div class="w3-row">
                <div class="w3-twothird w3-container">
                    <p>
                        <img class="w3-image" src="images/library/8.jpg">
                    </p>
                </div>
                <div class="w3-third w3-container">
                    <p class="w3-text-blue-gray w3-center w3-large" style="margin-top: 175px">
                        <fmt:message key="main.about-library.3"/>
                    </p>
                </div>
            </div>

            <div class="w3-row">
                <div class="w3-third w3-container">
                    <p class="w3-text-blue-gray w3-center w3-large" style="margin-top: 175px">
                        <fmt:message key="main.about-library.4"/>
                    </p>
                </div>
                <div class="w3-twothird w3-container">
                    <p>
                        <img class="w3-image" src="images/library/2.jpg">
                    </p>
                </div>
            </div>

            <div class="w3-row">
                <div class="w3-twothird w3-container">
                    <p>
                        <img class="w3-image" src="images/library/4.jpg">
                    </p>
                </div>
                <div class="w3-third w3-container">
                    <p class="w3-text-blue-gray w3-center w3-large" style="margin-top: 175px">
                        <fmt:message key="main.about-library.5"/>
                    </p>
                </div>
            </div>

            <div class="w3-row">
                <div class="w3-third w3-container">
                    <p class="w3-text-blue-gray w3-center w3-large" style="margin-top: 175px">
                        <fmt:message key="main.about-library.6"/>
                    </p>
                </div>
                <div class="w3-twothird w3-container">
                    <p>
                        <img class="w3-image" src="images/library/5.jpg">
                    </p>
                </div>
            </div>

            <div class="w3-row">
                <div class="w3-twothird w3-container">
                    <p>
                        <img class="w3-image" src="images/library/6.jpg">
                    </p>
                </div>
                <div class="w3-third w3-container">
                    <p class="w3-text-blue-gray w3-center w3-large" style="margin-top: 175px">
                        <fmt:message key="main.about-library.7"/>
                    </p>
                </div>
            </div>


<%--    <div class="w3-content w3-section" style="height:500px">--%>

<%--        <img class="mySlides w3-animate-fading" src="images/library/2.jpg" style="width:100%; object-fit: cover">--%>
<%--        <img class="mySlides w3-animate-fading" src="images/library/3.jpg" style="width:100%; object-fit: cover">--%>
<%--        <img class="mySlides w3-animate-fading" src="images/library/4.jpg" style="width:100%; object-fit: cover">--%>
<%--        <img class="mySlides w3-animate-fading" src="images/library/5.jpg" style="width:100%; object-fit: cover">--%>
<%--    </div>--%>

<%--    <script>--%>
<%--        var myIndex = 0;--%>
<%--        carousel();--%>

<%--        function carousel() {--%>
<%--            var i;--%>
<%--            var x = document.getElementsByClassName("mySlides");--%>
<%--            for (i = 0; i < x.length; i++) {--%>
<%--                x[i].style.display = "none";--%>
<%--            }--%>
<%--            myIndex++;--%>
<%--            if (myIndex > x.length) {myIndex = 1}--%>
<%--            x[myIndex-1].style.display = "block";--%>
<%--            setTimeout(carousel, 9000);--%>
<%--        }--%>
<%--    </script>--%>


        </main>
        <jsp:include page="tempalte/footer.jsp" />
    </body>
</html>
