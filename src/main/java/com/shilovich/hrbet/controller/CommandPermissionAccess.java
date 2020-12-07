package com.shilovich.hrbet.controller;

import java.util.EnumSet;
import java.util.Set;

import static com.shilovich.hrbet.controller.CommandType.*;

public enum CommandPermissionAccess {
    QUEST(EnumSet.of(RACES, RACE, LOGIN, FORWARD_REGISTRATION, REGISTRATION, COOKIE)),
    USER_BASIC(EnumSet.of(RACES, RACE, LOGOUT, PROFILE, COOKIE, SHOW_USER_BETS, UPDATE_CASH)),
    ADMIN_BASIC(EnumSet.of(RACES, RACE, LOGOUT, PROFILE, COOKIE, SHOW_USERS)),
    CUSTOMER_BASIC(EnumSet.of(RACES, RACE, LOGOUT, PROFILE, COOKIE, ADD_RACE, DELETE_RACE)),
    PLACE_BET(EnumSet.of(BET)),
    PLACE_RESULT(EnumSet.of(ENTER_RESULT)),
    PLACE_RATIO(EnumSet.of(SET_RATIO)),
    BAN_USER(EnumSet.of(DELETE_USER));

    private Set<CommandType> allowedCommands;

    CommandPermissionAccess(Set<CommandType> allowedCommands) {
        this.allowedCommands = allowedCommands;
    }

    public static CommandPermissionAccess getPermission(String permission) {
        return CommandPermissionAccess.valueOf(permission.toUpperCase());
    }

    public boolean isCommandAllowed(CommandType commandType) {
        for (CommandType allowedCommand : allowedCommands) {
            if (allowedCommand == commandType) {
                return true;
            }
        }
        return false;
    }
}
