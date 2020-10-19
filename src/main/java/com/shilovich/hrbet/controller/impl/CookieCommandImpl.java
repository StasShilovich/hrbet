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

import static com.shilovich.hrbet.constant.CommonConstant.*;

public class CookieCommandImpl implements Command {
    @Override
    public ServletForward execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, CommandException {
        String locale = req.getParameter(PARAM_COOKIE_LOCALE);
        String language = null;
        if (locale != null && locale.equalsIgnoreCase(LOCALE_LANGUAGE_RU)) {
            language = new Locale(LOCALE_LANGUAGE_RU, LOCALE_COUNTRY_RU).toString();
        } else {
            language = new Locale(LOCALE_LANGUAGE_EN, LOCALE_COUNTRY_US).toString();
        }
        resp.addCookie(new Cookie(PARAM_COOKIE_LOCALE, language));
        String url = req.getHeader(URL_REFERER);
        String forward = url.replaceAll(PAGE_START, BLANK);
        return new ServletForward(forward, true);
    }
}