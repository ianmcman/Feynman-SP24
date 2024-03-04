<%@page import="business.User"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Admin Dashboard</title>
    </head>
    <body>
        <table>
            <tr>
                <th>UserID</th>
                <th>Username</th>
                <th>First Name</th>
                <th>Last Name</th>
                <th>Roles</th>
            </tr>
            <c:forEach items="${users}" var="localUser">
            <tr>
                <td>${localUser.userID}</td>
                <td>${localUser.username}</td>
                <td>${localUser.firstName}</td>
                <td>${localUser.lastName}</td>
                <td>${localUser.roles}</td>
                <td>
                    <form action="Admin" method="get">
                        <input type="hidden" name="action" value="edit" />
                        <input type="hidden" name="userID" value="${localUser.userID}" />
                        <input type="submit" value="Edit"/>
                    </form>
                </td>
            </tr>
            </c:forEach>    
        </table>
    </body>
</html>
