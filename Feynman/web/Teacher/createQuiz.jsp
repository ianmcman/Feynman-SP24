<%-- 
    Document   : createQuiz
    Created on : Feb 21, 2024, 2:22:49 PM
    Author     : im757299
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
        <h1>Quiz Creation</h1>
        <ul>
            <p style="color: red; font-weight: bold">${message}</p>
            <c:forEach items="${errors}" var="error">
                <li style="color: red;">${error}</li>
            </c:forEach>
        </ul>
        
        <form action="Teacher" method="post">
            <input type="hidden" name="action" value="createQuiz"/>
            <label for="name">Assessment Name:</label>
            <input id="name" name="name"/> <br><br>
            <label for="retakes">Retakes:</label>
            <input id="retakes" type="number" name="retakes" min="0" value="0">
            <label for="infiniteRetakes">Infinite Retakes?</label>
            <input id="infiniteRetakes" type="checkbox" name="infiniteRetakes" 
                   onclick="document.getElementById('retakes').disabled=this.checked;"/>                   
            <br><br>
            <label for id="poolChoice">Question Pool for this Assessment:</label>
            <select name="poolChoice" id="poolChoice">
                <c:forEach items="${pools}" var="pool">
                    <option value="${pool.ID}">${pool.name}</option>
                </c:forEach>
            </select><br><br>
            
            <label for id="aType">Assessment Type:</label>
            <select name="aType" id="aType">
                <c:forEach items="${aTypes}" var="type">
                    <option value="${type}"
                            <c:if test="${aType == type}">selected</c:if>>
                                ${type}</option>
                </c:forEach>
            </select>
            
            <br><br>
            <input type="submit" value="Create Quiz">
        </form>
    </body>
</html>
