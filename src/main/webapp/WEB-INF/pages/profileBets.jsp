<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <%@include file="/WEB-INF/pages/util/localization.jsp" %>
    <%@include file="/WEB-INF/pages/util/bootstrap.jsp" %>
    <title><fmt:message key="label.profile.bets"/></title>
</head>
<body data-topbar="dark" data-layout="horizontal">
<div id="layout-wrapper">
    <%@include file="/WEB-INF/pages/header.jsp" %>
    <div class="container-fluid  page-content ">
        <div class="col-lg-12 card  ">
            <div class="table-responsive">
                <table class="table table-centered datatable dt-responsive nowrap"
                       data-page-length="5"
                       style="border-collapse: collapse; border-spacing: 0; width: 100%;">
                    <thead class="thead-light">
                    <th style="width: 20px;">
                        <div class="custom-control custom-checkbox">
                            <input type="checkbox" class="custom-control-input"
                                   id="ordercheck">
                            <label class="custom-control-label"
                                   for="ordercheck">&nbsp;</label>
                        </div>
                    </th>
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
                            <fmt:message key="label.profile.cash"/>
                        </th>
                        <th scope="col">
                            <fmt:message key="label.profile.ratio"/>
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
                    <td>
                        <div class="custom-control custom-checkbox">
                            <input type="checkbox" class="custom-control-input"
                                   id="ordercheck1">
                            <label class="custom-control-label"
                                   for="ordercheck1">&nbsp;</label>
                        </div>
                    </td>
                    <c:forEach var="bet" items="${userBets}" varStatus="count">
                        <tr>
                            <td>${count.index+1} </td>
                            <td> ${bet.status} </td>
                            <td>${bet.date}</td>
                            <td>${bet.race.location}</td>
                            <td>${bet.cash}</td>
                            <td>${bet.ratio}</td>
                            <td>${bet.type.type}</td>
                            <td>${bet.horse.name}</td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
    <%@include file="/WEB-INF/pages/footer.jsp" %>
</div>
</body>
</html>