<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="business.User"%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Adding a Question Pool</title>
    </head>
    <body>
        <c:import url="/nav.jsp" />
        <h1>New Question Pool: </h1>
        <form action="<c:url value='Teacher?action=createQPool' />" method="post">
            <input type="hidden" value="<c:out value='${rPage}'/>" name="rPage" />
            <input type="hidden" value="<c:out value='${rIndex}'/>" name="rIndex" />
            <label for="qPName">Name for Question Pool: </label>
            <input type="text" value="<c:out value='${qPName}'/>" name="qPName" id="qPName"/><br>
            <input type="submit"/>
        </form>
        <c:forEach items="${errors}" var="error">
        <li>${error}</li>
        </c:forEach>
</body>
</html>
