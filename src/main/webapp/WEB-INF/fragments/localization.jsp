<%--<div class="btn-group btn-group-toggle" data-toggle="buttons">--%>
<%--    <label class="btn btn-primary ">--%>
<%--        <input type="checkbox" autocomplete="off"> EN--%>
<%--        <c:set var="${locale}" value="${en}"/>--%>
<%--    </label>--%>
<%--    <label class="btn btn-primary">--%>
<%--        <input type="checkbox" autocomplete="off"> RU--%>
<%--        <c:set var="${locale}" value="${ru}"/>--%>
<%--    </label>--%>
<%--</div>--%>

<%--<c:choose>--%>
<%--    <c:when test="${ locale='en'}">--%>
<%--        <fmt:setLocale value="en_US"/>--%>
<%--    </c:when>--%>
<%--    <c:otherwise>--%>
<%--        <fmt:setLocale value="ru_RU"/>--%>
<%--    </c:otherwise>--%>
<%--</c:choose>--%>
<fmt:setLocale value="ru_RU"/>
<fmt:setBundle basename="international\message"/>