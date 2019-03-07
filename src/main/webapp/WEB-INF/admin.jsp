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

<c:if test="${empty lastSubmitStatusMessage}">
    <h1 class="admin__greeting-message" style="color: indigo">
        　Hi, administrator with email ${sessionScope.get('email')}!
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
            <option selected disabled hidden value>
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
            <option selected disabled hidden value>
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
            <option selected disabled hidden value>
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
            <option selected disabled hidden value>
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
            <option selected disabled hidden value>
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
<table>
    <tr>
        <td>　　</td>
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
        <td>　   　</td>
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