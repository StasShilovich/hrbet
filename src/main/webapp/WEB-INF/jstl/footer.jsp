<c:if test="${not empty  sessionScope.userAuth}">
    <div class="col-md-4" xmlns:c="http://www.w3.org/1999/html">
        <c:if test="${not empty sessionScope}">
            <td>
                <fmt:message key="label.footer.hello"/>
                <c:out value="${sessionScope.userAuth.name}"/>
            </td>
        </c:if>
    </div>
</c:if>
<div class="row">
    <div class="col-1"></div>
    <div class="col-1">
        <a class="form-horizontal" href="${pageContext.request.contextPath}/dispatcher?command=cookie&locale=en">
            <div class="form-group">
                <input type="submit" name="logout-submit" id="EN" class="btn btn-primary btn-lg active" value="EN">
            </div>
        </a>
    </div>
    <div class="col-1">
        <a class="form-horizontal" href="${pageContext.request.contextPath}/dispatcher?command=cookie&locale=ru">
            <div class="form-group">
                <input type="submit" name="logout-submit" id="RU" class="btn btn-primary btn-lg active" value="RU">
            </div>
        </a>
    </div>
    <div class="col-9"></div>

</div>