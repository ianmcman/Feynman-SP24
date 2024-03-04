<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Adding a Question</title>
    </head>
    <body>
        <h1>New Question: </h1>
        <form action="<c:url value='Teacher?action=createQuestion' />" method="post">
            <input type="hidden" value="<c:out value='${rPage}'/>" name="rPage" />
            <input type="hidden" value="<c:out value='${rIndex}'/>" name="rIndex" />
            <label for="qText">Question text: </label>
            <input type="text" value="<c:out value='${qText}'/>" name="qText" id="qText"/><br>
            <label for="qAnswer">Answer to question: </label>
            <input type="text" value="<c:out value='${qAnswer}'/>" name="qAnswer" id="qAnswer"/><br>
            <label for="qDiff">Question difficulty (1-5): </label>
            <input type="text" value="<c:out value='${qDiff}'/>" name="qDiff" id="qDiff"/><br>

            <label for="qType">Question included in:</label>
            <select name="qType" id="qType">
                <c:forEach items="${qTypes}" var="type">
                    <option value="${type}" <c:if test="${qType == type}">selected</c:if>>
                        ${type}</option>
                </c:forEach>
            </select><br>

            <input type="submit"/>
        </form>
        <c:forEach items="${errors}" var="error">
        <li>${error}</li>
        </c:forEach>
</body>
</html>
