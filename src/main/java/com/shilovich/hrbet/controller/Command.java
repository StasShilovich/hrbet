package com.shilovich.hrbet.controller;

import com.shilovich.hrbet.service.exception.ServiceException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface Command {
    String execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, ServiceException;
}
