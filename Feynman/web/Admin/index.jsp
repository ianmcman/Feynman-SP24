<%-- 
    Document   : index
    Created on : Feb 21, 2024, 2:09:58 PM
    Author     : jw536419
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <nav>
            <a href="<c:url value='Public?action=login' />"> Login</a> |
            <a href="<c:url value='Public?action=register' />">Register</a> | 
            <a href="<c:url value='Teacher' />">Teachers</a>
        </nav>
        
        <h1>Admin Home</h1>
    </body>
</html>
