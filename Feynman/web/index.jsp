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
            <a href="<c:url value='Teacher' />">Teachers</a>
            <a href="<c:url value='Student' />">Student</a>
        </nav>
        <div>  
            <p>UserID: <c:out value='${user.userID}'/></p>
            <p>Username: <c:out value='${user.username}'/></p>
            <p>FirstName: <c:out value='${user.firstName}'/></p>
            <p>LastName: <c:out value='${user.lastName}'/></p>
            <p>Password: <c:out value='${user.password}'/></p>
            <p>Roles: <c:out value='${user.roles}'/></p>
        </div>
    </body>
</html>