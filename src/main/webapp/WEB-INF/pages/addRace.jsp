<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <%@include file="/WEB-INF/pages/util/localization.jsp" %>
    <%@include file="/WEB-INF/pages/util/bootstrap.jsp" %>
    <title><fmt:message key="label.title.profile"/></title>
</head>
<jsp:useBean id="now" class="java.util.Date"/>
<fmt:formatDate var="date" value="${now}" pattern="yyyy-MM-dd"/>
<body data-topbar="dark" data-layout="horizontal">
<div id="layout-wrapper">
    <%@include file="/WEB-INF/pages/header.jsp" %>
    <div class="container-fluid  page-content ">
        <div class="col-lg-12 card  ">
            <div class="table-responsive">
                <form class="form-horizontal" action="${pageContext.request.contextPath}/dispatcher" method="post">
                    <input type="hidden" name="command" value="add_race">
                    <table class="table table-striped">
                        <tbody>
                        <tr>
                            <td>
                                <label for="location-input" class="col-2 col-form-label">
                                    <fmt:message key="label.location"/>
                                </label>
                            </td>
                            <td>
                                <input class="form-control" type="text" name="location" id="location-input"/>
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <label for="datetime-input" class="col-2 col-form-label">
                                    <fmt:message key="label.race.date"/>
                                </label>
                            </td>
                            <td>
                                <input class="form-control" type="datetime-local" name="date" value="${date}"
                                       id="datetime-input">
                            </td>
                        </tr>
                        <c:forEach var="i" begin="0" end="5" step="1">
                            <tr>
                                <td>
                                    <div class="input-group-prepend">
                                        <label class="input-group-text" for="inputGroupSelect">
                                            <fmt:message key="label.profile.horse"/>
                                        </label>
                                    </div>
                                </td>
                                <td>
                                    <select class="custom-select" id="inputGroupSelect" name="horseId">
                                        <option selected><fmt:message key="label.race.choose"/></option>
                                        <c:forEach var="horse" items="${horseList}">
                                            <option value="${horse.id}">${horse.name}
                                                <c:set target="${horseMap}" property="${i}" value="${horse.id}"/>
                                            </option>
                                        </c:forEach>
                                    </select>
                                </td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                    <input class="btn btn-primary btn-lg active" type="submit"
                           value="<fmt:message key="label.race.add"/>"/>
                </form>
            </div>
        </div>
    </div>
    <%@include file="/WEB-INF/pages/footer.jsp" %>
</div>
</body>
</html>