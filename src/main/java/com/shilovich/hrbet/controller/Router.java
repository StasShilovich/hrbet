package com.shilovich.hrbet.controller;

import javax.servlet.http.HttpServletRequest;

import static com.shilovich.hrbet.controller.CommandParameter.*;

public class Router {

    private final String page;
    private boolean isRedirect;

    public Router(HttpServletRequest request) {
        String url = request.getHeader(URL_REFERER);
        this.page = url.replaceAll(PAGE_START_PREFIX, BLANK);
    }

    public Router(String page) {
        this.page = page;
    }

    public String getPage() {
        return page;
    }

    public boolean isRedirect() {
        return isRedirect;
    }

    public void redirect() {
        this.isRedirect = true;
    }
}
