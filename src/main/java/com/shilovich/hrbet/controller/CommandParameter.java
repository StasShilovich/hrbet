package com.shilovich.hrbet.controller;

public class CommandParameter {
    /**
     * Parameters
     */
    public static final String PARAM_NAME = "name";
    public static final String PARAM_SURNAME = "surname";
    public static final String PARAM_PASSWORD = "password";
    public static final String PARAM_EMAIL = "email";
    public static final String PARAM_PAGE = "page";
    public static final String PARAM_RACE_ID = "raceId";
    public static final String PARAM_RACES_LIST = "races";
    public static final String PARAM_COOKIE_LOCALE = "locale";
    /**
     * Attributes
     */
    public static final String ATTR_USER_AUTH = "userAuth";
    public static final String ATTR_USER_MAP = "userMap";
    public static final String ATTR_RACE_SET = "horseSet";
    public static final String ATTR_PAGE_NUMBER = "pageNumber";
    public static final String ATTR_USER_BETS = "userBets";
    public static final String ATTR_EMPTY_PARAM = "emptyParams";
    public static final String ATTR_INCORRECT_DATA = "incorrectData";
    public static final String ATTR_EMPTY_MESSAGE = "There should be no empty fields!";
    public static final String ATTR_INCORRECT_MESSAGE = "Incorrect email or password! Try again.";

    /**
     * PAGES
     */
    public static final String PAGE_INDEX = "/index.jsp";
    public static final String PAGE_REDIRECT_INDEX = "/hrbets";
    public static final String PAGE_RACES = "/WEB-INF/pages/races.jsp";
    public static final String PAGE_REGISTRATION = "/WEB-INF/pages/registration.jsp";
    public static final String PAGE_RACE = "/WEB-INF/pages/race.jsp";
    public static final String PAGE_PROFILE = "/WEB-INF/pages/profile.jsp";
    public static final String PAGE_PROFILE_BETS = "/WEB-INF/pages/profileBets.jsp";
    public static final String PAGE_START = "http://localhost:8888/hrbets";
    public static final String PAGE_USER_FRIENDLY = "www.races/hrbets";
    /**
     * LOCALIZATION
     */
    public static final String LOCALE_LANGUAGE_RU = "ru";
    public static final String LOCALE_LANGUAGE_EN = "en";
    public static final String LOCALE_COUNTRY_RU = "RU";
    public static final String LOCALE_COUNTRY_US = "US";
    /**
     * Filter
     */
    public static final String FILTER_ENCODING ="encoding";
    /**
     * OTHERS
     */
    public static final String URL_REFERER = "referer";
    public static final int PAGE_OFFSET = 0;
}
