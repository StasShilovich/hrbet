package com.shilovich.hrbet.controller;

import com.shilovich.hrbet.controller.exception.CommandException;
import com.shilovich.hrbet.controller.model.ServletForward;
import com.shilovich.hrbet.service.exception.ServiceException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public interface Command {
    ServletForward execute(HttpServletRequest req) throws ServletException, IOException, CommandException;
}
