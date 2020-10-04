<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css"
      integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"
        integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl"
        crossorigin="anonymous"></script>
<div class="row">
    <div class="col-xs-12 col-sm-8 col-md-6 col-sm-offset-2 col-md-offset-3">
        <div class="graphic-container">
            <form class="form-horizontal" action="${pageContext.request.contextPath}/races?command=login"
                  method="post">
                <div class="form-group">
                    <label for="email">Email</label>:
                    <input type="email"
                           id="email"
                           name="email"
                           class="form-control"
                           autofocus="autofocus"
                           placeholder="Email"
                           value="${email}">
                </div>
                <div class="form-group">
                    <label for="password">Password</label>:
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
                <div class="form-group" action="${session.invalidate()};">
                    <div class="row">
                        <div class="col-sm-6 col-sm-offset-3">
                            <input type="submit"
                                   name="logout-submit"
                                   id="logout-submit"
                                   class="form-control btn btn-info"
                                   value="Log out">
                        </div>
                    </div>
                </div>
            </form>
        </div>
    </div>
</div>