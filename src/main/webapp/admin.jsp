<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="driversEmails" value="${requestScope.get('driversEmails')}"/>
<c:set var="busesSerials" value="${requestScope.get('busesSerials')}"/>
<c:set var="routesNames" value="${requestScope.get('routesNames')}"/>
<c:set var="lastSubmitStatusMessage" value="${requestScope.get('lastSubmitStatusMessage')}"/>

<html>
<head>
    <title>Depot's Administration</title>
</head>
<body>

<c:if test="${empty lastSubmitStatusMessage}">
    <h1 class="admin__greetingMessage" style="color: indigo">
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
                name="driver-email">
            <option selected disabled hidden>
                Drivers
            </option>
            <c:forEach var="driver_email" items="${driversEmails}">
                <option class="driver-to-bus__driver-dropdown-option-${driver_email.replace('@', '_')}">
                        ${driver_email}
                </option>
            </c:forEach>
        </select>
    </label>
    <label>
        Select bus:
        <select class="driver-to-bus__bus-dropdown"
                name="bus-serial">
            <option selected disabled hidden>
                Buses
            </option>
            <c:forEach var="bus_serial" items="${busesSerials}">
                <option class="driver-to-bus__bus-dropdown-option-${bus_serial}">
                        ${bus_serial}
                </option>
            </c:forEach>
        </select>
    </label>
    <input type="submit" class="driver-to-bus__submit-button" value="Assign">
</form>
<hr style="color: aliceblue">
<form action="${pageContext.request.contextPath}/vacate-driver" method="post">
    <label>
        Select driver:
        <select class="vacate-driver__driver-dropdown"
                name="driver-email">
            <option selected disabled hidden>
                Drivers
            </option>
            <c:forEach var="driver_email" items="${driversEmails}">
                <option class="vacate-driver__driver-dropdown-option-${driver_email.replace('@', '_')}">
                        ${driver_email}
                </option>
            </c:forEach>
        </select>
    </label>
    <input type="submit" class="vacate-driver__submit-button" value="Vacate">
</form>
<hr style="color: aliceblue">
<form action="${pageContext.request.contextPath}/bus-to-route" method="post">
    <label>
        Select bus:
        <select class="bus-to-route__bus-dropdown"
                name="bus-serial">
            <option selected disabled hidden>
                Buses
            </option>
            <c:forEach var="bus_serial" items="${busesSerials}">
                <option class="bus-to-route__bus-dropdown-option-${bus_serial}">
                        ${bus_serial}
                </option>
            </c:forEach>
        </select>
    </label>
    <label>
        Select route:
        <select class="bus-to-route__route-dropdown"
                name="route-name">
            <option selected disabled hidden>
                Routes
            </option>
            <c:forEach var="route_name" items="${routesNames}">
                <option class="bus-to-route__route-dropdown-option-${route_name}">
                        ${route_name}
                </option>
            </c:forEach>
        </select>
    </label>
    <input type="submit" class="bus-to-route__submit-button" value="Assign">
</form>

</body>
</html>
