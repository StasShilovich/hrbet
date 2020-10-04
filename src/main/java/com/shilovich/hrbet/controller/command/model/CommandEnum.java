package com.shilovich.hrbet.controller.command.model;

public enum CommandEnum {
    RACES,
    LOGIN;

    public static CommandEnum getCommand(String command) {
        return CommandEnum.valueOf(command.toUpperCase());
    }
}
