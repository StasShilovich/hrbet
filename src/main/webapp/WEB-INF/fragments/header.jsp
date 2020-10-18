<%@include file="/WEB-INF/fragments/bootstrap.jsp" %>
<%@include file="/WEB-INF/fragments/localization.jsp" %>
<c:if test="${empty  sessionScope.userAuth}">
    <%@include file="/WEB-INF/fragments/login.jsp" %>
</c:if>
<c:if test="${ not empty sessionScope.userAuth}">
    <%@include file="/WEB-INF/fragments/logout.jsp" %>
</c:if>

<%--<div>--%>
<%--    <a href=" <fmt:setLocale value="en_US"/>" scope="session">En</a>--%>
<%--    <a href="<fmt:setLocale value="ru_RU"/>" scope="session">Ru</a>--%>
<%--</div>--%>
