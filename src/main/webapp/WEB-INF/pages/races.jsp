<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Items</title>
</head>
<body>
<div class="col-md-4">
    <h1>
        <div class="ml-auto">
            Races Page
        </div>
    </h1>
</div>
<div class="col-md-10">
    <div class="ml-auto">
        <table class="table" border="4">
            <thead>
            <tr>
                <th scope="col">location</th>
                <th scope="col">date</th>
                <th scope="col">bank</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach  var="race" items="${races}">
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
</body>
</html>
