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
        <h1>Quiz Creation</h1>
        <ul>
            <p style="color: red; font-weight: bold">${message}</p>
            <c:forEach items="${errors}" var="error">
                <li>${error}</li>
            </c:forEach>
        </ul>
        
        <form action="Teacher" method="post">
            <input type="hidden" name="action" value="createQuiz"/>
            <label for="retakes">Retakes:</label>
            <input id="retakes" type="number" name="retakes" min="1" value="1"><br><br>
            <label for id="poolChoice">Question Pool for this Assessment:</label>
            <select name="poolsList" id="poolChoice">
                <c:forEach items="${pools}" var="pool">
                    <option value="${pool.ID}>${pool.name}</option>
                </c:forEach>
            </select><br><br>
            <input type="submit" value="Create Quiz">
        </form>
    </body>
</html>
