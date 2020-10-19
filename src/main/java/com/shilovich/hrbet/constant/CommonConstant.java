package com.shilovich.hrbet.constant;

public final class CommonConstant {
    /**
     * USER
     */
    public static final String USER_ID = "u.id";
    public static final String USER_NAME = "u.name";
    public static final String USER_SURNAME = "u.surname";
    public static final String USER_PASSWORD = "u.password";
    /**
     * ROLE
     */
    public static final String ROLE_ID = "r.id";
    public static final String ROLE_NAME = "r.name";
    /**
     * RACE
     */
    public static final String RACE_ID = "r.id";
    public static final String RACE_LOCATION = "r.location";
    public static final String RACE_TIME = "r.time";
    public static final String RACE_BANK_DOLLARS = "r.bank_dollars";
    /**
     * ROLE_PERMISSIONS
     */
    public static final String RP_ROLE_ID = "rp.role_id";
    public static final String P_ID = "p.id";
    public static final String P_NAME = "p.name";
    /**
     * BETS
     */
    public static final String BET_ID = "b.id";
    public static final String BET_STATUS = "b.status";
    public static final String BET_TIME = "b.time";
    public static final String BET_RACE_ID = "b.race_id";
    public static final String BET_DOLLARS = "b.cash_dollars";
    public static final String BET_CENTS = "b.cash_cents";
    public static final String BET_TYPE_ID = "b.type_id";
    public static final String BET_TYPE_NAME = "t.name";
    public static final String BET_HORSE_ID = "b.bet_horse_id";
    public static final String BET_HORSE_NAME = "h.name";
    /**
     * HORSE
     */
    public static final String HORSE_ID = "h.id";
    public static final String HORSE_NAME = "h.name";
    public static final String HORSE_AGE = "h.age";
    public static final String HORSE_JOCKEY = "h.jockey";
    /**
     * User parameters and attribute
     */
    public static final String PARAM_NAME = "name";
    public static final String PARAM_ERROR_NAME = "incorrectName";
    public static final String PARAM_SURNAME = "surname";
    public static final String PARAM_ERROR_SURNAME = "incorrectSurname";
    public static final String PARAM_PASSWORD = "password";
    public static final String PARAM_ERROR_PASSWORD = "incorrectPassword";
    public static final String PARAM_EMAIL = "email";
    public static final String PARAM_ERROR_EMAIL = "incorrectEmail";
    public static final String PARAM_RACE_ID = "raceId";
    public static final String ATTR_USER_AUTH = "userAuth";
    public static final String ATTR_USER_MAP = "userMap";
    public static final String ATTR_RACE_SET = "horseSet";
    public static final String ATTR_EMPTY_PARAM = "emptyParams";
    public static final String ATTR_INCORRECT_DATA = "incorrectData";
    public static final String ATTR_EMPTY_MESSAGE = "There should be no empty fields!";
    public static final String ATTR_INCORRECT_MESSAGE = "Incorrect email or password! Try again.";
    public static final String PARAM_RACES_LIST = "races";
    public static final String PARAM_COOKIE_LOCALE = "locale";
    public static final String LOCALE_LANGUAGE_RU = "ru";
    public static final String LOCALE_LANGUAGE_EN = "en";
    public static final String LOCALE_COUNTRY_RU = "RU";
    public static final String LOCALE_COUNTRY_US = "US";
    public static final String URL_REFERER = "referer";
    /**
     * PAGES
     */
    public static final String PAGE_INDEX = "/index.jsp";
    public static final String PAGE_REDIRECT_INDEX = "/hrbets";
    public static final String PAGE_RACES = "/WEB-INF/pages/races.jsp";
    public static final String PAGE_REGISTRATION = "/WEB-INF/pages/registration.jsp";
    public static final String PAGE_RACE = "/WEB-INF/pages/race.jsp";
    public static final String PAGE_PROFILE = "/WEB-INF/pages/profile.jsp";
    public static final String PAGE_START = "http://localhost:8888/hrbets";
    /**
     * OTHERS
     */
    public static final String BLANK = "";
}
