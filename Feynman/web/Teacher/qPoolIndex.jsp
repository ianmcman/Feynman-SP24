<%-- 
    Document   : qPoolIndex
    Created on : Feb 21, 2024, 2:18:40 PM
    Author     : ms461431
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="business.QuestionPool"%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Question Pools</title>
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
        <h1>Question Pools</h1>
        <a href="<c:url value='Teacher?action=addQPool&rPage=index' />"> Add Question Pool</a>
        <c:choose>
            <c:when test="${pools.isEmpty()}">
                <h2>There are no pools currently</h2>
            </c:when>
            <c:otherwise >
                <c:forEach items="${pools}" var="pool">
                    <h3><c:out value="${pool.getName()}"/></h3>
                    <a href="<c:url value='Teacher?action=editQPool&rPage=index&rIndex=${pool.getID()}' />">edit</a><br/>
                </c:forEach>
                    <a href="<c:url value='Teacher?action=addQPool&rPage=index' />"> Add Question Pool</a>
            </c:otherwise >
        </c:choose>
        
    </body>
</html>
