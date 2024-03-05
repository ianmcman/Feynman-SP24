<%-- 
    Document   : StudentResults
    Created on : Feb 21, 2024, 2:32:54 PM
    Author     : mh749813
--%>
<%@page import="java.util.ArrayList"%>
<%@page import="business.Attempt"%>
<%@page import="java.util.List"%>

<% List<Attempt> studentAttempts = (List<Attempt>)request.getAttribute("studentAttempts"); %>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
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
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Assessment Results</title>
    </head>
    <body>
        <h1>Assessment results for {student)</h1>
        <ul>
            <c:forEach items="${studentAttempts}" var="attempt">
                <li>
                    Attempt ID: ${attempt.attemptID} |
                    Score: ${attempt.attemptScore * 100}% |
                    Date: ${attempt.attemptDate} 
                </li>
            </c:forEach>
        </ul>
    </body>
</html>
