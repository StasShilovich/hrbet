package com.shilovich.hrbet.controller;

import com.shilovich.hrbet.exception.CommandException;
import com.shilovich.hrbet.controller.model.ServletForward;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface Command {
    ServletForward execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, CommandException;
}
