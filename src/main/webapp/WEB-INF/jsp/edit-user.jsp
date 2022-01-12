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
        <main class="w3-auto w3-container">
            <div class="w3-row">
                <div class="w3-col w3-container" style="width: 15%"></div>
                <div class="w3-col w3-container w3-text-blue-grey" style="width: 70%">
                    <p class="w3-center"><b class="w3-xlarge"><fmt:message key="edit-user.label"/></b></p>
                    <form class="w3-container w3-card w3-round-large" action="controller" method="post">
                        <input type="hidden" name="command" value="edit-user">
                        <c:if test="${param.user_id != null}">
                            <input type="hidden" name="user_id" value="${requestScope.user.id}">
                        </c:if>
                        <c:if test="${param.reader_id != null}">
                            <input type="hidden" name="reader_id" value="${requestScope.user.id}">
                        </c:if>

                        <div class="w3-half w3-container">
                            <p>
                                <label><fmt:message key="user.last-name"/></label>
                                <input class="input-padding w3-input w3-round" type="text" name="last_name" value="${requestScope.user.lastName}"
                                    <c:if test="${sessionScope.session_user.role == 'READER'}">
                                       disabled
                                    </c:if>
                                >
                                <c:if test="${sessionScope.session_user.role == 'READER'}">
                                    <input type="hidden" name="last_name" value="${requestScope.user.lastName}">
                                </c:if>
                            </p>
                            <p>
                                <label><fmt:message key="user.first-name"/></label>
                                <input class="input-padding w3-input w3-round" type="text" name="first_name" value="${requestScope.user.firstName}"
                                    <c:if test="${sessionScope.session_user.role == 'READER'}">
                                       disabled
                                    </c:if>
                                >
                                <c:if test="${sessionScope.session_user.role == 'READER'}">
                                    <input type="hidden" name="first_name" value="${requestScope.user.firstName}">
                                </c:if>
                            </p>
                            <p>
                                <label><fmt:message key="user.father-name"/></label>
                                <input class="input-padding w3-input w3-round" type="text" name="father_name" value="${requestScope.user.fatherName}"
                                    <c:if test="${sessionScope.session_user.role == 'READER'}">
                                       disabled
                                    </c:if>
                                >
                                <c:if test="${sessionScope.session_user.role == 'READER'}">
                                    <input type="hidden" name="father_name" value="${requestScope.user.fatherName}">
                                </c:if>
                            </p>
                            <p>
                                <label><fmt:message key="user.address"/></label>
                                <input class="input-padding w3-input w3-round" type="text" name="address" value="${requestScope.user.address}">
                            </p>
                            <p>
                                <label><fmt:message key="user.phone"/></label>
                                <input class="input-padding w3-input w3-round" type="text" name="phone" value="${requestScope.user.phone}">
                            </p>
                            <p>
                                <label><fmt:message key="user.email"/></label>
                                <input class="input-padding w3-input w3-round" type="text" name="email" value="${requestScope.user.email}"
                                    <c:if test="${sessionScope.session_user.id != requestScope.user.id}">
                                       disabled
                                    </c:if>
                                >
                                <c:if test="${sessionScope.session_user.id != requestScope.user.id}">
                                    <input type="hidden" name="email" value="${requestScope.user.email}">
                                </c:if>
                            </p>
                            <p>
                                <label><fmt:message key="user.nickname"/></label>
                                <input class="input-padding w3-input w3-round" type="text" name="nickname" value="${requestScope.user.nickname}"
                                    <c:if test="${sessionScope.session_user.id != requestScope.user.id}">
                                       disabled
                                    </c:if>
                                >
                                <c:if test="${sessionScope.session_user.id != requestScope.user.id}">
                                    <input type="hidden" name="nickname" value="${requestScope.user.nickname}">
                                </c:if>
                            </p>
                            <c:if test="${sessionScope.session_user.id == requestScope.user.id}">
                                <p>
                                    <label><fmt:message key="edit-user.old-password"/></label>
                                    <input class="input-padding w3-input w3-round" type="password" name="old_password">
                                </p>
                                <p>
                                    <label><fmt:message key="edit-user.new-password"/></label>
                                    <input class="input-padding w3-input w3-round" type="password" name="new_password">
                                </p>
                                <p>
                                    <label><fmt:message key="edit-user.repeated-new-password"/></label>
                                    <input class="input-padding w3-input w3-round" type="password" name="repeated_new_password">
                                </p>
                            </c:if>
                            <c:if test="${sessionScope.session_user.role == 'ADMIN'}">
                                <p>
                                    <label><fmt:message key="edit-user.role"/></label>
                                    <select id="single-role" type="text" name="role">
                                        <option value="READER"
                                                <c:if test="${requestScope.user.role == 'READER'}">
                                                    selected
                                                </c:if>
                                        ><fmt:message key="edit-user.reader"/></option>
                                        <option value="LIBRARIAN"
                                                <c:if test="${requestScope.user.role == 'LIBRARIAN'}">
                                                    selected
                                                </c:if>
                                        ><fmt:message key="edit-user.librarian"/></option>
                                        <option value="ADMIN"
                                                <c:if test="${requestScope.user.role == 'ADMIN'}">
                                                    selected
                                                </c:if>
                                        ><fmt:message key="edit-user.admin"/></option>
                                    </select>
                                    <script>
                                        new SlimSelect({
                                            select: '#single-role'
                                        })
                                    </script>
                                </p>
                            </c:if>
                        </div>
                        <div class="w3-half w3-container">
                            <p>
                                <label><fmt:message key="user.birthday"/></label>
                                <input class="input-padding w3-input w3-round" type="date" name="birthday" value="${requestScope.user.birthday}"
                                    <c:if test="${sessionScope.session_user.role == 'READER'}">
                                       disabled
                                    </c:if>
                                >
                                <c:if test="${sessionScope.session_user.role == 'READER'}">
                                    <input type="hidden" name="birthday" value="${requestScope.user.birthday}">
                                </c:if>
                            </p>
                            <p>
                                <label><fmt:message key="user.gender"/></label>
                                <br>
                                <input class="w3-radio" type="radio" name="gender" value="M"
                                    <c:if test="${requestScope.user.gender == 'M'}">
                                        checked
                                    </c:if>
                                    <c:if test="${sessionScope.session_user.role == 'READER'}">
                                       disabled
                                    </c:if>
                                >
                                <c:if test="${sessionScope.session_user.role == 'READER'}">
                                    <input type="hidden" name="gender" value="M">
                                </c:if>
                                <label><fmt:message key="user.male"/></label>
                                <input class="w3-radio" type="radio" name="gender" value="F"
                                    <c:if test="${requestScope.user.gender == 'F'}">
                                        checked
                                    </c:if>
                                    <c:if test="${sessionScope.session_user.role == 'READER'}">
                                       disabled
                                    </c:if>
                                >
                                <c:if test="${sessionScope.session_user.role == 'READER'}">
                                    <input type="hidden" name="gender" value="F">
                                </c:if>
                                <label><fmt:message key="user.female"/></label>
                            </p>
                            <p>
                                <label><fmt:message key="user.passport"/></label>
                                <input class="input-padding w3-input w3-round" type="text" name="passport" value="${requestScope.user.passport}"
                                    <c:if test="${sessionScope.session_user.role == 'READER'}">
                                       disabled
                                    </c:if>
                                >
                                <c:if test="${sessionScope.session_user.role == 'READER'}">
                                    <input type="hidden" name="passport" value="${requestScope.user.passport}">
                                </c:if>
                            </p>
                            <div class="w3-border w3-margin-bottom w3-margin-top w3-display-container image-preview-user" id="imagePreview">
                                <img src="${requestScope.user.imageURL}" alt="Image preview" class="w3-display-middle image-preview__image-user">
                                <span class="w3-display-middle image-preview__default-text"></span>
                            </div>
                            <input type="file" name="inpFile" id="inpFile"
                                <c:if test="${sessionScope.session_user.id != requestScope.user.id}">
                                   disabled
                                </c:if>
                            >
                            <script>uploadImageUser("${requestScope.user.imageURL}")</script>

                            <button class="w3-button w3-right w3-theme w3-round-large w3-margin-bottom w3-margin-top" type="submit"><fmt:message key="edit-user.button"/></button>
                        </div>
                    </form>
                </div>
                <div class="w3-col w3-container" style="width: 15%"></div>
            </div>
        </main>
        <jsp:include page="tempalte/footer.jsp" />
    </body>
</html>
