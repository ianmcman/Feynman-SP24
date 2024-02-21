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
            <c:forEach items="${errors}" var="error">
                <li>${error}</li>
            </c:forEach>
        </ul>
        
        <form action="TeacherController" method="post">
            <input type="hidden" name="action" value="createQuiz"
            <select id=""
        </form>
    </body>
</html>
