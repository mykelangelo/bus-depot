<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="lang" value="${sessionScope.get('language')}"/>
<fmt:setLocale value="${lang}"/>
<fmt:setBundle basename="admin-texts" var="texts"/>
<c:set var="buses" value="${requestScope.get('buses')}"/>
<c:set var="drivers" value="${requestScope.get('drivers')}"/>
<c:set var="routes" value="${requestScope.get('routes')}"/>
<c:set var="lastSubmitStatusMessage" value="${requestScope.get('lastSubmitStatusMessage')}"/>

<html>
<head>
    <title><fmt:message key="admin_title" bundle="${texts}"/></title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css"
          integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
</head>
<body>

<form action="${pageContext.request.contextPath}/logout" method="get">
    　<input type="submit" class="admin__logout-button" value="<fmt:message key="admin_log-out" bundle="${texts}"/>">
</form>

<c:if test="${empty lastSubmitStatusMessage}">
    <h1 class="admin__greeting-message" style="color: indigo">
        　<fmt:message key="admin_greeting" bundle="${texts}"/> ${sessionScope.get('userDetails').getEmail()}!
    </h1>
</c:if>

<c:if test="${not empty lastSubmitStatusMessage}">
    <h1 class="admin__last-submit-status-message" style="background-color: silver">
            ${lastSubmitStatusMessage}
    </h1>
</c:if>

<form action="${pageContext.request.contextPath}/driver-to-bus" method="post">
    <label>
        　<fmt:message key="admin_select-driver" bundle="${texts}"/>:
        <select class="driver-to-bus__driver-dropdown"
                required
                name="driver-email">
            <option selected disabled value>
                <fmt:message key="admin_drivers" bundle="${texts}"/>
            </option>
            <c:forEach var="driver" items="${drivers}">
                <option class="driver-to-bus__driver-dropdown-option-${driver.getUserEmail().replace('@', '_')}">
                        ${driver.getUserEmail()}
                </option>
            </c:forEach>
        </select>
    </label>
    <label>
        <fmt:message key="admin_select-bus" bundle="${texts}"/>:
        <select class="driver-to-bus__bus-dropdown"
                required
                name="bus-serial">
            <option selected disabled value>
                <fmt:message key="admin_buses" bundle="${texts}"/>
            </option>
            <c:forEach var="bus" items="${buses}">
                <option class="driver-to-bus__bus-dropdown-option-${bus.getSerialNumber()}">
                        ${bus.getSerialNumber()}
                </option>
            </c:forEach>
        </select>
    </label>
    <input type="submit" class="driver-to-bus__submit-button"
           value="<fmt:message key="admin_assign-driver-to-bus" bundle="${texts}"/>">
</form>
<hr>

<form action="${pageContext.request.contextPath}/vacate-driver" method="post">
    <label>
        　<fmt:message key="admin_select-driver" bundle="${texts}"/>:
        <select class="vacate-driver__driver-dropdown"
                required
                name="driver-email">
            <option selected disabled value>
                <fmt:message key="admin_drivers" bundle="${texts}"/>
            </option>
            <c:forEach var="driver" items="${drivers}">
                <option class="vacate-driver__driver-dropdown-option-${driver.getUserEmail().replace('@', '_')}">
                        ${driver.getUserEmail()}
                </option>
            </c:forEach>
        </select>
    </label>
    <input type="submit" class="vacate-driver__submit-button"
           value="<fmt:message key="admin_vacate-driver" bundle="${texts}"/>">
</form>
<hr>

<form action="${pageContext.request.contextPath}/bus-to-route" method="post">
    <label>
        　<fmt:message key="admin_select-bus" bundle="${texts}"/>
        <select class="bus-to-route__bus-dropdown"
                required
                name="bus-serial">
            <option selected disabled value>
                <fmt:message key="admin_buses" bundle="${texts}"/>
            </option>
            <c:forEach var="bus" items="${buses}">
                <option class="bus-to-route__bus-dropdown-option-${bus.getSerialNumber()}">
                        ${bus.getSerialNumber()}
                </option>
            </c:forEach>
        </select>
    </label>
    <label>
        <fmt:message key="admin_select-route" bundle="${texts}"/>:
        <select class="bus-to-route__route-dropdown"
                required
                name="route-name">
            <option selected disabled value>
                <fmt:message key="admin_routes" bundle="${texts}"/>
            </option>
            <c:forEach var="route" items="${routes}">
                <option class="bus-to-route__route-dropdown-option-${route.getName()}">
                        ${route.getName()}
                </option>
            </c:forEach>
        </select>
    </label>
    <input type="submit" class="bus-to-route__submit-button"
           value="<fmt:message key="admin_assign-bus-to-route" bundle="${texts}"/>">
</form>
<hr>

<form action="${pageContext.request.contextPath}/add-route" method="post">
    <label>
        　<fmt:message key="admin_create-route" bundle="${texts}"/>:
        <input name="route-name" class="add-route__route-name"
               placeholder="<fmt:message key="admin_input-route-name" bundle="${texts}"/>" required>
    </label>
    <input type="submit" class="add-route__submit-button"
           value="<fmt:message key="admin_create-route" bundle="${texts}"/>">
</form>
<hr>

<form action="${pageContext.request.contextPath}/delete-route" method="post">
    <label>
        　<fmt:message key="admin_delete-route" bundle="${texts}"/>:
        <select class="delete-route__route-dropdown"
                required
                name="route-name">
            <option selected disabled value>
                <fmt:message key="admin_routes" bundle="${texts}"/>
            </option>
            <c:forEach var="route" items="${routes}">
                <option class="delete-route__route-dropdown-option-${route.getName()}">
                        ${route.getName()}
                </option>
            </c:forEach>
        </select>
    </label>
    <input type="submit" class="delete-route__submit-button"
           value="<fmt:message key="admin_delete-route" bundle="${texts}"/>">
</form>
<hr>

<form action="${pageContext.request.contextPath}/add-bus" method="post">
    <label>
        　<fmt:message key="admin_create-bus" bundle="${texts}"/>:
        <input name="bus-serial" class="add-bus__serial-number"
               placeholder="<fmt:message key="admin_input-serial-number" bundle="${texts}"/>" required>
    </label>
    <input type="submit" class="add-bus__submit-button" value="<fmt:message key="admin_create-bus" bundle="${texts}"/>">
</form>
<hr>

<form action="${pageContext.request.contextPath}/delete-bus" method="post">
    <label>
        　<fmt:message key="admin_delete-bus" bundle="${texts}"/>:
        <select class="delete-bus__bus-dropdown"
                required
                name="bus-serial">
            <option selected disabled value>
                <fmt:message key="admin_buses" bundle="${texts}"/>
            </option>
            <c:forEach var="bus" items="${buses}">
                <option class="delete-bus__bus-dropdown-option-${bus.getSerialNumber()}">
                        ${bus.getSerialNumber()}
                </option>
            </c:forEach>
        </select>
    </label>
    <input type="submit" class="delete-bus__submit-button"
           value="<fmt:message key="admin_delete-bus" bundle="${texts}"/>">
</form>
<hr>

<form action="${pageContext.request.contextPath}/add-driver" method="post">
    <label>
        　<fmt:message key="admin_create-driver" bundle="${texts}"/>:
        <input type="email" name="driver-email" class="add-driver__email"
               placeholder="<fmt:message key="admin_email" bundle="${texts}"/>" required>
    </label>
    <input type="password" name="driver-password" class="add-driver__password"
           placeholder="<fmt:message key="admin_new-password" bundle="${texts}"/>" required>
    <input type="submit" class="add-driver__submit-button"
           value="<fmt:message key="admin_create-driver" bundle="${texts}"/>">
</form>
<hr>

<form action="${pageContext.request.contextPath}/delete-driver" method="post">
    <label>
        　<fmt:message key="admin_delete-driver" bundle="${texts}"/>:
        <select class="delete-driver__driver-dropdown"
                required
                name="driver-email">
            <option selected disabled value>
                <fmt:message key="admin_drivers" bundle="${texts}"/>
            </option>
            <c:forEach var="driver" items="${drivers}">
                <option class="delete-driver__driver-dropdown-option-${driver.getUserEmail().replace('@', '_')}">
                        ${driver.getUserEmail()}
                </option>
            </c:forEach>
        </select>
    </label>
    <input type="submit" class="delete-driver__submit-button"
           value="<fmt:message key="admin_delete-driver" bundle="${texts}"/>">
</form>
<hr>


<table>
    <tr>
        <td>　　</td>
        <td>
            <table>
                <thead><fmt:message key="admin_all-routes" bundle="${texts}"/>:
                </thead>
                <tr>
                    <th>
                        <fmt:message key="admin_route-name" bundle="${texts}"/>
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
                　　<fmt:message key="admin_all-buses" bundle="${texts}"/>:
                </thead>
                <tr>
                    <th>
                        <fmt:message key="admin_serial" bundle="${texts}"/>
                    </th>
                    <th>　</th>
                    <th>
                        <fmt:message key="admin_route" bundle="${texts}"/>
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
                　　<fmt:message key="admin_all-drivers" bundle="${texts}"/>:
                </thead>
                <tr>
                    <th>
                        <fmt:message key="admin_email-capital" bundle="${texts}"/>
                    </th>
                    <th>　</th>
                    <th>
                        <fmt:message key="admin_bus" bundle="${texts}"/>
                    </th>
                    <th>　</th>
                    <th>
                        <fmt:message key="admin_is-aware" bundle="${texts}"/>
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