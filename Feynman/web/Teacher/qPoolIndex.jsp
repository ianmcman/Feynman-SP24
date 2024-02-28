<%-- 
    Document   : qPoolIndex
    Created on : Feb 21, 2024, 2:18:40 PM
    Author     : ms461431
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
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
            <c:when test="${qPools == null}">
                <h2>There are no pools currently</h2>
            </c:when>
            <c:otherwise>
                <c:forEach items="${qPools}" var="pool">
                    <h2>${pool.getName}</h2>
                </c:forEach>
                    <a href="<c:url value='Teacher?action=addQPool&rPage=index' />"> Add Question Pool</a>
            </c:otherwise>
        </c:choose>
        
    </body>
</html>
