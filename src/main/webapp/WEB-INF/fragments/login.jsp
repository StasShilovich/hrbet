<form class="form-horizontal" action="${pageContext.request.contextPath}/dispatcher"
      method="post">
    <input type="hidden" name="command" value="login">
    <div class="form-group">
        <%--                    <label for="email">--%>
        <%--                        <fmt:message key="label.header.email"/>--%>
        <%--                    </label>:--%>
        <input type="email"
               id="email"
               name="email"
               class="form-control"
               autofocus="autofocus"
               placeholder="<fmt:message key="label.header.email"/>"
               value="${email}">
    </div>
    <div class="form-group">
        <%--                    <label for="password">--%>
        <%--                        <fmt:message key="label.header.password"/>--%>
        <%--                    </label>:--%>
        <input type="password"
               id="password"
               name="password"
               class="form-control"
               placeholder="<fmt:message key="label.header.password"/>"
               value="${password}">
    </div>
    <div class="form-group">
        <div class="form-horizontal">
            <input type="submit"
                   name="login-submit"
                   id="login-submit"
                   class="btn btn-primary btn-lg active"
                   value="<fmt:message key="label.login"/>">
        </div>
    </div>
</form>
<a class="form-horizontal"
   href="${pageContext.request.contextPath}/dispatcher?command=forward_registration">
    <div class="form-group">
        <input type="submit"
               name="logout-submit"
               id="registration"
               class="btn btn-primary btn-lg active"
               value="<fmt:message key="label.registration.title"/>">
    </div>
</a>

<c:if test="${not empty emptyParams}">
    <div class="alert alert-danger" role="alert">
            ${emptyParams}
    </div>
</c:if>
<c:if test="${not empty incorrectData}">
    <div class="alert alert-danger" role="alert">
            ${incorrectData}
    </div>
</c:if>
