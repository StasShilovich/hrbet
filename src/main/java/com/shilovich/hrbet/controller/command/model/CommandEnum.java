package com.shilovich.hrbet.controller.command.model;

public enum CommandEnum {
    RACES,
    LOGIN,
    LOGOUT;

    public static CommandEnum getCommand(String command) {
        return CommandEnum.valueOf(command.toUpperCase());
    }
}
