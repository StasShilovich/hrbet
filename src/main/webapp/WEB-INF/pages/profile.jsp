<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <%@include file="/WEB-INF/pages/util/localization.jsp" %>
    <%@include file="/WEB-INF/pages/util/bootstrap.jsp" %>
    <title><fmt:message key="label.title.profile"/></title>
</head>
<body data-topbar="dark" data-layout="horizontal">
<div id="layout-wrapper">
    <%@include file="/WEB-INF/pages/header.jsp" %>
    <div class="container-fluid  page-content ">
        <div class="col-lg-12 card  ">
            <div class="table-responsive">

                <div class="card mb-3">
                    <div class="card-body">
                        <div class="row">
                            <div class="col-sm-3"><h6 class="mb-0">Name</h6></div>
                            <div class="col-sm-9 text-secondary">${sessionScope.userAuth.name}</div>
                        </div>
                        <hr>
                        <div class="row">
                            <div class="col-sm-3"><h6 class="mb-0">Surname</h6></div>
                            <div class="col-sm-9 text-secondary"> ${sessionScope.userAuth.surname}</div>
                        </div>
                        <hr>
                        <div class="row">
                            <div class="col-sm-3"><h6 class="mb-0">Email</h6></div>
                            <div class="col-sm-9 text-secondary"> ${sessionScope.userAuth.email}</div>
                        </div>
                        <hr>
                        <div class="row">
                            <div class="col-sm-3"><h6 class="mb-0">Cash</h6></div>
                            <div class="col-sm-9 text-secondary"> ${sessionScope.userAuth.cash}</div>
                        </div>
                        <hr>
                        <div class="row">
                            <div class="col-sm-3"><h6 class="mb-0">Role</h6></div>
                            <div class="col-sm-9 text-secondary">${sessionScope.userAuth.role.name}</div>
                        </div>
                    </div>
                </div>
                <form class="form-inline">
                    <a class="btn btn-primary btn-lg active"
                       href="${pageContext.request.contextPath}/dispatcher?command=update_cash&addCash=10">
                        <fmt:message key="label.addCash.10"/>
                    </a>
                    <a class="btn btn-primary btn-lg active"
                       href="${pageContext.request.contextPath}/dispatcher?command=update_cash&addCash=25">
                        <fmt:message key="label.addCash.25"/>
                    </a>
                    <a class="btn btn-primary btn-lg active"
                       href="${pageContext.request.contextPath}/dispatcher?command=update_cash&addCash=100">
                        <fmt:message key="label.addCash.100"/>
                    </a>
                </form>
                <form class="form-inline">
                    <a class="btn btn-primary btn-lg active"
                       href="${pageContext.request.contextPath}/dispatcher?command=show_user_bets">
                        <fmt:message key="label.registration.bets"/>
                    </a>
                </form>
            </div>
        </div>
        <%@include file="/WEB-INF/pages/footer.jsp" %>
    </div>
</body>
</html>
