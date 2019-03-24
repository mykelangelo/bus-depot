<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="lang" value="${sessionScope.get('language')}"/>
<fmt:setLocale value="${lang}"/>
<fmt:setBundle basename="driver-texts" var="texts"/>
<c:set var="driver" value="${requestScope.get('driver')}"/>

<html>
<head>
    <title><fmt:message key="driver_title" bundle="${texts}"/></title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css"
          integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
</head>
<body>

<form action="${pageContext.request.contextPath}/logout" method="get">
    　<input type="submit" class="driver__logout-button" value="<fmt:message key="driver_log-out" bundle="${texts}"/>">
</form>

<h1 class="driver__greeting-message" style="color: indigo">
    <fmt:message key="driver_greeting" bundle="${texts}"/> ${sessionScope.get('userDetails').getEmail()}!
</h1>

<c:if test="${not driver.isAwareOfAssignment()}">
    <h2 class="driver__unaware-message" style="color: firebrick">
        <fmt:message key="driver_new-assignment" bundle="${texts}"/>
    </h2>
    <h3>
        <fmt:message key="driver_press-confirm" bundle="${texts}"/>
    </h3>
    <form action="${pageContext.request.contextPath}/driver" method="post">
        　<input type="submit" class="driver__confirm-button"
                value="<fmt:message key="driver_confirm" bundle="${texts}"/>">
    </form>
</c:if>

<c:if test="${driver.isAwareOfAssignment()}">
    <c:if test="${empty driver.getBus()}">
        <h2 class="driver__vacated-message">
            　<fmt:message key="driver_vacant" bundle="${texts}"/>
        </h2>
    </c:if>
    <c:if test="${not empty driver.getBus()}">
        <table>
            <tr>
                <th>　</th>
                <th>
                    <fmt:message key="driver_your-bus" bundle="${texts}"/>:
                </th>
                <th>　</th>
                <th>
                    <fmt:message key="driver_your-route" bundle="${texts}"/>:
                </th>
            </tr>
            <tr>
                <td>　</td>
                <td class="driver__bus-serial">
                        ${driver.getBus().getSerialNumber()}
                </td>
                <td>　</td>
                <td class="driver__route-name">
                        ${driver.getBus().getRoute().getName()}
                </td>
            </tr>
        </table>
    </c:if>
</c:if>

</body>
</html>
