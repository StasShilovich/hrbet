<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
    <html>
    <head>
        <%@include file="/WEB-INF/pages/util/localization.jsp" %>
        <%@include file="/WEB-INF/pages/util/bootstrap.jsp" %>
        <title>
            <fmt:message key="label.title.races"/>
        </title>
    </head>
    <body data-topbar="dark" data-layout="horizontal">
    <div id="layout-wrapper">
            <%@include file="/WEB-INF/pages/header.jsp" %>
        <div class="container-fluid  page-content ">
            <div class="col-lg-12 card  ">

                <h4 class="card-title mb-4">
                    <fmt:message key="label.title.races"/>
                </h4>

                <div class="table-responsive">
                    <table class="table table-centered datatable dt-responsive nowrap"
                           data-page-length="5"
                           style="border-collapse: collapse; border-spacing: 0; width: 100%;">
                        <thead class="thead-light">
                        <tr>
                            <th scope="col">№</th>
                            <th scope="col">
                                <fmt:message key="label.location"/>
                            </th>
                            <th scope="col">
                                <fmt:message key="label.date"/>
                            </th>
                        </tr>
                        </thead>
                        <tbody>
                        <tr>

                            <c:set var="pageCount"
                                   value="${pageContext.request.getParameter('page')}"/>
                            <c:forEach var="race" items="${races}" varStatus="count">

                            <td>
                                <c:if test="${empty pageCount}">
                                    <c:set var="pageCount" value="1"/>
                                </c:if>
                                    ${(pageCount-1)*6+1+count.index} </td>
                            <td>
                                <a href="${pageContext.request.contextPath}/dispatcher?command=race&raceId=${race.id}">${race.location}</a>
                            </td>
                            <td>${race.date}</td>
                        </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>

                <nav>
                    <ul class="pagination pagination-lg">
                        <c:forEach var="page" begin="1" end="${pageNumber}">
                            <li class="page-item"><a class="page-link"
                                                     href="${pageContext.request.contextPath}/dispatcher?command=races&page=${page}">${page}</a>
                            </li>
                        </c:forEach>
                    </ul>
                </nav>
            </div>
            <%@include file="/WEB-INF/pages/footer.jsp" %>
        </div>
    </body>
    </html>