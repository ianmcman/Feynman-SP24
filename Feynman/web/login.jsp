<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <nav>
        <c:choose>
            <c:when test = "${sessionScope.user == null}"> 
                <a href="<c:url value='Public?action=login' />"> Login</a> |    
            </c:when>
            <c:when test = "${sessionScope.user != null}"> 
                <a href="<c:url value='Private?action=logout' />"> Logout</a> |    
            </c:when>
        </c:choose>
            <a href="<c:url value='Public?action=register' />">Register</a> | 
            <a href="<c:url value='Private?action=dashboard' />">Dashboard</a> |
            <a href="<c:url value='Teacher' />">Teachers</a> |
            <a href="<c:url value='Student' />">Student</a>
        </nav>
    <body>
        <div align="center">
            <h1>Test Login Form</h1>
            <form action="<c:url value='Public?action=login' />" method="post">
                <table>
                    <tr>
                        <td>Username</td>
                        <td><input type="text" name="username" /></td>
                    </tr>
                    <tr>
                        <td>Password</td>
                        <td><input type="password" name="password" /></td>
                    </tr>
                </table>
                <input type="submit" value="Submit" />
            </form>
        </div>
    </body>
</html>
