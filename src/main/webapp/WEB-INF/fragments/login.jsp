<div class="row">
    <div class="col-xs-12 col-sm-8 col-md-6 col-sm-offset-2 col-md-offset-3">
        <div class="graphic-container">
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
                    <div class="row">
                        <div class="col-sm-6 col-sm-offset-3">
                            <input type="submit"
                                   name="login-submit"
                                   id="login-submit"
                                   class="form-control btn btn-info"
                                   value="<fmt:message key="label.login"/>">
                        </div>
                    </div>
                </div>
            </form>
        </div>
    </div>
</div>

<a class="form-horizontal"
   href="${pageContext.request.contextPath}/dispatcher?command=forward_registration">
    <div class="form-group">

        <input type="submit"
               name="logout-submit"
               id="registration"
               class="form-control btn btn-info"
               value="<fmt:message key="label.registration.title"/>">

    </div>
</a>

<c:if test="${not empty emptyParams}">
    <div class="alert alert-dismissible alert-primary">
        <button type="button" class="close" data-dismiss="alert">&times;</button>
            ${emptyParams}
    </div>
</c:if>
<c:if test="${not empty incorrectData}">
    <div class="alert alert-dismissible alert-primary">
        <button type="button" class="close" data-dismiss="alert">&times;</button>
            ${incorrectData}
    </div>
</c:if>
