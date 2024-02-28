<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
    <head>
        <title>TODO supply a title</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
    </head>
    <body>
        <nav>
            <a href="<c:url value='Public?action=login' />"> Login</a> |
            <a href="<c:url value='Public?action=register' />">Register</a> | 
            <a href="<c:url value='Teacher' />">Temp Teachers</a> |
            <a href="<c:url value='Private?action=dashboard' />">Dashboard</a>
        </nav>

    </body>
</html>