<c:if test="${empty  sessionScope.userAuth}">
    <div class="row">
        <div class="col-xs-12 col-sm-8 col-md-6 col-sm-offset-2 col-md-offset-3">
            <div class="graphic-container">
                <form class="form-horizontal" action="/hrbets/dispatcher?command=login"
                      method="post">
                    <div class="form-group">
                        <label for="email">
                            <fmt:message key="label.header.email"/>
                        </label>:
                        <input type="email"
                               id="email"
                               name="email"
                               class="form-control"
                               autofocus="autofocus"
                               placeholder="Email"
                               value="${email}">
                    </div>
                    <div class="form-group">
                        <label for="password">
                            <fmt:message key="label.header.password"/>
                        </label>:
                        <input type="password"
                               id="password"
                               name="password"
                               class="form-control"
                               placeholder="Password"
                               value="${password}">
                    </div>
                    <div class="form-group">
                        <div class="row">
                            <div class="col-sm-6 col-sm-offset-3">
                                <input type="submit"
                                       name="login-submit"
                                       id="login-submit"
                                       class="form-control btn btn-info"
                                       value="Log In">
                            </div>
                        </div>
                    </div>
                </form>
                <a href="">
                    <div class="form-group">
                        <div class="row">
                            <div class="col-sm-6 col-sm-offset-3">
                                <input type="submit"
                                       id="registration"
                                       class="form-control btn btn-info"
                                       value="Registration">
                            </div>
                        </div>
                    </div>
                </a>
            </div>
        </div>
    </div>
</c:if>

<c:if test="${not empty sessionScope.userAuth}">
    <form class="form-horizontal" action="/hrbets/dispatcher?command=logout" method="post">
        <div class="row">
            <div class="col-sm-6 col-sm-offset-3">
                <input type="submit"
                       name="logout-submit"
                       id="logout-submit"
                       class="form-control btn btn-info"
                       value="Log out">
            </div>
        </div>
    </form>
</c:if>
<div class="col-xl-4">
    <c:if test="${not empty emptyParams}">
        <div class="alert-dark" role="alert">
            <c:out value="${emptyParams}"/>
        </div>
    </c:if>
</div>