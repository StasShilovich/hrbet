package com.shilovich.hrbet.controller.impl;

import com.shilovich.hrbet.controller.Command;
import com.shilovich.hrbet.controller.exception.CommandException;
import com.shilovich.hrbet.controller.model.ServletForward;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

import static com.shilovich.hrbet.constant.CommonConstant.PAGE_REGISTRATION;

public class ForwardRegistrationCommandImpl implements Command {
    @Override
    public ServletForward execute(HttpServletRequest req) throws ServletException, IOException, CommandException {
        return new ServletForward(PAGE_REGISTRATION, false);
    }
}
