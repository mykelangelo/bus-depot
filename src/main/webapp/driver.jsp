<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="email" scope="session" type="java.lang.String"/>

<html>
<head>
    <title>Bus Driver's Haven</title>
</head>
<body>
<h1 class="driver__greetingMessage" style="color: indigo">
    Hi, bus driver with email <c:out value="${email}"/>!
</h1>

</body>
</html>
