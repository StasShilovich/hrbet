package com.shilovich.hrbet.dao;

public class DaoTableField {
    /**
     * USER
     */
    public static final String USER_ID = "u.id";
    public static final String USER_NAME = "u.name";
    public static final String USER_SURNAME = "u.surname";
    public static final String USER_PASSWORD = "u.password";
    public static final String USER_CASH = "u.cash";
    public static final String USER_EMAIL = "u.email";
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
    public static final String P_ID = "p.id";
    public static final String P_NAME = "p.name";
    /**
     * BETS
     */
    public static final String BET_ID = "b.id";
    public static final String BET_STATUS = "b.is_win";
    public static final String BET_TIME = "b.time";
    public static final String BET_RACE_ID = "b.race_id";
    public static final String BET_USER_ID = "b.user_id";
    public static final String BET_CASH = "b.cash";
    public static final String BET_RATIO = "b.ratio";
    public static final String BET_TYPE_ID = "b.type_id";
    public static final String BET_TYPE_NAME = "t.name";
    public static final String BET_HORSE_ID = "b.bet_horse_id";
    public static final String BET_HORSE_NAME = "h.name";
    public static final String BET_COUNT = "COUNT(b.id)";
    public static final String BET_SUM = "SUM(b.cash)";
    /**
     * HORSE
     */
    public static final String HORSE_ID = "h.id";
    public static final String HORSE_NAME = "h.name";
    public static final String HORSE_AGE = "h.age";
    public static final String HORSE_JOCKEY = "h.jockey";
    /**
     * Ratio
     */
    public static final String RATIO_HORSE_ID="r.horse_id";
    public static final String RATIO_TYPE_ID="r.type_id";
    public static final String RATIO_RATIO="r.ratio";

    /**
     * Count
     */
    public static final String ENTITY_COUNT = "count(id)";
    private DaoTableField(){}
}