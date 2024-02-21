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
        </nav>
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