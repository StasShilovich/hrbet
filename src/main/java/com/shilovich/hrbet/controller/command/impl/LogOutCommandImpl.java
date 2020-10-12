package com.shilovich.hrbet.controller.command.impl;

import com.shilovich.hrbet.controller.Command;
import com.shilovich.hrbet.controller.command.ServletForward;
import com.shilovich.hrbet.service.exception.ServiceException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class LogOutCommandImpl implements Command {
    @Override
    public ServletForward execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, ServiceException {
        HttpSession session = req.getSession();
        if(session!=null){
            session.invalidate();
        }
        return new ServletForward("/index.jsp", false);
    }
}
