<%-- 
    Document   : index
    Created on : Feb 21, 2024, 1:19:40 PM
    Author     : im757299
--%>

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
            <a href="<c:url value='Public' />"> Login</a> |
            <a href="<c:url value='Public?action=register' />">Register</a> | 
            <a href="<c:url value='Private' />">Profile</a> | 
            <a href="<c:url value='Private?action=users' />">All Users</a>
        </nav>
        <a href="<c:url value='Teacher' />">Teachers</a>
    </body>
</html>