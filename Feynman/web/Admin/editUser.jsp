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
        <nav>
        <c:choose>
            <c:when test = "${sessionScope.user == null}"> 
                <a href="<c:url value='Public?action=login' />"> Login</a> |   
                <a href="<c:url value='Public?action=register' />">Register</a> | 
            </c:when>
            <c:when test = "${sessionScope.user != null}"> 
                <a href="<c:url value='Private?action=logout' />"> Logout</a> |  
                <a href="<c:url value='Private?action=dashboard' />">Dashboard</a>
            </c:when>
        </c:choose>
        </nav>
        <h2>User Information Edit</h2>
            
        <fieldset>
            <legend>User Roles</legend>
            <form action="Admin" method="post">
                <input type="hidden" name="action" value="edit">  
                <label class="pad_top">User ID:</label>
                <input type="text" name="userID" value="<c:out value='${userToEdit.userID}'/>" readonly><br>
                <label class="pad_top">Username:</label>
                <input type="text" name="username" value="<c:out value='${userToEdit.username}'/>" readonly><br>
                <label class="pad_top">Password:</label>
                <input type="text" name="password"><span>Leave this field blank unless you are changing your password.</span><br>
                <label class="pad_top">First Name:</label>
                <input type="text" name="firstName" value="<c:out value='${userToEdit.firstName}'/>"><br>
                <label class="pad_top">Last Name:</label>
                <input type="text" name="lastName" value="<c:out value='${userToEdit.lastName}'/>"><br>
                <input type="hidden" name="action" value="update">  
                
                <div>
                    <input type="checkbox" value="admin" name="roles" <c:if test="${userToEdit.roles.contains('admin')}">checked</c:if> />
                    <label for="admin">Admin</label>
                </div>

                <div>
                    <input type="checkbox" value="student" name="roles" <c:if test="${userToEdit.roles.contains('student')}">checked</c:if> />
                    <label for="student">Student</label>
                </div>
                
                <div>
                    <input type="checkbox" value="teacher" name="roles" <c:if test="${userToEdit.roles.contains('teacher')}">checked</c:if> />
                    <label for="teacher">Teacher</label>
                </div>
                
                <div>
                    <input type="checkbox" value="parent" name="roles" <c:if test="${userToEdit.roles.contains('parent')}">checked</c:if> />
                    <label for="parent">Parent</label>
                </div>
                <input type="submit" value="Update"><br>
            </form>
        </fieldset>
        <p>${message}</p>
    </body>
</html>
