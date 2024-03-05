<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <div align="center">
            <h1>Test Register Form</h1>
            <form action="<c:url value='Public?action=register' />" method="post">
                <table>
                    <tr>
                        <td>First Name</td>
                        <td><input type="text" name="firstName" /></td>
                    </tr>
                    <tr>
                        <td>Last Name</td>
                        <td><input type="text" name="lastName" /></td>
                    </tr>
                    <tr>
                        <td>Username</td>
                        <td><input type="text" name="username" /></td>
                    </tr>
                    <tr>
                        <td>Password</td>
                        <td><input type="password" name="password" /></td>
                    </tr>
                    <tr>
                        <td>
                            <input type="checkbox" value="parent" name="roles" />
                            <label for="parent">Parent</label>
                        </td>
                        <td>
                            <input type="checkbox" value="student" name="roles" />
                            <label for="student">Student</label>
                        </td>
                        <td>
                            <input type="checkbox" value="teacher" name="roles" />
                            <label for="teacher">Teacher</label>
                        </td>
                    </tr>
                </table>
                <input type="submit" value="Submit" />
            </form>
            <ul>
            <c:forEach items="${errors}" var="error">
                <li>${error}</li>
            </c:forEach>
        </ul>
        </div>
    </body>
</html>
