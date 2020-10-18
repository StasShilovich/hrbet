<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <%@include file="/WEB-INF/jstl/bootstrap.jsp" %>
    <%@include file="/WEB-INF/jstl/localization.jsp" %>
    <title>
        <fmt:message key="label.registration.title"/>
    </title>
</head>
<body>
<div class="card mb-3">
        <form class="form-horizontal" action="${pageContext.request.contextPath}/dispatcher" method="post">
            <input type="hidden" name="command" value="registration">
            <table class="table table-striped">
                <tr>
                    <td><fmt:message key="label.registration.name"/></td>
                    <td><input type="text" name="name"/></td>
                </tr>
                <tr>
                    <td><fmt:message key="label.registration.surname"/></td>
                    <td><input type="text" name="surname"/></td>
                </tr>
                <tr>
                    <td><fmt:message key="label.header.email"/></td>
                    <td><input type="text" name="email"/></td>
                </tr>
                <tr>
                    <td><fmt:message key="label.header.password"/></td>
                    <td><input type="password" name="password"/></td>
                </tr>
            </table>
            <input class="btn btn-primary btn-lg active" type="submit" value="Register"/></form>
</div>
<%@include file="/WEB-INF/jstl/footer.jsp" %>
</body>
</html>