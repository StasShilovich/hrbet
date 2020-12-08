package com.shilovich.hrbet.bean;

import com.shilovich.hrbet.controller.CommandType;

public enum PermissionEnum {
    QUEST_BASIC,
    USER_BASIC,
    ADMIN_BASIC,
    CUSTOMER_BASIC,
    PLACE_BET,
    PLACE_RESULT,
    PLACE_RATIO,
    BAN_USER;

    public static PermissionEnum getPermission(String permission) {
        return PermissionEnum.valueOf(permission.toUpperCase());
    }
}
