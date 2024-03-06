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
        
        <hr>
        <table>
            <caption>Your Associated Students</caption>
            <thead>
                <tr>
                    <th>Username</th>
                    <th>First Name</th>
                    <th>Last Name</th>
                    <th>Student ID</th>
                    <th></th>
                </tr>
            </thead>
            <tbody>
                <tr>
                <th>examplestudent</th>
                <th>Example</th>
                <th>Student</th>
                <th>999</th>
                <th>
                    <form action="" method="post">
                        <input type="hidden" name="action" value="student_detail" />
                        <input type="hidden" name="userID" value="${parent_student.userID}" />
                        <input type="submit" value="Assessments"/>
                    </form>
                </th>
            </tr>
            </tbody>
        </table>
    </body>
</html>
