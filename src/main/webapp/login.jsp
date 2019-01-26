<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8"%>
<jsp:useBean id="loginErrorMessage" scope="request" class="java.lang.String"/>
<html>
<head>
    <title>Login</title>
</head>
<body>

<h1 style="color: goldenrod">Welcome to The Bus Depot!</h1>

<c:if test="${empty loginErrorMessage}">
    <h3 style="color: blueviolet">Please log in below :-)</h3>
</c:if>

<c:if test="${not empty loginErrorMessage}">
    <div class="login__error-message" style="color: darkred">
        <c:out value="${loginErrorMessage}"/>
    </div>
</c:if>

<form action="login" method="post">
    <table>
        <tr>
            <td>
                <label for="input-email">Email:</label>
            </td>
            <td>
                <%--todo чому без поля name воно падає?--%>
                <input id="input-email" type="email" name="email" class="login__email">
            </td>
        </tr>
        <tr>
            <td>
                <label for="input-password">Password:</label>
            </td>
            <td>
                <input id="input-password" type="password" name="password" class="login__password">
            </td>
        </tr>
    </table>
    <input type="submit" class="login__submit" value="Submit">
</form>

</body>
</html>
