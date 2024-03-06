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
        <nav>
        <c:choose>
            <c:when test = "${sessionScope.user == null}"> 
                <a href="<c:url value='Public?action=login' />"> Login</a> |    
                <a href="<c:url value='Public?action=register' />">Register</a> | 
            </c:when>
            <c:when test = "${sessionScope.user != null}"> 
                <a href="<c:url value='Private?action=logout' />"> Logout</a> |    
            </c:when>
        </c:choose>
            <a href="<c:url value='Private?action=dashboard' />">Dashboard</a>
        </nav>
        <h1>Parent Dashboard</h1>
        <p>Username: ${sessionScope.user.username}</p>
        <p>First Name: ${sessionScope.user.firstName}</p>
        <p>Last Name: ${sessionScope.user.lastName}</p>
        <p>Roles: ${sessionScope.user.roles}</p>
    </body>
</html>
