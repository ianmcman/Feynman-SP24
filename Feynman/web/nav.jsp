        <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <nav>
            <a href="<c:url value='Public' />">Home</a> | 
        <c:choose>
            <c:when test = "${sessionScope.user == null}"> 
                <a href="<c:url value='Public?action=login' />">Login</a> |    
                <a href="<c:url value='Public?action=register' />">Register</a> | 
            </c:when>
            <c:when test = "${sessionScope.user != null}"> 
                <a href="<c:url value='Private?action=dashboard' />">Dashboard</a> |    
                <a href="<c:url value='Private?action=logout' />">Logout</a>
            </c:when>
        </c:choose>
        </nav>
