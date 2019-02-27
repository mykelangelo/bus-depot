<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="email" value="${sessionScope.get('email')}"/>
<c:set var="driver" value="${requestScope.get('driver')}"/>

<html>
<head>
    <title>Driver's Haven</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css"
          integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
</head>
<body>

<h1 class="driver__greeting-message" style="color: indigo">
    Hi, bus driver with email ${email}!
</h1>

<c:if test="${not driver.isAwareOfAssignment()}">
    <h2 class="driver__unaware-message" style="color: firebrick">
        You have been assigned to a new bus and/or route. Press 'Confirm' button below to see you new assignment.
    </h2>
    <form action="${pageContext.request.contextPath}/driver" method="post">
        <input type="submit" class="driver__confirm-button" value="Confirm">
    </form>
</c:if>

<c:if test="${driver.isAwareOfAssignment()}">
    <c:if test="${empty driver.getBus()}">
        <h2 class="driver__vacated-message">
            You're free of any work currently. Have fun on your vacation!
        </h2>
    </c:if>
    <c:if test="${not empty driver.getBus()}">
        <table>
            <thead>
            Info for ${driver.getUserEmail()}:
            </thead>
            <tr>
                <th>
                    Your Bus:
                </th>
                <th>
                    Your Route:
                </th>
            </tr>
            <tr>
                <td class="driver__bus-serial">
                        ${driver.getBus().getSerialNumber()}
                </td>
                <td class="driver__route-name">
                        ${driver.getBus().getRoute().getName()}
                </td>
            </tr>
            <tfoot> Come again soon!</tfoot>
        </table>
    </c:if>
</c:if>

</body>
</html>
