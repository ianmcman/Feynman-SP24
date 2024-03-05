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
        <h2>Your User Information</h2>
        <form action="Admin" method="post">
            <input type="hidden" name="action" value="update">  
            <label class="pad_top">User ID:</label>
            <input type="text" name="userID" value="<c:out value='${user.userID}'/>" readonly><br>
            <label class="pad_top">Username:</label>
            <input type="text" name="username" value="<c:out value='${user.username}'/>" readonly><br>
            <label class="pad_top">Password:</label>
            <input type="text" name="password"><span>Leave this field blank unless you are changing your password.</span><br>
            <label class="pad_top">First Name:</label>
            <input type="text" name="firstName" value="<c:out value='${user.firstName}'/>"><br>
            <label class="pad_top">Last Name:</label>
            <input type="text" name="lastName" value="<c:out value='${user.lastName}'/>"><br>
            <input type="submit" value="Submit Edits"><br>
        </form>
            
        <fieldset>
            <legend>User Roles</legend>
            <form action="Admin" method="post">
                <input type="hidden" name="action" value="update">  
                
                <div>
                    <input type="checkbox" id="admin" name="roles" <c:if test="${user.roles.contains('admin')}">checked</c:if> />
                    <label for="admin">Admin</label>
                </div>

                <div>
                    <input type="checkbox" id="student" name="roles" <c:if test="${user.roles.contains('student')}">checked</c:if> />
                    <label for="student">Student</label>
                </div>
                
                <div>
                    <input type="checkbox" id="teacher" name="roles" <c:if test="${user.roles.contains('teacher')}">checked</c:if> />
                    <label for="teacher">Teacher</label>
                </div>
                
                <div>
                    <input type="checkbox" id="parent" name="roles" <c:if test="${user.roles.contains('parent')}">checked</c:if> />
                    <label for="parent">Parent</label>
                </div>
                <input type="submit" value="Update"><br>
            </form>
        </fieldset>
    </body>
</html>
