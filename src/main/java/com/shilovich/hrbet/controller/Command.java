package com.shilovich.hrbet.controller;

import com.shilovich.hrbet.exception.CommandException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface Command {
    Router execute(HttpServletRequest req, HttpServletResponse resp) throws CommandException;
}
