package com.shilovich.hrbet.controller.command;

import com.shilovich.hrbet.controller.Command;
import com.shilovich.hrbet.exception.CommandException;
import com.shilovich.hrbet.controller.Router;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.shilovich.hrbet.controller.CommandParameter.PAGE_PROFILE;

public class ProfileCommand implements Command {
    @Override
    public Router execute(HttpServletRequest req, HttpServletResponse resp) throws CommandException {
        return new Router(PAGE_PROFILE);
    }
}