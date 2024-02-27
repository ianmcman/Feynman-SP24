<%-- 
    Document   : index
    Created on : Feb 21, 2024, 2:06:17 PM
    Author     : ms461431
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
        <h1>Hello World!</h1>
        <a href="<c:url value='Teacher?action=qPHome' />"> qPool</a>
        <a href="<c:url value='Teacher?action=createQuizHome' />"> Create Quiz</a>
        <a href="<c:url value='Teacher?action=addQ&rPage=index' />"> Add Question </a>
    </body>
</html>
