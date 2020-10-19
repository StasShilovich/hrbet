<nav class="navbar navbar-light bg-light">
    <div class="col-2">
        <a class="navbar-brand" href="${pageContext.request.contextPath}/index.jsp">
            <img alt="Brand" class="img-responsive" src="${pageContext.request.contextPath}/img/Bet_logo.svg.png">
        </a>
    </div>
    <div class="col-2"></div>
    <div class="col-2">
        <c:if test="${empty  sessionScope.userAuth}">
            <%@include file="/WEB-INF/fragments/login.jsp" %>
        </c:if>
        <c:if test="${ not empty sessionScope.userAuth}">
            <%@include file="/WEB-INF/fragments/user.jsp" %>
        </c:if>
    </div>
</nav>
