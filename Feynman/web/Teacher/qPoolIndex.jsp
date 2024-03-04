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
        <h1>Question Pools</h1>
        <a href="<c:url value='Teacher?action=addQPool&rPage=index' />"> Add Question Pool</a>
        <c:choose>
            <c:when test="${pools == null}">
                <h2>There are no pools currently</h2>
            </c:when>
            <c:otherwise >
                <h2>help</h2>
                <c:forEach items="${pools}" var="pool">
                    <h2>me</h2>
                    <h2><c:out value="${pool.getName()}"/></h2><br/>
                </c:forEach>
                    <a href="<c:url value='Teacher?action=addQPool&rPage=index' />"> Add Question Pool</a>
                    <h3><c:out value="${pools}"/></h3>
            </c:otherwise >
        </c:choose>
        
    </body>
</html>
