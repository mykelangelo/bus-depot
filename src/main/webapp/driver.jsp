<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>Bus Driver's Haven</title>
</head>
<body>
<h1 class="driver__greetingMessage" style="color: indigo">
    Hi, bus driver with email ${sessionScope.get('email')}!
</h1>

</body>
</html>
