<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setBundle basename="international\message"/>
<html>
<head>
    <%@include file="/WEB-INF/fragments/header.jsp" %>
    <title>
        <fmt:message key="label.title"/>
    </title>

</head>
<body>
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
                    <td>${count.index+1}</td>
                    <td>${race.location}</td>
                    <td>${race.date}</td>
                    <td>${race.bank}</td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</div>
<%@include file="/WEB-INF/fragments/footer.jsp" %>
</body>
</html>
