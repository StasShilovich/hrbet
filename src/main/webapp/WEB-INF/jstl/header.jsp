<meta name="viewport" content="width=device-width, initial-scale=1">
<style>
    body, html {
        width: 100%;
        height: 100%;
    }

    .bg {
        position: relative;
        width: 100%;
        height: 40%;
        background-image: url("${pageContext.request.contextPath}/img/background.png");
        background-repeat: no-repeat;
        background-size: cover;
        display: block;
        background-position: center;
    }
</style>
<nav class="navbar navbar-light bg">
    <div class="row">
        <div class="col-md-10 left">
            <a class="navbar-brand" href="${pageContext.request.contextPath}/index.jsp">
                <img alt="Brand" class="img-responsive"
                     src="${pageContext.request.contextPath}/img/log.png">
            </a>
        </div>
        <div class="col-md-2 right">
            <c:if test="${empty  sessionScope.userAuth}">
                <%@include file="/WEB-INF/fragments/login.jsp" %>
            </c:if>
            <c:if test="${ not empty sessionScope.userAuth}">
                <%@include file="/WEB-INF/fragments/user.jsp" %>
            </c:if>
        </div>
    </div>
</nav>
