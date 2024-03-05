<%-- 
    Document   : editQPool
    Created on : Mar 5, 2024, 3:32:50 AM
    Author     : ms461431
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="business.QuestionPool"%>
<%@page import="business.Question"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Edit ${qp.getName()}</title>
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
        <form action="<c:url value='Teacher?action=editQPool' />" method="post">
            <input type="hidden" value="<c:out value='${rPage}'/>" name="rPage" />
            <input type="hidden" value="<c:out value='${rIndex}'/>" name="rIndex" />
            <input type="hidden" value="rename" name="edit" />
            <label for="qPName">Name for Question Pool: </label>
            <input type="text" value="<c:out value='${qp.getName()}'/>" name="qPName" id="qPName"/><br>
            <input type="submit" value="Rename"/>
        </form>
            <a href="<c:url value='Teacher?action=addQuestion&rPage=editQP&rIndex=${rIndex}' />"> Add Question to Pool</a>
            <c:choose>
            <c:when test="${qp.getQuestions().isEmpty()}">
                <h2>There are no questions currently</h2>
            </c:when>
            <c:otherwise >
                <c:forEach items="${qp.getQuestions()}" var="q">
                    <p><c:out value="${q.getQuestionText()}"/></p>
                </c:forEach>
                    <a href="<c:url value='Teacher?action=addQuestion&rPage=editQP&rIndex=${rIndex}' />"> Add Question to Pool</a>
            </c:otherwise >
        </c:choose>
            <ul>
            <c:forEach items="${errors}" var="error">
        <li>${error}</li>
        </c:forEach>
            </ul>
            <a href="<c:url value='Teacher?action=qPHome' />"> Back </a>
    </body>
</html>
