<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="buses" value="${requestScope.get('buses')}"/>
<c:set var="drivers" value="${requestScope.get('drivers')}"/>
<c:set var="routes" value="${requestScope.get('routes')}"/>
<c:set var="lastSubmitStatusMessage" value="${requestScope.get('lastSubmitStatusMessage')}"/>

<html>
<head>
    <title>Depot's Administration</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css"
          integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
</head>
<body>

<form action="${pageContext.request.contextPath}/logout" method="get">
    　<input type="submit" class="admin__logout-button" value="Log Out">
</form>

<c:if test="${empty lastSubmitStatusMessage}">
    <h1 class="admin__greeting-message" style="color: indigo">
        　Hi, administrator with email ${sessionScope.get('userDetails').getEmail()}!
    </h1>
</c:if>

<c:if test="${not empty lastSubmitStatusMessage}">
    <h1 class="admin__last-submit-status-message" style="background-color: silver">
            ${lastSubmitStatusMessage}
    </h1>
</c:if>

<form action="${pageContext.request.contextPath}/driver-to-bus" method="post">
    <label>
        　Select driver:
        <select class="driver-to-bus__driver-dropdown"
                required
                name="driver-email">
            <option selected disabled value>
                Drivers
            </option>
            <c:forEach var="driver" items="${drivers}">
                <option class="driver-to-bus__driver-dropdown-option-${driver.getUserEmail().replace('@', '_')}">
                        ${driver.getUserEmail()}
                </option>
            </c:forEach>
        </select>
    </label>
    <label>
        Select bus:
        <select class="driver-to-bus__bus-dropdown"
                required
                name="bus-serial">
            <option selected disabled value>
                Buses
            </option>
            <c:forEach var="bus" items="${buses}">
                <option class="driver-to-bus__bus-dropdown-option-${bus.getSerialNumber()}">
                        ${bus.getSerialNumber()}
                </option>
            </c:forEach>
        </select>
    </label>
    <input type="submit" class="driver-to-bus__submit-button" value="Assign">
</form>
<hr>

<form action="${pageContext.request.contextPath}/vacate-driver" method="post">
    <label>
        　Select driver:
        <select class="vacate-driver__driver-dropdown"
                required
                name="driver-email">
            <option selected disabled value>
                Drivers
            </option>
            <c:forEach var="driver" items="${drivers}">
                <option class="vacate-driver__driver-dropdown-option-${driver.getUserEmail().replace('@', '_')}">
                        ${driver.getUserEmail()}
                </option>
            </c:forEach>
        </select>
    </label>
    <input type="submit" class="vacate-driver__submit-button" value="Vacate">
</form>
<hr>

<form action="${pageContext.request.contextPath}/bus-to-route" method="post">
    <label>
        　Select bus:
        <select class="bus-to-route__bus-dropdown"
                required
                name="bus-serial">
            <option selected disabled value>
                Buses
            </option>
            <c:forEach var="bus" items="${buses}">
                <option class="bus-to-route__bus-dropdown-option-${bus.getSerialNumber()}">
                        ${bus.getSerialNumber()}
                </option>
            </c:forEach>
        </select>
    </label>
    <label>
        Select route:
        <select class="bus-to-route__route-dropdown"
                required
                name="route-name">
            <option selected disabled value>
                Routes
            </option>
            <c:forEach var="route" items="${routes}">
                <option class="bus-to-route__route-dropdown-option-${route.getName()}">
                        ${route.getName()}
                </option>
            </c:forEach>
        </select>
    </label>
    <input type="submit" class="bus-to-route__submit-button" value="Assign">
</form>
<hr>

<form action="${pageContext.request.contextPath}/add-route" method="post">
    <label>
        　Create new route:
        <input name="route-name" class="add-route__route-name" placeholder="input name here, e.g. 89k" required>
    </label>
    <input type="submit" class="add-route__submit-button" value="Add route">
</form>
<hr>

<form action="${pageContext.request.contextPath}/delete-route" method="post">
    <label>
        　Delete route:
        <select class="delete-route__route-dropdown"
                required
                name="route-name">
            <option selected disabled value>
                Routes
            </option>
            <c:forEach var="route" items="${routes}">
                <option class="delete-route__route-dropdown-option-${route.getName()}">
                        ${route.getName()}
                </option>
            </c:forEach>
        </select>
    </label>
    <input type="submit" class="delete-route__submit-button" value="Delete route">
</form>
<hr>

<form action="${pageContext.request.contextPath}/add-bus" method="post">
    <label>
        　Create new bus:
        <input name="bus-serial" class="add-bus__serial-number" placeholder="input serial here, e.g. AI4520KM" required>
    </label>
    <input type="submit" class="add-bus__submit-button" value="Add bus">
</form>
<hr>

<form action="${pageContext.request.contextPath}/delete-bus" method="post">
    <label>
        　Delete bus:
        <select class="delete-bus__bus-dropdown"
                required
                name="bus-serial">
            <option selected disabled value>
                Buses
            </option>
            <c:forEach var="bus" items="${buses}">
                <option class="delete-bus__bus-dropdown-option-${bus.getSerialNumber()}">
                        ${bus.getSerialNumber()}
                </option>
            </c:forEach>
        </select>
    </label>
    <input type="submit" class="delete-bus__submit-button" value="Delete bus">
</form>
<hr>

<form action="${pageContext.request.contextPath}/add-driver" method="post">
    <label>
        　Create new driver:
        <input type="email" name="driver-email" class="add-driver__email" placeholder="email" required>
    </label>
    <input type="password" name="driver-password" class="add-driver__password" placeholder="new password" required>
    <input type="submit" class="add-driver__submit-button" value="Add driver">
</form>
<hr>

<form action="${pageContext.request.contextPath}/delete-driver" method="post">
    <label>
        　Delete driver:
        <select class="delete-driver__driver-dropdown"
                required
                name="driver-email">
            <option selected disabled value>
                Drivers
            </option>
            <c:forEach var="driver" items="${drivers}">
                <option class="delete-driver__driver-dropdown-option-${driver.getUserEmail().replace('@', '_')}">
                        ${driver.getUserEmail()}
                </option>
            </c:forEach>
        </select>
    </label>
    <input type="submit" class="delete-driver__submit-button" value="Delete driver">
</form>
<hr>


<table>
    <tr>
        <td>　　</td>
        <td>
            <table>
                <thead>
                All Routes:
                </thead>
                <tr>
                    <th>
                        Name
                    </th>
                </tr>
                <c:forEach var="route" items="${routes}">
                    <tr>
                        <td class="routes-view__route-name-for-${route.getName()}">
                                ${route.getName()}
                        </td>
                    </tr>
                </c:forEach>
            </table>
        </td>
        <td>　 　</td>
        <td>
            <table>
                <thead>
                　　All Buses:
                </thead>
                <tr>
                    <th>
                        Serial
                    </th>
                    <th>　</th>
                    <th>
                        Route
                    </th>
                </tr>
                <c:forEach var="bus" items="${buses}">
                    <tr>
                        <td class="buses-view__bus-serial-for-${bus.getSerialNumber()}">
                                ${bus.getSerialNumber()}
                        </td>
                        <td>　</td>
                        <td class="buses-view__route-name-for-${bus.getSerialNumber()}">
                                ${bus.getRoute().getName()}
                        </td>
                    </tr>
                </c:forEach>
            </table>
        </td>
        <td>　  　</td>
        <td>
            <table>
                <thead>
                　　All Drivers:
                </thead>
                <tr>
                    <th>
                        Email
                    </th>
                    <th>　</th>
                    <th>
                        Bus
                    </th>
                    <th>　</th>
                    <th>
                        Aware?
                    </th>
                </tr>
                <c:forEach var="driver" items="${drivers}">
                    <tr>
                        <td class="drivers-view__user-email-for-${driver.getUserEmail().replace('@', '_')}">
                                ${driver.getUserEmail()}
                        </td>
                        <th>　</th>
                        <td class="drivers-view__bus-serial-for-${driver.getUserEmail().replace('@', '_')}">
                                ${driver.getBus().getSerialNumber()}
                        </td>
                        <th>　</th>
                        <td class="drivers-view__assignment-awareness-for-${driver.getUserEmail().replace('@', '_')}">
                            <c:if test="${driver.isAwareOfAssignment()}">
                                ${"✅"}
                            </c:if>
                            <c:if test="${not driver.isAwareOfAssignment()}">
                                ${"❌"}
                            </c:if>
                        </td>
                    </tr>
                </c:forEach>
            </table>
        </td>
    </tr>
</table>
</body>
</html>