package com.shilovich.hrbet.controller.impl;

import com.shilovich.hrbet.beans.User;
import com.shilovich.hrbet.controller.Command;
import com.shilovich.hrbet.controller.exception.CommandException;
import com.shilovich.hrbet.controller.model.ServletForward;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.shilovich.hrbet.constant.CommonConstant.*;

public class ProfileCommandImpl implements Command {
    @Override
    public ServletForward execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, CommandException {
        User user = (User) req.getSession().getAttribute(ATTR_USER_AUTH);
        System.out.println(user.toString());
        return new ServletForward(PAGE_PROFILE, false);
    }
}