<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setBundle basename="international\message"/>
<html>
<head>
    <%@include file="/WEB-INF/fragments/header.jsp" %>
    <title>
        Registration
    </title>
</head>
<body>
<form action="/hrbets/dispatcher?command=registration" method="post">
    <table style="with: 50%">
        <tr>
            <td>Name</td>
            <td><input type="text" name="name"/></td>
        </tr>
        <tr>
            <td>Surname</td>
            <td><input type="text" name="surname"/></td>
        </tr>
        <tr>
            <td>Email</td>
            <td><input type="text" name="email"/></td>
        </tr>
        <tr>
            <td>Password</td>
            <td><input type="password" name="password"/></td>
        </tr>
    </table>
    <input type="submit" value="Submit"/></form>
</body>
</html>