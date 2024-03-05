<%-- 
    Document   : index
    Created on : Feb 21, 2024, 2:06:17 PM
    Author     : ms461431
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
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
        
        <h1>Teacher Home</h1>
        
        <a href="<c:url value='Teacher?action=qPHome' />"> qPool</a> | 
        <a href="<c:url value='Teacher?action=createQuizHome' />"> Create Quiz</a> | 
        <a href="<c:url value='Teacher?action=addQuestion' />"> Add Question </a>
        
        <c:forEach items="${errors}" var="error">
        <li>${error}</li>
        </c:forEach>
    </body>
</html>
