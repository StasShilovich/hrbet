package com.shilovich.hrbet.controller.impl;

import com.shilovich.hrbet.controller.Command;
import com.shilovich.hrbet.exception.CommandException;
import com.shilovich.hrbet.controller.model.ServletForward;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static com.shilovich.hrbet.controller.CommandParameter.PAGE_INDEX;

public class LogOutCommandImpl implements Command {
    @Override
    public ServletForward execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, CommandException {
        HttpSession session = req.getSession();
        if (session != null) {
            session.invalidate();
        }
        return new ServletForward(PAGE_INDEX);
    }
}