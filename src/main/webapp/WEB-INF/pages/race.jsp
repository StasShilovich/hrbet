<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <%@include file="/WEB-INF/fragments/header.jsp" %>
    <title>Race</title>
</head>
<body>
<div class="col-md-10">
    <div class="ml-auto">
        <table class="table" border="4">
            <thead>
            <tr>
                <th scope="col">№</th>
                <th scope="col">
                    <fmt:message key="label.race.name"/>
                </th>
                <th scope="col">
                    <fmt:message key="label.race.age"/>
                </th>
                <th scope="col">
                    <fmt:message key="label.race.jockey"/>
                </th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="horse" items="${horseSet}" varStatus="count">
                <tr>
                    <td>${count.index+1} </td>
                    <td> ${horse.name} </td>
                    <td>${horse.age}</td>
                    <td>${horse.jockey}</td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</div>

<%@include file="/WEB-INF/fragments/footer.jsp" %>
</body>
</html>
