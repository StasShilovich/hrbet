package com.shilovich.hrbet.controller.model;

public enum CommandEnum {
    RACES,
    LOGIN,
    LOGOUT,
    REGISTRATION,
    FORWARD_REGISTRATION,
    RACE,
    PROFILE,
    COOKIE,
    SHOW_USER_BETS;

    public static boolean isContains(String name) {
        for (CommandEnum value : CommandEnum.values()) {
            if (value.name().equalsIgnoreCase(name)) {
                return true;
            }
        }
        return false;
    }

    public static CommandEnum getCommand(String command) {
        return CommandEnum.valueOf(command.toUpperCase());
    }
}
