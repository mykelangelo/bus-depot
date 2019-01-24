<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="email" scope="session" type="java.lang.String"/>

<html>
<head>
    <title>Index</title>
</head>
<body>
<h1 class="landing__greetingMessage" style="color: indigo">
    Hi, <c:out value="${email}"/>
</h1>

</body>
</html>
