package com.shilovich.hrbet.controller.model;

public class ServletForward {

    private String page;
    private boolean isRedirect = false;

    public ServletForward(String page) {
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
