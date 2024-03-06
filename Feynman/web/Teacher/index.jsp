<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="business.User"%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Teacher Dashboard</title>
    </head>
    <body>
        <c:import url="/nav.jsp" />
        
        <h1>Teacher Home</h1>
        
        <a href="<c:url value='Teacher?action=qPHome' />"> Question Pools</a> | 
        <a href="<c:url value='Teacher?action=createQuizHome' />"> Create Quiz</a> | 
        <a href="<c:url value='Teacher?action=addQuestion' />"> Add Question </a>
        
        <c:forEach items="${errors}" var="error">
        <li>${error}</li>
        </c:forEach>
    </body>
</html>
