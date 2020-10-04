<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <%@include  file="/WEB-INF/fragments/header.jsp" %>
    <title>Races Page</title>

</head>
<body>
<div class="col-md-10">
    <div class="ml-auto">
        <table class="table" border="4">
            <thead>
            <tr>
                <th scope="col">Location</th>
                <th scope="col">Date</th>
                <th scope="col">Bank</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="race" items="${races}">
                <tr>
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
