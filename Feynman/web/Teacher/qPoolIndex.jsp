<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="business.QuestionPool"%>
<%@page import="business.User"%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Question Pools</title>
    </head>
    <body>
        <c:import url="/nav.jsp" />
        <h1>Question Pools</h1>
        <a href="<c:url value='Teacher?action=addQPool&rPage=index' />"> Add Question Pool</a>
        <c:choose>
            <c:when test="${pools.isEmpty()}">
                <h2>There are no pools currently</h2>
            </c:when>
            <c:otherwise >
                <c:forEach items="${pools}" var="pool">
                    <h3><c:out value="${pool.getName()}"/></h3>
                    <a href="<c:url value='Teacher?action=editQPool&rPage=index&rIndex=${pool.getID()}' />">Edit</a><br/>
                </c:forEach>
                    <a href="<c:url value='Teacher?action=addQPool&rPage=index' />"> Add Question Pool</a>
            </c:otherwise >
        </c:choose>
        
    </body>
</html>
