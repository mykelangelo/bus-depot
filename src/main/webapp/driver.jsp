<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="email" value="${sessionScope.get('email')}"/>
<c:set var="driver" value="${requestScope.get('driver')}"/>

<html>
<head>
    <title>Driver's Haven</title>
</head>
<body>
<h1 class="driver__greetingMessage" style="color: indigo">
    Hi, bus driver with email ${email}!
</h1>

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

</body>
</html>
