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
                <div class="w3-col w3-container" style="width: 5%"></div>
                <div class="w3-col w3-container w3-text-blue-grey" style="width: 90%">
                    <p class="w3-center"><b class="w3-xlarge"><fmt:message key="edit-user.label"/></b></p>

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

                    <div class="w3-container w3-card w3-round-large">
                        <form class="w3-twothird w3-rightbar" action="controller" method="post">
                            <input type="hidden" name="command" value="edit-user">
                            <c:if test="${param.user_id != null}">
                                <input type="hidden" name="user_id" value="${sessionScope.user.id}">
                            </c:if>
                            <c:if test="${param.reader_id != null}">
                                <input type="hidden" name="reader_id" value="${sessionScope.user.id}">
                            </c:if>

                            <div class="w3-half w3-container">
                                <p>
                                    <label><fmt:message key="user.last-name"/></label>
                                    <input class="input-padding w3-input w3-round" type="text" name="last_name" value="${sessionScope.user.lastName}"
                                    ${sessionScope.session_user.role == 'READER' ? 'disabled' : ''}
                                           required maxlength="30">
                                    <c:if test="${sessionScope.session_user.role == 'READER'}">
                                        <input type="hidden" name="last_name" value="${sessionScope.user.lastName}">
                                    </c:if>
                                </p>
                                <p>
                                    <label><fmt:message key="user.first-name"/></label>
                                    <input class="input-padding w3-input w3-round" type="text" name="first_name" value="${sessionScope.user.firstName}"
                                    ${sessionScope.session_user.role == 'READER' ? 'disabled' : ''}
                                           required maxlength="30">
                                    <c:if test="${sessionScope.session_user.role == 'READER'}">
                                        <input type="hidden" name="first_name" value="${sessionScope.user.firstName}">
                                    </c:if>
                                </p>
                                <p>
                                    <label><fmt:message key="user.father-name"/></label>
                                    <input class="input-padding w3-input w3-round" type="text" name="father_name" value="${sessionScope.user.fatherName}"
                                    ${sessionScope.session_user.role == 'READER' ? 'disabled' : ''}
                                           maxlength="30">
                                    <c:if test="${sessionScope.session_user.role == 'READER'}">
                                        <input type="hidden" name="father_name" value="${sessionScope.user.fatherName}">
                                    </c:if>
                                </p>
                                <p>
                                    <label><fmt:message key="user.address"/></label>
                                    <input class="input-padding w3-input w3-round" type="text" name="address" value="${sessionScope.user.address}" maxlength="100">
                                </p>
                                <p>
                                    <label><fmt:message key="user.phone"/></label>
                                    <input class="input-padding w3-input w3-round" type="text" name="phone" value="${sessionScope.user.phone}" maxlength="20" placeholder="+______________">
                                </p>
                                <c:if test="${sessionScope.session_user.id == sessionScope.user.id}">
                                    <p>
                                        <label><fmt:message key="edit-user.current-password"/></label>
                                        <input class="input-padding w3-input w3-round" type="password" name="current_password">
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
                                                ${sessionScope.user.role == 'READER' ? 'selected' : ''}
                                            ><fmt:message key="edit-user.reader"/></option>
                                            <option value="LIBRARIAN"
                                                ${sessionScope.user.role == 'LIBRARIAN' ? 'selected' : ''}
                                            ><fmt:message key="edit-user.librarian"/></option>
                                            <option value="ADMIN"
                                                ${sessionScope.user.role == 'ADMIN' ? 'selected' : ''}
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
                                    <input class="input-padding w3-input w3-round" type="date" name="birthday" value="${sessionScope.user.birthday}"
                                    ${sessionScope.session_user.role == 'READER' ? 'disabled' : ''}
                                           required min="1900-01-01" max="2099-12-31">
                                    <c:if test="${sessionScope.session_user.role == 'READER'}">
                                        <input type="hidden" name="birthday" value="${sessionScope.user.birthday}">
                                    </c:if>
                                </p>
                                <p>
                                    <label><fmt:message key="user.gender"/></label>
                                    <br>
                                    <input class="w3-radio" type="radio" name="gender" value="M"
                                    ${sessionScope.user.gender == 'M' ? 'checked' : ''}
                                    ${sessionScope.session_user.role == 'READER' ? 'disabled' : ''}
                                           required>
                                    <c:if test="${sessionScope.session_user.role == 'READER'}">
                                        <input type="hidden" name="gender" value="M">
                                    </c:if>
                                    <label><fmt:message key="user.male"/></label>
                                    <input class="w3-radio" type="radio" name="gender" value="F"
                                    ${sessionScope.user.gender == 'F' ? 'checked' : ''}
                                    ${sessionScope.session_user.role == 'READER' ? 'disabled' : ''}
                                           required>
                                    <c:if test="${sessionScope.session_user.role == 'READER'}">
                                        <input type="hidden" name="gender" value="F">
                                    </c:if>
                                    <label><fmt:message key="user.female"/></label>
                                </p>
                                <p>
                                    <label><fmt:message key="user.passport"/></label>
                                    <input class="input-padding w3-input w3-round" type="text" name="passport" value="${sessionScope.user.passport}"
                                    ${sessionScope.session_user.role == 'READER' ? 'disabled' : ''}
                                           maxlength="20">
                                    <c:if test="${sessionScope.session_user.role == 'READER'}">
                                        <input type="hidden" name="passport" value="${sessionScope.user.passport}">
                                    </c:if>
                                </p>
                                <p>
                                    <label><fmt:message key="user.email"/></label>
                                    <input class="input-padding w3-input w3-round" type="email" name="email" value="${sessionScope.user.email}"
                                    ${sessionScope.session_user.id != sessionScope.user.id ? 'disabled' : ''}
                                           required maxlength="45">
                                    <c:if test="${sessionScope.session_user.id != sessionScope.user.id}">
                                        <input type="hidden" name="email" value="${sessionScope.user.email}">
                                    </c:if>
                                </p>
                                <p>
                                    <label><fmt:message key="user.nickname"/></label>
                                    <input class="input-padding w3-input w3-round" type="text" name="nickname" value="${sessionScope.user.nickname}"
                                    ${sessionScope.session_user.id != sessionScope.user.id ? 'disabled' : ''}
                                           required maxlength="20">
                                    <c:if test="${sessionScope.session_user.id != sessionScope.user.id}">
                                        <input type="hidden" name="nickname" value="${sessionScope.user.nickname}">
                                    </c:if>
                                </p>

                                <button class="w3-button w3-right w3-theme w3-round-large w3-margin-bottom" type="submit"><fmt:message key="edit-user.button"/></button>
                            </div>
                        </form>
                        <form class="w3-container w3-third" action="user-image-upload" method="post" enctype="multipart/form-data">
                            <div class="w3-border w3-margin-top w3-display-container image-preview-user" id="imagePreview">
                                <img src="images/users/${sessionScope.user.imageURL}" alt="Image preview" class="w3-display-middle image-preview__image-user">
                                <span class="w3-display-middle image-preview__default-text"></span>
                            </div>
                            <p>
                                <input type="file" name="image" id="inpFile"
                                ${sessionScope.session_user.id != sessionScope.user.id ? 'disabled' : ''}
                                >
                            </p>

                            <button id="save-image" class="w3-button w3-right w3-theme w3-round-large w3-margin-bottom" type="submit" disabled><fmt:message key="edit-user.button-image"/></button>

                            <script>uploadImageUser("images/users/${sessionScope.user.imageURL}")</script>
                        </form>
                    </div>
                    <c:remove var="user" scope="session"/>
                </div>
                <div class="w3-col w3-container" style="width: 5%"></div>
            </div>
        </main>
        <jsp:include page="tempalte/footer.jsp" />
    </body>
</html>
