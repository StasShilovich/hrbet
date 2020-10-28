<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <%@include file="/WEB-INF/jstl/localization.jsp" %>
    <%@include file="/WEB-INF/jstl/bootstrap.jsp" %>
    <title><fmt:message key="label.profile.bets"/></title>
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
                    <fmt:message key="label.profile.status"/>
                </th>
                <th scope="col">
                    <fmt:message key="label.profile.time"/>
                </th>
                <th scope="col">
                    <fmt:message key="label.profile.location"/>
                </th>
                <th scope="col">
                    <fmt:message key="label.profile.dollars"/>
                </th>
                <th scope="col">
                    <fmt:message key="label.profile.type"/>
                </th>
                <th scope="col">
                    <fmt:message key="label.profile.horse"/>
                </th>

            </tr>
            </thead>
            <tbody>
            <c:forEach var="bet" items="${userBets}" varStatus="count">
                <tr>
                    <td>${count.index+1} </td>
                    <td> ${bet.status} </td>
                    <td>${bet.date}</td>
                    <td>${bet.race.location}</td>
                    <td>${bet.dollars}</td>
                    <td>${bet.type.type}</td>
                    <td>${bet.horse.name}</td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</div>
<%@include file="/WEB-INF/jstl/footer.jsp" %>
</body>
</html>