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
                <td>
                    <input id="nameCol" type="text" name="name"/>
                    <script>
                        document.getElementById("nameCol").value = "<c:out value="${userMap['name']}"/>";
                    </script>
                    <c:if test="${not empty  userMap &&empty userMap['name']}">
                        <div class="alert alert-danger" role="alert">
                            <fmt:message key="label.registration.incorrect.name"/>
                        </div>
                    </c:if>
                </td>
            </tr>
            <tr>
                <td><fmt:message key="label.registration.surname"/></td>
                <td>
                    <input id="surnameCol" type="text" name="surname"/>
                    <script>
                        document.getElementById("surnameCol").value = "<c:out value="${userMap['surname']}"/>";
                    </script>
                    <c:if test="${not empty  userMap &&empty userMap['surname']}">
                        <div class="alert alert-danger" role="alert">
                            <fmt:message key="label.registration.incorrect.surname"/>
                        </div>
                    </c:if>
                </td>
            </tr>
            <tr>
                <td><fmt:message key="label.header.email"/></td>
                <td>
                    <input id="emailCol" type="text" name="email"/>
                    <script>
                        document.getElementById("emailCol").value = "<c:out value="${userMap['email']}"/>";
                    </script>
                    <c:if test="${not empty  userMap &&empty userMap['email']}">
                        <div class="alert alert-danger" role="alert">
                            <fmt:message key="label.registration.incorrect.email"/>
                        </div>
                    </c:if>
                </td>
            </tr>
            <tr>
                <td><fmt:message key="label.header.password"/></td>
                <td>
                    <input id="passwordCol" type="password" name="password"/>
                    <script>
                        document.getElementById("passwordCol").value = "<c:out value="${userMap['password']}"/>";
                    </script>
                    <c:if test="${not empty  userMap &&empty userMap['password']}">
                        <div class="alert alert-danger" role="alert">
                            <fmt:message key="label.registration.incorrect.password"/>
                        </div>
                    </c:if>
                </td>
            </tr>
        </table>
        <input class="btn btn-primary btn-lg active" type="submit" value="Register"/></form>
</div>
</body>
</html>