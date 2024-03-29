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
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Assessment Results</title>
    </head>
    <body>
        <c:import url="/nav.jsp" />
        <h1>Assessment results</h1>
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
