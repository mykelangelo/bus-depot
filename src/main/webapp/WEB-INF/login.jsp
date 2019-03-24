<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="lang" value="${sessionScope.get('language')}"/>
<fmt:setLocale value="${lang}"/>
<fmt:setBundle basename="login-texts" var="texts"/>
<c:set var="displayLoginError" value="${requestScope.get('displayLoginErrorMessage')}"/>

<html>
<head>
    <title><fmt:message key="login_title" bundle="${texts}"/></title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css"
          integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
</head>
<body>

<form action="${pageContext.request.contextPath}/localize" method="post">
    <label>
        <fmt:message key="login_language" bundle="${texts}"/>:
        <select class="login__language-dropdown" name='language' onchange='this.form.submit()'>
            <option class="login__language-dropdown-option-english"
                    <c:if test="${'en'.equals(lang)}">selected</c:if>
                    value="en">🇺🇸<%--US flag emoji--%></option>
            <option class="login__language-dropdown-option-ukrainian"
                    <c:if test="${'uk'.equals(lang)}">selected</c:if>
                    value="uk">🇺🇦<%--UA flag emoji--%></option>
            <option class="login__language-dropdown-option-russian"
                    <c:if test="${'ru'.equals(lang)}">selected</c:if>
                    value="ru">🇷🇺<%--RU flag emoji--%></option>
        </select>
    </label>
    <noscript><input type="submit" value="<fmt:message key="login_select-language" bundle="${texts}"/>:"></noscript>
</form>

<h1 class="login__greeting-message" style="color: goldenrod">
    <fmt:message key="login_greeting-message" bundle="${texts}"/>
</h1>

<c:if test="${empty displayLoginError}">
    <h3 style="color: blueviolet"><fmt:message key="login_please-login-message" bundle="${texts}"/></h3>
</c:if>

<c:if test="${not empty displayLoginError}">
    <div class="login__error-message" style="color: darkred">
        <fmt:message key="login_error-message" bundle="${texts}"/>
    </div>
</c:if>

<form action="${pageContext.request.contextPath}/login" method="post">
    <table>
        <tr>
            <td>
                <label for="input-email"><fmt:message key="login_email" bundle="${texts}"/>:</label>
            </td>
            <td>
                <input id="input-email" type="email" name="email" class="login__email">
            </td>
        </tr>
        <tr>
            <td>
                <label for="input-password"><fmt:message key="login_password" bundle="${texts}"/>:</label>
            </td>
            <td>
                <input id="input-password" type="password" name="password" class="login__password">
            </td>
        </tr>
        <tr>
            <td>
                <input type="submit" class="login__submit" value="<fmt:message key="login_log-in" bundle="${texts}"/>">
            </td>
        </tr>
    </table>
</form>

</body>
</html>
