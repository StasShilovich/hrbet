package com.shilovich.hrbet.controller;

import com.shilovich.hrbet.bean.PermissionEnum;
import com.shilovich.hrbet.exception.CommandException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Set;

/**
 * The interface Command.
 * <p>
 * This is the interface for each command that the servlet handles implements.
 */
public interface Command {
    /**
     * We get an object of the router type, which is further processed by the servlet.
     *
     * @param request  HTTP servlet request
     * @param response HTTP servlet response
     * @return Router
     * @throws CommandException the command exception
     */
    Router execute(HttpServletRequest request, HttpServletResponse response) throws CommandException;

    /**
     * Checks if the list of permissions is sufficient to permit further work.
     *
     * @param permissions the permissions
     * @return the boolean
     */
    boolean isAllowed(Set<PermissionEnum> permissions);
}
