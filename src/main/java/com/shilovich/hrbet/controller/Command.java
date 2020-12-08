package com.shilovich.hrbet.controller;

import com.shilovich.hrbet.bean.PermissionEnum;
import com.shilovich.hrbet.exception.CommandException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Set;

public interface Command {
    Router execute(HttpServletRequest req, HttpServletResponse resp) throws CommandException;

    boolean isAllowed(Set<PermissionEnum> permissions);
}
