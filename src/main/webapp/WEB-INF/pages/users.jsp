<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <%@include file="/WEB-INF/pages/util/localization.jsp" %>
    <%@include file="/WEB-INF/pages/util/bootstrap.jsp" %>
    <title>
        <fmt:message key="label.title.users"/>
    </title>
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
                            <fmt:message key="label.user.name"/>
                        </th>
                        <th scope="col">
                            <fmt:message key="label.user.surname"/>
                        </th>
                        <th scope="col">
                            <fmt:message key="label.user.email"/>
                        </th>
                        <th scope="col">
                            <fmt:message key="label.user.cash"/>
                        </th>
                        <th scope="col">
                            <fmt:message key="label.user.role.name"/>
                        </th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:set var="pageCount" value="${pageContext.request.getParameter('page')}"/>
                    <c:forEach var="user" items="${users}" varStatus="count">
                        <tr>
                            <td>
                                <c:if test="${empty pageCount}">
                                    <c:set var="pageCount" value="1"/>
                                </c:if>
                                    ${(pageCount-1)*2+1+count.index}
                            </td>
                            <td> ${user.name} </td>
                            <td>${user.surname}</td>
                            <td>${user.email}</td>
                            <td>${user.cash}</td>
                            <td>${user.role.name}</td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>

                <nav>
                    <ul class="pagination pagination-lg">
                        <c:forEach var="page" begin="1" end="${pageNumber}">
                            <li class="page-item"><a class="page-link"
                                                     href="${pageContext.request.contextPath}/dispatcher?command=show_users&page=${page}">${page}</a>
                            </li>
                        </c:forEach>
                    </ul>
                </nav>
            </div>
        </div>
        <%@include file="/WEB-INF/pages/footer.jsp" %>
    </div>
</div>
</body>
</html>