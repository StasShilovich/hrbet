package com.shilovich.hrbet.controller.impl;

import com.shilovich.hrbet.controller.Command;
import com.shilovich.hrbet.controller.exception.CommandException;
import com.shilovich.hrbet.controller.model.ServletForward;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Locale;

public class CookieCommandImpl implements Command {
    @Override
    public ServletForward execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, CommandException {
        String locale = req.getParameter("locale");
        String language = null;
        if (locale != null && locale.equalsIgnoreCase("ru")) {
            language = new Locale("ru", "RU").toString();
        } else {
            language = new Locale("en", "US").toString();
        }
        System.out.println(language);
        Cookie cookie = new Cookie("locale", language);
        resp.addCookie(cookie);
        // TODO: 18.10.2020 refactor
        String[] split = req.getHeader("referer").split("8080");
        System.out.println(split[1]);
        return new ServletForward(split[1], true);
    }
}