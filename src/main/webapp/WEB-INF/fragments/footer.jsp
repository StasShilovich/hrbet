<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<div class="col-md-4" xmlns:c="http://www.w3.org/1999/html">
    <c:if test="${not empty sessionScope}">
        <td>
            <fmt:message key="label.footer.hello"/>
            <c:out value="${sessionScope.userAuth.name}"/>
        </td>
    </c:if>
</div>
