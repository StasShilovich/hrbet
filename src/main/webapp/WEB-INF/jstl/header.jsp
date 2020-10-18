<nav class="navbar navbar-light bg-light">
    <a class="navbar-brand" href="${pageContext.request.contextPath}/index.jsp">
        <img alt="Brand" class="img-responsive" src="${pageContext.request.contextPath}/img/Bet_logo.svg.png">
    </a>
    <c:if test="${empty  sessionScope.userAuth}">
        <%@include file="/WEB-INF/fragments/login.jsp" %>
    </c:if>
    <c:if test="${ not empty sessionScope.userAuth}">
        <%@include file="/WEB-INF/fragments/user.jsp" %>
    </c:if>
</nav>
