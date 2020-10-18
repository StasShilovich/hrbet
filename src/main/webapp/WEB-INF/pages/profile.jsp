<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <%@include file="/WEB-INF/jstl/localization.jsp" %>
    <%@include file="/WEB-INF/jstl/bootstrap.jsp" %>
    <title><fmt:message key="label.title.profile"/></title>
</head>
<body>
<%@include file="/WEB-INF/jstl/header.jsp" %>

<div class="card mb-3">
    <div class="card-body">
        <div class="row">
            <div class="col-sm-3"><h6 class="mb-0">Name</h6></div>
            <div class="col-sm-9 text-secondary">${sessionScope.userAuth.name}</div>
        </div>
        <hr>
        <div class="row">
            <div class="col-sm-3"><h6 class="mb-0">Surname</h6></div>
            <div class="col-sm-9 text-secondary"> ${sessionScope.userAuth.email}</div>
        </div>
        <hr>
        <div class="row">
            <div class="col-sm-3"><h6 class="mb-0">Email</h6></div>
            <div class="col-sm-9 text-secondary"> ${sessionScope.userAuth.email}</div>
        </div>
        <hr>
        <div class="row">
            <div class="col-sm-3"><h6 class="mb-0">Role</h6></div>
            <div class="col-sm-9 text-secondary">${sessionScope.userAuth.role.name}</div>
        </div>
    </div>
</div>
</body>
</html>
