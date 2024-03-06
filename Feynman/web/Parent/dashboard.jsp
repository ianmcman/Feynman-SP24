<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="business.User"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Parent Dashboard</title>
    </head>
    <body>
        <c:import url="/nav.jsp" />
        <h1>Parent Dashboard</h1>
        <p>Username: ${sessionScope.user.username}</p>
        <p>First Name: ${sessionScope.user.firstName}</p>
        <p>Last Name: ${sessionScope.user.lastName}</p>
        <p>Roles: ${String.join(", ", sessionScope.user.roles)}</p>
    </body>
</html>
