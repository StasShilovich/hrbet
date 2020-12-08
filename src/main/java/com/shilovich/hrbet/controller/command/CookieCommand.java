package com.shilovich.hrbet.controller.command;

import com.shilovich.hrbet.bean.PermissionEnum;
import com.shilovich.hrbet.controller.Command;
import com.shilovich.hrbet.exception.CommandException;
import com.shilovich.hrbet.controller.Router;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;
import java.util.Set;

import static com.shilovich.hrbet.bean.PermissionEnum.*;
import static com.shilovich.hrbet.controller.CommandParameter.*;
import static org.apache.commons.lang3.StringUtils.*;

public class CookieCommand implements Command {
    @Override
    public Router execute(HttpServletRequest req, HttpServletResponse resp) throws CommandException {
        String locale = req.getParameter(PARAM_COOKIE_LOCALE);
        String language;
        if (isNoneEmpty(locale) && equalsIgnoreCase(locale, LOCALE_LANGUAGE_RU)) {
            language = new Locale(LOCALE_LANGUAGE_RU, LOCALE_COUNTRY_RU).toString();
        } else {
            language = new Locale(LOCALE_LANGUAGE_EN, LOCALE_COUNTRY_US).toString();
        }
        resp.addCookie(new Cookie(PARAM_COOKIE_LOCALE, language));
        Router router = new Router(req);
        router.redirect();
        return router;
    }

    @Override
    public boolean isAllowed(Set<PermissionEnum> permissions) {
        return permissions.contains(QUEST_BASIC) || permissions.contains(USER_BASIC) ||
                permissions.contains(ADMIN_BASIC) || permissions.contains(CUSTOMER_BASIC);
    }
}