package com.shilovich.hrbet.controller.command;

public class ServletForward {

    private String page;
    private Boolean isRedirect;

    public ServletForward(String page, Boolean isRedirect) {
        this.page = page;
        this.isRedirect = isRedirect;
    }

    public String getPage() {
        return page;
    }

    public Boolean getRedirect() {
        return isRedirect;
    }
}
