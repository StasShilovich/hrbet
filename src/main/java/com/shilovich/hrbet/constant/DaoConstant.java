package com.shilovich.hrbet.constant;

public class DaoConstant {
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
}
