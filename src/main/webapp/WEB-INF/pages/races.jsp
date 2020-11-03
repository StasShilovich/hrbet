<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <%@include file="/WEB-INF/jstl/localization.jsp" %>
    <%@include file="/WEB-INF/jstl/bootstrap.jsp" %>
    <title>
        <fmt:message key="label.title.races"/>
    </title>
</head>
<body>
<%@include file="/WEB-INF/jstl/header.jsp" %>
<div class="col-md-10">
    <div class="ml-auto">
        <table class="table" border="4">
            <thead>
            <tr>
                <th scope="col">№</th>
                <th scope="col">
                    <fmt:message key="label.location"/>
                </th>
                <th scope="col">
                    <fmt:message key="label.date"/>
                </th>
                <th scope="col">
                    <fmt:message key="label.bank"/>
                </th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="race" items="${races}" varStatus="count">
                <tr>
                    <td>${count.index+1} </td>
                    <td>
                        <a href="${pageContext.request.contextPath}/dispatcher?command=race&raceId=${race.id}">${race.location}</a>
                    </td>
                    <td>${race.date}</td>
                    <td>${race.bank}</td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</div>
<nav>
    <ul class="pagination pagination-lg">
        <c:forEach var="page" begin="1" end="${pageNumber}">
            <li class="page-item"><a class="page-link"
                                     href="${pageContext.request.contextPath}/dispatcher?command=races&page=${page}">${page}</a>
            </li>
        </c:forEach>
    </ul>
</nav>
<%@include file="/WEB-INF/jstl/footer.jsp" %>
</body>
</html>
